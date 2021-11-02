package booking.hotel.beans;


import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.concurrent.TimeUnit;

@Configuration
public class ApplicationBeans {

    @Bean
    public NoOpPasswordEncoder noOpPasswordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("users","rooms");
        cacheManager.setCaffeine(cacheProperties());
        return cacheManager;
    }



    public Caffeine<Object, Object> cacheProperties() {
        return Caffeine.newBuilder()
                .initialCapacity(10)
                .maximumSize(50)
                .expireAfterAccess(20, TimeUnit.SECONDS)
                .weakKeys()
                .recordStats();
    }

    @Bean
    public S3Client s3Client(AwsS3Config amazonConfiguration) {
        return S3Client
                .builder()
                .region(Region.of(amazonConfiguration.getRegion()))
                .credentialsProvider(() ->
                        AwsBasicCredentials.create(
                                amazonConfiguration.getAwsAccessKeyId(),
                                amazonConfiguration.getAwsSecretKey()
                        ))
                .build();
    }

}

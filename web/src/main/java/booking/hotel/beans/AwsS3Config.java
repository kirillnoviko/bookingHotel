package booking.hotel.beans;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties("amazon")
public class AwsS3Config {

    private String serverURL;

    private String awsAccessKeyId;

    private String awsSecretKey;

    private String folder;

    private String bucket;

    private String region;
}
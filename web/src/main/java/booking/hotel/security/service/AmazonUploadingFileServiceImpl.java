package booking.hotel.security.service;

import booking.hotel.beans.AwsS3Config;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AmazonUploadingFileServiceImpl implements AmazonUploadingFileService {

    private final S3Client s3Client;

    private final AwsS3Config amazonS3Config;

    @Override
    public String uploadFile(byte[] imageBytes, Long userId) throws IOException {

        String imageUUID = UUID.randomUUID().toString();

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(amazonS3Config.getBucket())
                        .contentType("image/jpeg")
                        .contentLength((long) imageBytes.length)
                        .acl(ObjectCannedACL.PUBLIC_READ)
                        .key(String.format("%s/%s/%s.jpg", amazonS3Config.getFolder(), userId, imageUUID))
                        .build(),
                RequestBody.fromBytes(imageBytes)
        );

        return String.format("%s/%s/%s/%s/%s.jpg",
                amazonS3Config.getServerURL(),
                amazonS3Config.getBucket(),
                amazonS3Config.getFolder(),
                userId, imageUUID);
    }
}
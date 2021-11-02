package booking.hotel.controller.rest;


import booking.hotel.domain.User;
import booking.hotel.domain.UserPhoto;
import booking.hotel.repository.dataspring.UserRepositoryData;
import booking.hotel.security.service.AmazonUploadingFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/aws/upload")
@RequiredArgsConstructor
public class AwsFileUploadingController {

    private final AmazonUploadingFileService service;
    private final UserRepositoryData userRepository;

    @PostMapping("/user/{userId}")
    public User uploadPhoto(@PathVariable Long userId,
                            MultipartFile photo) throws Exception {

        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            String userPhotoLink = service.uploadFile(photo.getBytes(), userId);

            User hibernateUser = userOptional.get();
            Set<UserPhoto> photos = hibernateUser.getPhotos();
            photos.add(new UserPhoto(userPhotoLink));

            hibernateUser.setPhotos(photos);

            return userRepository.save(hibernateUser);
        } else {
            throw new RuntimeException("User not found!");
        }
    }
}
package booking.hotel.security.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import booking.hotel.exception.NoSuchEntityException;
import booking.hotel.request.UserCreateRequest;
import booking.hotel.domain.User;
import booking.hotel.repository.dataspring.UserRepositoryData;
import booking.hotel.security.service.ValidationNewUser;
import booking.hotel.service.UserService;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserRepositoryData userRepositoryData;
    public final ConversionService conversionService;
    private final ValidationNewUser validationUser;
    private final UserService userService;

    @Autowired
    private JavaMailSender mailSender;

    @ApiOperation(value = "Creating one user")
    @PostMapping
    public User createUser(@RequestBody UserCreateRequest createRequest) throws RuntimeException {

        try{

            User newUser = validationUser.checkUser(conversionService.convert(createRequest,User.class));
            String code=UUID.randomUUID().toString();

            try {

                MimeMessage mail = mailSender.createMimeMessage();
                MimeMessageHelper messageHelper = new MimeMessageHelper(mail, true);
                messageHelper.setTo(newUser.getGmail());
                messageHelper.setSubject("Booking hotel");
                messageHelper.setText("registration confirmation code: " + code , true);
                mailSender.send(mail);

            } catch (MailException | MessagingException e) {
                throw new NoSuchEntityException("error send registration message");
            }

            User savedUser = userService.saveOrUpdateWithAddedRoles(newUser);
            userRepositoryData.createRowUuidCode(code,new Timestamp(new Date().getTime()),savedUser.getId());

            return savedUser;

        }catch (RuntimeException E){
           throw E;
        }

    }







}

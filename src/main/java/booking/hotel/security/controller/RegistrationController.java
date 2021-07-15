package booking.hotel.security.controller;
import booking.hotel.controller.exception.DefaultExceptionHandler;
import booking.hotel.controller.request.UserCreateRequest;
import booking.hotel.domain.Role;
import booking.hotel.domain.User;
import booking.hotel.exception.NoSuchEntityException;
import booking.hotel.repository.RoleRepository;
import booking.hotel.repository.UserRepository;
import booking.hotel.security.service.ValidationNewUser;
import booking.hotel.security.service.ValidationRoles;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final ValidationRoles validationRoles;
    private final ValidationNewUser validationUser;

    @Autowired
    private JavaMailSender mailSender;

    @ApiOperation(value = "Creating one user")
    @PostMapping
    public User createUser(@RequestBody UserCreateRequest createRequest) throws RuntimeException {

        try{
            validationRoles.checkRoles(createRequest.getRoles());

            User newUser = new User();

            newUser.setGmail(createRequest.getGmail());
            newUser.setName(createRequest.getName());
            newUser.setSurname(createRequest.getSurname());
            newUser.setPassword(createRequest.getPassword());

            validationUser.checkUser(newUser);

            User savedUser = userRepository.save(newUser);

            List<Role> roles = roleRepository.findAll();

            userRepository.saveUserRoles(savedUser, roles);


            return savedUser;

        }catch (RuntimeException E){
           throw E;
        }




    }


    @RequestMapping(value="/mail",method=RequestMethod.GET)
    public ModelAndView sendEmailToReference(HttpServletRequest request, HttpSession session) {

        try {

            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mail, true);
            messageHelper.setTo("kiril.novikov.2222@mail.ru");
            messageHelper.setSubject("Testing mail");
            messageHelper.setText("bbbbb", true);
            mailSender.send(mail);

        } catch (MailException | MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }





}

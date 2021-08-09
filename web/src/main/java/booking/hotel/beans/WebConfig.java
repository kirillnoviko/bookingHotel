package booking.hotel.beans;

import booking.hotel.converter.RoleCreateRequestConverter;
import booking.hotel.converter.RoleEditRequestConverter;
import booking.hotel.converter.UserCreateRequestConverter;
import booking.hotel.converter.UserEditRequestConverter;
import booking.hotel.repository.dataspring.RoleRepositoryData;
import booking.hotel.repository.dataspring.UserRepositoryData;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final UserRepositoryData userRepositoryData;
    private final RoleRepositoryData roleRepositoryData;

    @Override
    public void addFormatters(FormatterRegistry registry) {

        registry.addConverter(new UserEditRequestConverter(userRepositoryData) {});
        registry.addConverter(new UserCreateRequestConverter(){});
        registry.addConverter(new RoleCreateRequestConverter(){});
        registry.addConverter(new RoleEditRequestConverter(roleRepositoryData){});

    }
}
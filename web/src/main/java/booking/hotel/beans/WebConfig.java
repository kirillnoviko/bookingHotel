package booking.hotel.beans;

import booking.hotel.converter.*;
import booking.hotel.repository.dataspring.OrderRepositoryData;
import booking.hotel.repository.dataspring.RoleRepositoryData;
import booking.hotel.repository.dataspring.RoomRepositoryData;
import booking.hotel.repository.dataspring.UserRepositoryData;
import booking.hotel.request.OrderChangeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final UserRepositoryData userRepositoryData;
    private final RoleRepositoryData roleRepositoryData;
    private final RoomRepositoryData roomRepositoryData;
    private final OrderRepositoryData orderRepositoryData;

    @Override
    public void addFormatters(FormatterRegistry registry) {

        registry.addConverter(new UserEditRequestConverter(userRepositoryData) {});
        registry.addConverter(new UserCreateRequestConverter(){});
        registry.addConverter(new RoleCreateRequestConverter(){});
        registry.addConverter(new RoleEditRequestConverter(roleRepositoryData){});
        registry.addConverter(new OrderCreateRequestConverter(roomRepositoryData){});
        registry.addConverter(new OrderEditRequestConverter(orderRepositoryData,roomRepositoryData){});
        registry.addConverter(new OrderSearchRequestConverter(orderRepositoryData,roomRepositoryData){});
        registry.addConverter(new RoomSearchRequestConverter(){});

    }
}
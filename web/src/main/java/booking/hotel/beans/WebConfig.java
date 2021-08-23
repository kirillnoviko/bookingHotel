package booking.hotel.beans;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import booking.hotel.converter.UserCreateRequestConverter;
import booking.hotel.converter.OrderCreateRequestConverter;
import booking.hotel.converter.RoleCreateRequestConverter;
import booking.hotel.converter.RoleEditRequestConverter;
import booking.hotel.converter.UserEditRequestConverter;
import booking.hotel.converter.OrderEditRequestConverter;
import booking.hotel.converter.OrderSearchRequestConverter;
import booking.hotel.converter.RoomSearchRequestConverter;
import booking.hotel.repository.dataspring.OrderRepositoryData;
import booking.hotel.repository.dataspring.RoleRepositoryData;
import booking.hotel.repository.dataspring.RoomRepositoryData;
import booking.hotel.repository.dataspring.UserRepositoryData;


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
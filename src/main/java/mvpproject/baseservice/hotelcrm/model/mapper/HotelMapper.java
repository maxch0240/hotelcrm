package mvpproject.baseservice.hotelcrm.model.mapper;


import lombok.RequiredArgsConstructor;
import mvpproject.baseservice.hotelcrm.model.dto.HotelBriefDto;
import mvpproject.baseservice.hotelcrm.model.dto.HotelDto;
import mvpproject.baseservice.hotelcrm.model.entity.AddressEntity;
import mvpproject.baseservice.hotelcrm.model.entity.HotelEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class HotelMapper {
    private final ModelMapper modelMapper;

    public HotelDto convertToDto(HotelEntity hotelEntity) {
        return modelMapper.map(hotelEntity, HotelDto.class);
    }

    public HotelBriefDto convertToBriefDto(HotelEntity hotelEntity) {
        return HotelBriefDto.builder()
                .id(hotelEntity.getId())
                .name(hotelEntity.getName())
                .description(hotelEntity.getDescription())
                .address(formatAddress(hotelEntity.getAddress()))
                .phone(hotelEntity.getContacts().getPhone())
                .build();
    }

    String formatAddress(AddressEntity address) {
        if (address == null) return "";

        return String.join(", ",
                address.getStreet(),
                address.getCity(),
                address.getPostCode(),
                address.getCountry()
        );
    }

    public HotelEntity convertToEntity(HotelDto hotelDto) {
        return modelMapper.map(hotelDto, HotelEntity.class);
    }
}

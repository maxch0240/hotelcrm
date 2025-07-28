package mvpproject.baseservice.hotelcrm.model.mapper;

import mvpproject.baseservice.hotelcrm.model.dto.HotelBriefDto;
import mvpproject.baseservice.hotelcrm.model.dto.HotelDto;
import mvpproject.baseservice.hotelcrm.model.entity.AddressEntity;
import mvpproject.baseservice.hotelcrm.model.entity.ContactsEntity;
import mvpproject.baseservice.hotelcrm.model.entity.HotelEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.util.Collections;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelMapperTest {
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private HotelMapper hotelMapper;

    @Test
    void convertToDto_shouldUseModelMapper() {
        HotelEntity entity = createHotelEntity();
        HotelDto expectedDto = createHotelDto();

        when(modelMapper.map(entity, HotelDto.class)).thenReturn(expectedDto);

        HotelDto result = hotelMapper.convertToDto(entity);

        assertThat(result).isSameAs(expectedDto);
        verify(modelMapper).map(entity, HotelDto.class);
    }

    @Test
    void convertToEntity_shouldUseModelMapper() {
        HotelDto dto = createHotelDto();
        HotelEntity expectedEntity = createHotelEntity();

        when(modelMapper.map(dto, HotelEntity.class)).thenReturn(expectedEntity);

        HotelEntity result = hotelMapper.convertToEntity(dto);

        assertThat(result).isSameAs(expectedEntity);
        verify(modelMapper).map(dto, HotelEntity.class);
    }

    @Test
    void convertToBriefDto_shouldMapCorrectFields() {
        HotelEntity entity = createHotelEntity();
        entity.getContacts().setPhone("+1234567890");

        HotelBriefDto result = hotelMapper.convertToBriefDto(entity);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Grand Plaza");
        assertThat(result.getDescription()).isEqualTo("Luxury hotel");
        assertThat(result.getAddress()).isEqualTo("123 Main St, New York, 10001, USA");
        assertThat(result.getPhone()).isEqualTo("+1234567890");
    }

    @Test
    void convertToBriefDto_shouldHandleNullAddress() {
        HotelEntity entity = createHotelEntity();
        entity.setAddress(null);

        HotelBriefDto result = hotelMapper.convertToBriefDto(entity);

        assertThat(result.getAddress()).isEmpty();
    }

    @Test
    void formatAddress_shouldJoinAllComponents() {
        AddressEntity address = AddressEntity.builder()
                .houseNumber(23)
                .street("123 Main St")
                .city("New York")
                .postCode("10001")
                .country("USA")
                .build();

        String result = hotelMapper.formatAddress(address);

        assertThat(result).isEqualTo("123 Main St, New York, 10001, USA");
    }

    private HotelEntity createHotelEntity() {
        AddressEntity address = AddressEntity.builder()
                .houseNumber(12)
                .street("123 Main St")
                .city("New York")
                .postCode("10001")
                .country("USA")
                .build();

        ContactsEntity contacts = new ContactsEntity();
        contacts.setPhone("+1234567890");

        return HotelEntity.builder()
                .id(1L)
                .name("Grand Plaza")
                .description("Luxury hotel")
                .address(address)
                .contacts(contacts)
                .amenities(Collections.singletonList("Pool"))
                .build();
    }

    private HotelDto createHotelDto() {
        return HotelDto.builder()
                .id(1L)
                .name("Grand Plaza")
                .build();
    }
}
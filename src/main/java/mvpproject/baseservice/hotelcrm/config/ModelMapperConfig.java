package mvpproject.baseservice.hotelcrm.config;

import org.hibernate.collection.spi.PersistentBag;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new AbstractConverter<PersistentBag<?>, List<String>>() {
            @Override
            protected List<String> convert(PersistentBag source) {
                return source == null ? null : new ArrayList<String>(source);
            }
        });

        return modelMapper;
    }
}

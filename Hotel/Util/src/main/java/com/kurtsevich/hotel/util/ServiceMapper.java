package com.kurtsevich.hotel.util;

import com.kurtsevich.hotel.dto.ServiceDto;
import com.kurtsevich.hotel.dto.ServiceWithoutHistoriesDto;
import com.kurtsevich.hotel.model.Service;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ServiceMapper {
    ServiceMapper INSTANCE = Mappers.getMapper(ServiceMapper.class);

    ServiceDto serviceToServiceDto(Service service);

    Service serviceDtoToService(ServiceDto serviceDTO);

    ServiceWithoutHistoriesDto serviceToServiceWithoutHistoriesDTO(Service service);

    Service serviceWithoutHistoriesDTOToService(ServiceWithoutHistoriesDto serviceDTO);
}

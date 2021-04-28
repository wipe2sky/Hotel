package com.kurtsevich.hotel.server.util;

import com.kurtsevich.hotel.server.dto.ServiceDto;
import com.kurtsevich.hotel.server.dto.ServiceWithoutHistoriesDto;
import com.kurtsevich.hotel.server.model.Service;
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

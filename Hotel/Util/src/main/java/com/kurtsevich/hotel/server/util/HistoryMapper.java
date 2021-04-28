package com.kurtsevich.hotel.server.util;

import com.kurtsevich.hotel.server.dto.HistoryDto;
import com.kurtsevich.hotel.server.model.History;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HistoryMapper {
    HistoryMapper INSTANCE = Mappers.getMapper(HistoryMapper.class);

    HistoryDto historyToHistoryDto(History history);

    History historyDtoToHistory(HistoryDto historyDTO);
}

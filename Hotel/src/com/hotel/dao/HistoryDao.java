package com.hotel.dao;

import com.hotel.api.dao.IHistoryDao;
import com.hotel.model.History;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HistoryDao extends AbstractDao<History> implements IHistoryDao {
    private static HistoryDao instance;

    private HistoryDao() {
    }

    public static HistoryDao getInstance() {
        if(instance == null) instance = new HistoryDao();
        return instance;
    }

    public List<History> getGuestHistory(Integer guestId) {
        return getAll().stream()
                .filter(history ->  history.getGuest().getId().equals(guestId))
                .collect(Collectors.toList());
//        List<History> guestHistory = new ArrayList<>();
//
//        for (History history :
//                getAll()) {
//            if (history.getGuest().getId().equals(guestId)) guestHistory.add(history);
//        }
//        return guestHistory;
    }

    @Override
    public History update(History entity) {
        History history = getById(entity.getId());
        history.setRoom(entity.getRoom());
        history.setGuest(entity.getGuest());
        history.setCheckInDate(entity.getCheckInDate());
        history.setCheckOutDate(entity.getCheckOutDate());
        history.setCost(entity.getCost());
        history.setServices(entity.getServices());
        return history;
    }
}

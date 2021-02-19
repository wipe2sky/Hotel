package com.dao;

import com.api.dao.IHistoryDao;
import com.model.History;

import java.util.ArrayList;
import java.util.List;

public class HistoryDao extends AbstractDao<History> implements IHistoryDao {


    public List<History> getGuestHistory(Integer guestId) {
        List<History> guestHistory = new ArrayList<>();

        for (History history :
                getAll()) {
            if (history.getGuest().getId().equals(guestId)) guestHistory.add(history);
        }
        return guestHistory;
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

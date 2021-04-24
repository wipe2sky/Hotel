package com.kurtsevich.hotel.server.controller;

import com.kurtsevich.hotel.server.api.service.IHistoryService;
import com.kurtsevich.hotel.server.dto.CheckInDto;
import com.kurtsevich.hotel.server.dto.GuestWithoutHistoriesDto;
import com.kurtsevich.hotel.server.dto.HistoryDto;
import com.kurtsevich.hotel.server.dto.ServiceWithoutHistoriesDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/histories")
@RequiredArgsConstructor
public class HistoriesController {
    private final IHistoryService historyService;

    @PutMapping("/checkin")
    @Valid
    public void checkIn(@RequestBody CheckInDto checkInDto) {
        historyService.checkIn(checkInDto);
    }

    @PutMapping("/{id}/checkout")
    public void checkOut(@PathVariable Integer id) {
        historyService.checkOut(id);
    }

    @GetMapping("/{guestId}/cost")
    public ResponseEntity<Double> getCostOfLiving(@PathVariable Integer guestId) {
        return new ResponseEntity<>(historyService.getCostOfLiving(guestId), HttpStatus.OK);
    }

    @GetMapping("/{roomId}/guests")
    public ResponseEntity<List<GuestWithoutHistoriesDto>> getLast3GuestInRoom(@PathVariable Integer roomId) {
        return new ResponseEntity<>(historyService.getLast3GuestInRoom(roomId), HttpStatus.OK);
    }

    @GetMapping("/{guestId}/history")
    public ResponseEntity<List<HistoryDto>> getGuestHistory(@PathVariable Integer guestId) {
        return new ResponseEntity<>(historyService.getGuestHistory(guestId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<HistoryDto>> getAll() {
        return new ResponseEntity<>(historyService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{guestId}/service")
    public ResponseEntity<List<ServiceWithoutHistoriesDTO>> getListOfGuestService(@PathVariable Integer guestId) {
        return new ResponseEntity<>(historyService.getListOfGuestService(guestId), HttpStatus.OK);
    }

}

package com.kurtsevich.hotel.server.controller;

import com.kurtsevich.hotel.server.api.service.IHistoryService;
import com.kurtsevich.hotel.server.dto.CheckInDto;
import com.kurtsevich.hotel.server.dto.GuestWithoutHistoriesDto;
import com.kurtsevich.hotel.server.dto.HistoryDto;
import com.kurtsevich.hotel.server.dto.ServiceWithoutHistoriesDto;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Void> checkIn(@RequestBody CheckInDto checkInDto) {
        historyService.checkIn(checkInDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/checkout")
    public ResponseEntity<Void> checkOut(@PathVariable Integer id) {
        historyService.checkOut(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{guestId}/cost")
    public ResponseEntity<Double> getCostOfLiving(@PathVariable Integer guestId) {
        return ResponseEntity.ok(historyService.getCostOfLiving(guestId));
    }

    @GetMapping("/{roomId}/guests")
    public ResponseEntity<List<GuestWithoutHistoriesDto>> getLast3GuestInRoom(@PathVariable Integer roomId) {
        return ResponseEntity.ok(historyService.getLast3GuestInRoom(roomId));
    }

    @GetMapping("/{guestId}/history")
    public ResponseEntity<List<HistoryDto>> getGuestHistory(@PathVariable Integer guestId) {
        return ResponseEntity.ok(historyService.getGuestHistory(guestId));
    }

    @GetMapping
    public ResponseEntity<List<HistoryDto>> getAll() {
        return ResponseEntity.ok(historyService.getAll());
    }

    @GetMapping("/{guestId}/service")
    public ResponseEntity<List<ServiceWithoutHistoriesDto>> getListOfGuestService(@PathVariable Integer guestId) {
        return ResponseEntity.ok(historyService.getListOfGuestService(guestId));
    }

}

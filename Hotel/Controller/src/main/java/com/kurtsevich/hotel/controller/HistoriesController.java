package com.kurtsevich.hotel.controller;

import com.kurtsevich.hotel.api.service.IHistoryService;
import com.kurtsevich.hotel.dto.CheckInDto;
import com.kurtsevich.hotel.dto.GuestWithoutHistoriesDto;
import com.kurtsevich.hotel.dto.HistoryDto;
import com.kurtsevich.hotel.dto.ServiceWithoutHistoriesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/histories")
@RequiredArgsConstructor
@Validated
public class HistoriesController {
    private final IHistoryService historyService;

    @PutMapping("/checkin")
    public ResponseEntity<Void> checkIn(@Valid @RequestBody CheckInDto checkInDto, BindingResult bindingResult) {
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

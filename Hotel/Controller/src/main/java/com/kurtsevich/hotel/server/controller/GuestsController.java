package com.kurtsevich.hotel.server.controller;

import com.kurtsevich.hotel.server.SortStatus;
import com.kurtsevich.hotel.server.api.service.IGuestService;
import com.kurtsevich.hotel.server.dto.GuestDto;
import com.kurtsevich.hotel.server.dto.GuestWithoutHistoriesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guests")
@RequiredArgsConstructor
public class GuestsController {
    private final IGuestService guestService;

    @GetMapping
    public ResponseEntity<List<GuestWithoutHistoriesDto>> getAll() {
        return ResponseEntity.ok(guestService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuestWithoutHistoriesDto> getGuest(@PathVariable int id) {
        return ResponseEntity.ok(guestService.getById(id));
    }

    @PutMapping
    public ResponseEntity<Void> addGuest(@RequestBody GuestDto guestDto) {
        guestService.add(guestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  deleteGuest(@PathVariable int id) {
        guestService.deleteGuest(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sort")
    public ResponseEntity<List<GuestWithoutHistoriesDto>> getSortBy(@RequestParam String sortStatus) {
        return ResponseEntity.ok(guestService.getSortBy(SortStatus.valueOf(sortStatus)));
    }

    @GetMapping(value = "/count")
    public ResponseEntity<Long> getCountGuestInHotel() {
        return ResponseEntity.ok(guestService.getCountGuestInHotel());
    }
}

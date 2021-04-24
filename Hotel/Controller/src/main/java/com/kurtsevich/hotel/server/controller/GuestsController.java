package com.kurtsevich.hotel.server.controller;

import com.kurtsevich.hotel.server.SortStatus;
import com.kurtsevich.hotel.server.api.service.IGuestService;
import com.kurtsevich.hotel.server.dto.GuestDto;
import com.kurtsevich.hotel.server.dto.GuestWithoutHistoriesDto;
import com.kurtsevich.hotel.server.dto.SortStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/guests")
@RequiredArgsConstructor
public class GuestsController {
    private final IGuestService guestService;

    @GetMapping
    public ResponseEntity<List<GuestWithoutHistoriesDto>> getAll() {
        return new ResponseEntity<>(guestService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuestWithoutHistoriesDto> getGuest(@PathVariable int id) {
        return new ResponseEntity<>(guestService.getById(id), HttpStatus.OK);
    }

    @PutMapping
    public void addGuest(@RequestBody GuestDto guestDTO) {
        guestService.add(guestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteGuest(@PathVariable int id) {
        guestService.deleteGuest(id);
    }

    @GetMapping("/sort")
    public ResponseEntity<List<GuestWithoutHistoriesDto>> getSortBy(@RequestParam String sortStatus) {
        return new ResponseEntity<>(guestService.getSortBy(SortStatus.valueOf(sortStatus)), HttpStatus.OK);
    }

    @GetMapping(value = "/count")
    public ResponseEntity<Long> getCountGuestInHotel() {
        return new ResponseEntity<>(guestService.getCountGuestInHotel(), HttpStatus.OK);
    }
}

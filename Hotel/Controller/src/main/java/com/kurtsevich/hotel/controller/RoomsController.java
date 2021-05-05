package com.kurtsevich.hotel.controller;

import com.kurtsevich.hotel.SortStatus;
import com.kurtsevich.hotel.api.service.IRoomService;
import com.kurtsevich.hotel.dto.DateDto;
import com.kurtsevich.hotel.dto.HistoryDto;
import com.kurtsevich.hotel.dto.RoomDto;
import com.kurtsevich.hotel.dto.RoomWithoutHistoriesDto;
import com.kurtsevich.hotel.model.RoomStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
@Validated
public class RoomsController {
    private final IRoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomWithoutHistoriesDto>> getAll() {
        return ResponseEntity.ok(roomService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomWithoutHistoriesDto> getRoom(@PathVariable int id) {
        return ResponseEntity.ok(roomService.getById(id));
    }


    @PutMapping
    public ResponseEntity<Void> addRoom(@RequestBody RoomDto roomDto) {
        roomService.addRoom(roomDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Integer id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/clean")
    public ResponseEntity<Void> setCleaningStatus(@RequestBody RoomDto roomDto) {
        roomService.setCleaningStatus(roomDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/price")
    public ResponseEntity<Void> changePrice(@RequestBody RoomDto roomDto) {
        roomService.changePrice(roomDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/sort")
    public ResponseEntity<List<RoomWithoutHistoriesDto>> getSortBy(@RequestParam String sortStatus, @RequestParam String roomStatus) {
        return ResponseEntity.ok(roomService.getSortBy(SortStatus.valueOf(sortStatus), RoomStatus.valueOf(roomStatus)));
    }

    @GetMapping(value = "/get")
    public ResponseEntity<List<RoomWithoutHistoriesDto>> getAvailableAfterDate(@Valid @RequestBody DateDto date, BindingResult bindingResult) {
        return ResponseEntity.ok(roomService.getAvailableAfterDate(date.getDate().atStartOfDay()));
    }

    @GetMapping(value = "/free")
    public ResponseEntity<Integer> getNumberOfFree() {
        return ResponseEntity.ok(roomService.getNumberOfFree());
    }

    @GetMapping(value = "/history/{id}")
    public ResponseEntity<List<HistoryDto>> getRoomHistory(@PathVariable Integer id) {
        return ResponseEntity.ok(roomService.getRoomHistory(id));
    }

    @PutMapping("/repair")
    public ResponseEntity<Void> setRepairStatus(@RequestBody RoomDto roomDto) {
        roomService.setRepairStatus(roomDto);
        return ResponseEntity.noContent().build();
    }
}

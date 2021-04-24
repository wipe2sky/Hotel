package com.kurtsevich.hotel.server.controller;

import com.kurtsevich.hotel.server.api.service.IRoomService;
import com.kurtsevich.hotel.server.dto.DateDto;
import com.kurtsevich.hotel.server.dto.HistoryDto;
import com.kurtsevich.hotel.server.dto.RoomDto;
import com.kurtsevich.hotel.server.dto.RoomWithoutHistoriesDto;
import com.kurtsevich.hotel.server.model.RoomStatus;
import com.kurtsevich.hotel.server.SortStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomsController {
    private final IRoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomWithoutHistoriesDto>> getAll() {
        return new ResponseEntity<>(roomService.getAll(), HttpStatus.OK);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<RoomWithoutHistoriesDto> getRoom(@PathVariable int id) {
//        return new ResponseEntity<>(roomService.getById(id), HttpStatus.OK);
//    }
    @GetMapping("/{id}")
    public RoomWithoutHistoriesDto getRoom(@PathVariable int id) {
        return roomService.getById(id);
    }

    @PutMapping
    public void addRoom(@RequestBody RoomDto roomDTO) {
        roomService.addRoom(roomDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable Integer id) {
        roomService.deleteRoom(id);
    }

    @PutMapping("/clean")
    public void setCleaningStatus(@RequestBody RoomDto roomDTO) {
        roomService.setCleaningStatus(roomDTO);
    }

    @PutMapping("/price")
    public void changePrice(@RequestBody RoomDto roomDTO) {
        roomService.changePrice(roomDTO);
    }

    @GetMapping(value = "/sort")
    public ResponseEntity<List<RoomWithoutHistoriesDto>> getSortBy(@RequestParam String sortStatus, @RequestParam String roomStatus) {
        return new ResponseEntity<>(roomService.getSortBy(SortStatus.valueOf(sortStatus), RoomStatus.valueOf(roomStatus)), HttpStatus.OK);
    }

    @GetMapping(value = "/get")
    @Valid
    public ResponseEntity<List<RoomWithoutHistoriesDto>> getAvailableAfterDate(@RequestBody DateDto date) {
        return new ResponseEntity<>(roomService.getAvailableAfterDate(date.getDate().atStartOfDay()), HttpStatus.OK);
    }

    @GetMapping(value = "/free")
    public ResponseEntity<Integer> getNumberOfFree() {
        return new ResponseEntity<>(roomService.getNumberOfFree(), HttpStatus.OK);
    }

    @GetMapping(value = "/history/{id}")
    public ResponseEntity<List<HistoryDto>> getRoomHistory(@PathVariable Integer id) {
        return new ResponseEntity<>(roomService.getRoomHistory(id), HttpStatus.OK);
    }

    @PutMapping("/repair")
    public void setRepairStatus(@RequestBody RoomDto roomDTO) {
        roomService.setRepairStatus(roomDTO);
    }
}

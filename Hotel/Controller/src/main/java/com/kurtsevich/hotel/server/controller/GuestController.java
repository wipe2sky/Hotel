package com.kurtsevich.hotel.server.controller;

import com.kurtsevich.hotel.server.api.service.IGuestService;
import com.kurtsevich.hotel.server.model.Guest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/guests")
@RequiredArgsConstructor
public class GuestController {
    private final IGuestService guestService;
    @GetMapping("/get")
    public ResponseEntity<Guest> getGuest(@RequestBody Guest object) {
        System.out.println(object);
        Guest responceObject = guestService.getById(2);
        return new ResponseEntity(responceObject, HttpStatus.OK);
    }
}

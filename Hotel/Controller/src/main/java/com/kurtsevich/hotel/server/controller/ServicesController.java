package com.kurtsevich.hotel.server.controller;

import com.kurtsevich.hotel.server.api.service.IServiceForService;
import com.kurtsevich.hotel.server.dto.ServiceDto;
import com.kurtsevich.hotel.server.dto.ServiceToGuestDto;
import com.kurtsevich.hotel.server.dto.ServiceWithoutHistoriesDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServicesController {
    private final IServiceForService service;

    @PutMapping
    public void addService(@RequestBody ServiceDto serviceDto) {
        service.addService(serviceDto);
    }

    @DeleteMapping("/{id}")
    public void deleteService(@PathVariable Integer id) {
        service.deleteService(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceWithoutHistoriesDTO> getById(@PathVariable Integer id) {
        return new ResponseEntity<>( service.getById(id), HttpStatus.OK);
    }

    @PutMapping("/guest")
    @Valid
    public void addServiceToGuest(@RequestBody ServiceToGuestDto serviceToGuestDto) {
        service.addServiceToGuest(serviceToGuestDto);
    }

    @GetMapping("/sort")
    public ResponseEntity<List<ServiceWithoutHistoriesDTO>> getSortByPrice() {
        return new ResponseEntity<>(service.getSortByPrice(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ServiceWithoutHistoriesDTO>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @PutMapping("/price")
    public void changeServicePrice(@RequestBody ServiceDto serviceDto) {
        service.changeServicePrice(serviceDto);
    }

}

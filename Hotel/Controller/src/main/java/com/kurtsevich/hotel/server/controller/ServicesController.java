package com.kurtsevich.hotel.server.controller;

import com.kurtsevich.hotel.server.api.service.IServiceForService;
import com.kurtsevich.hotel.server.dto.ServiceDto;
import com.kurtsevich.hotel.server.dto.ServiceToGuestDto;
import com.kurtsevich.hotel.server.dto.ServiceWithoutHistoriesDto;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Void> addService(@RequestBody ServiceDto serviceDto) {
        service.addService(serviceDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Integer id) {
        service.deleteService(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceWithoutHistoriesDto> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/guest")
    @Valid
    public ResponseEntity<Void> addServiceToGuest(@RequestBody ServiceToGuestDto serviceToGuestDto) {
        service.addServiceToGuest(serviceToGuestDto);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/sort")
    public ResponseEntity<List<ServiceWithoutHistoriesDto>> getSortByPrice() {
        return ResponseEntity.ok(service.getSortByPrice());
    }

    @GetMapping
    public ResponseEntity<List<ServiceWithoutHistoriesDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/price")
    public ResponseEntity<Void> changeServicePrice(@RequestBody ServiceDto serviceDto) {
        service.changeServicePrice(serviceDto);
        return ResponseEntity.noContent().build();

    }

}

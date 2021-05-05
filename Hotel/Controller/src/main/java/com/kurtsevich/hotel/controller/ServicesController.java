package com.kurtsevich.hotel.controller;

import com.kurtsevich.hotel.api.service.IServiceForService;
import com.kurtsevich.hotel.dto.ServiceDto;
import com.kurtsevich.hotel.dto.ServiceToGuestDto;
import com.kurtsevich.hotel.dto.ServiceWithoutHistoriesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
@Validated
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
    public ResponseEntity<Void> addServiceToGuest(@Valid @RequestBody ServiceToGuestDto serviceToGuestDto, BindingResult bindingResult) {
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

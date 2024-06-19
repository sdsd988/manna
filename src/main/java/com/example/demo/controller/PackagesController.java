package com.example.demo.controller;

import com.example.demo.dto.PackagesCreateRequestDto;
import com.example.demo.dto.PackagesResponseDto;
import com.example.demo.dto.PackagesUpdateRequestDto;
import com.example.demo.service.PackagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PackagesController {

    private final PackagesService packagesService;

    @GetMapping("/packages/{packageId}")
    public PackagesResponseDto getPackages(@PathVariable Long packageId) {

        return packagesService.getPacakges(packageId);
    }

    @PostMapping("/packages")
    public PackagesResponseDto createPackages(@RequestBody PackagesCreateRequestDto body) {

        return packagesService.createPackages(body);
    }

    @PatchMapping("/packages/{packageId}")
    public void updatePackages(@PathVariable Long packageId, @RequestBody PackagesUpdateRequestDto body) {
        packagesService.updatePackages(packageId,body);
    }

    @DeleteMapping("/packages/{packageId}")
    public void deletePackages(@PathVariable Long packageId) {
        packagesService.deletePackages(packageId);
    }
}




package com.example.demo.dto;

import com.example.demo.entity.PackageImg;

import java.util.List;

public record PackagesResponseDto(Long id, Long trackingNo, List<PackageImg> images) {
}

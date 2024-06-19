package com.example.demo.dto;

import com.example.demo.entity.PackageImg;
import lombok.Builder;

import java.util.List;

@Builder
public record PackagesUpdateRequestDto(Long trackingNo, List<PackageImg> images) {
}

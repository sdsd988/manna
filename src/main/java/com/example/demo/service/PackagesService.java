package com.example.demo.service;

import com.example.demo.dto.PackagesCreateRequestDto;
import com.example.demo.dto.PackagesResponseDto;
import com.example.demo.dto.PackagesUpdateRequestDto;
import com.example.demo.entity.PackageImg;
import com.example.demo.entity.Packages;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.PackageException;
import com.example.demo.repository.PackageImgRepository;
import com.example.demo.repository.PackagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PackagesService {

    private final PackagesRepository packagesRepository;
    private final PackageImgRepository packageImgRepository;

    @Transactional(readOnly = true)
    public PackagesResponseDto getPacakges(Long packageId) {
        Packages packages = packagesRepository.findById(packageId)
                .orElseThrow(() -> new PackageException(ErrorCode.PACKAGE_NOT_EXIST,"존재하지 않는 패키지입니다.".formatted(packageId)));

        return new PackagesResponseDto(packages.getId(),packages.getTrackingNo(), packages.getImages());
    }

    @Transactional
    public PackagesResponseDto createPackages(PackagesCreateRequestDto body) {

        Packages packages = Packages.builder()
                .trackingNo(body.trackingNo())
                .build();
        Packages savePackages = packagesRepository.save(packages);

        ArrayList<PackageImg> packageImg = new ArrayList<>();
        if (!body.images().isEmpty()) {
            for (PackageImg image : body.images()) {
                PackageImg saveImg = new PackageImg(image.getFilename(), image.getType());
                packageImg.add(saveImg);
            }
        }

        packages.setPackageImg(packageImg);

        return new PackagesResponseDto(packages.getId(), savePackages.getTrackingNo(), savePackages.getImages());
    }

    @Transactional
    public void updatePackages(Long packageId, PackagesUpdateRequestDto body) {

        Packages findPackages = packagesRepository.findById(packageId)
                .orElseThrow(() -> new PackageException(ErrorCode.PACKAGE_NOT_EXIST,"존재 하지 않는 패키지입니다."));

        packageImgRepository.deleteAllInBatch();

        ArrayList<PackageImg> packageImg = new ArrayList<>();
        if (!body.images().isEmpty()) {
            for (PackageImg image : body.images()) {
                PackageImg saveImg = new PackageImg(image.getFilename(), image.getType());
                packageImg.add(saveImg);
            }
        }
        findPackages.update(body);
    }

    public void deletePackages(Long packageId) {
        packagesRepository.findById(packageId)
                .orElseThrow(() -> new PackageException(ErrorCode.PACKAGE_NOT_EXIST, "존재 하지 않는 패키지입니다."));
        packagesRepository.deleteById(packageId);
    }
}

package com.example.demo.service;

import com.example.demo.dto.PackagesCreateRequestDto;
import com.example.demo.dto.PackagesResponseDto;
import com.example.demo.dto.PackagesUpdateRequestDto;
import com.example.demo.entity.PackageImg;
import com.example.demo.entity.Packages;
import com.example.demo.exception.PackageException;
import com.example.demo.repository.PackageImgRepository;
import com.example.demo.repository.PackagesRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.exception.ErrorCode.PACKAGE_NOT_EXIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PackagesServiceTest {

    @Autowired
    PackagesRepository packagesRepository;

    @Autowired
    PackageImgRepository packageImgRepository;

    @Autowired
    PackagesService packagesService;

    @BeforeEach
    void clean() {
        packagesRepository.deleteAllInBatch();
        packageImgRepository.deleteAllInBatch();

    }

    @Test
    @DisplayName("성공 : Package 생성")
    void create() {

        //given
        PackageImg img1 = PackageImg.builder()
                .filename("image1.png")
                .type("PKG")
                .build();
        PackageImg img2 = PackageImg.builder()
                .filename("image2.png")
                .type("PKG")
                .build();
        List<PackageImg> imgs = List.of(img1, img2);

        PackagesCreateRequestDto packages = PackagesCreateRequestDto.builder()
                .trackingNo(111122223333L)
                .images(imgs)
                .build();

        //when
        packagesService.createPackages(packages);

        //then
        assertEquals(packagesRepository.findAll().size(), 1);
        Packages savePackages = packagesRepository.findAll().get(0);
        assertEquals(savePackages.getTrackingNo(),111122223333L);

        List<PackageImg> savedImgs = savePackages.getImages();
        assertEquals(savedImgs.size(), imgs.size());
        for (int i = 0; i < imgs.size(); i++) {
            PackageImg expected = imgs.get(i);
            PackageImg actual = savedImgs.get(i);

            Assertions.assertEquals(expected.getFilename(), actual.getFilename());
            Assertions.assertEquals(expected.getType(), actual.getType());
        }
    }

    @Test
    @DisplayName("성공 : Package 조회")
    void get() {

        //given
        Packages packages = savePackages();

        packagesRepository.save(packages);

        //when
        PackagesResponseDto response = packagesService.getPacakges(packages.getId());

        //then
        assertNotNull(response);
        assertEquals(111122223333L, response.trackingNo());
        assertEquals(response.images().size(),2);
    }

    @Test
    @DisplayName("실패 : 존재하지 않는 Package 조회 시도")
    void notExistPackageGet() {

        //given
        Packages packages = savePackages();

        packagesRepository.save(packages);

        //when
        PackagesResponseDto response = packagesService.getPacakges(packages.getId());
        //expected
        PackageException exception = assertThrows(PackageException.class, () -> {
            packagesService.getPacakges(packages.getId() + 1L);
        });
        assertEquals(exception.getErrorCode(), PACKAGE_NOT_EXIST);
    }

    @Test
    @DisplayName("성공 : Package 수정")
    void update() {

        //given
        Packages packages = savePackages();
        packagesRepository.save(packages);

        PackageImg img3 = PackageImg.builder()
                .filename("image3.png")
                .type("PKG")
                .build();
        PackageImg img4 = PackageImg.builder()
                .filename("image4.png")
                .type("PKG")
                .build();

        long updateTrackingNo = 333322221111L;
        List<PackageImg> updateImgs = List.of(img3, img4);

        PackagesUpdateRequestDto updatePackageRequest = PackagesUpdateRequestDto.builder()
                .trackingNo(updateTrackingNo)
                .images(updateImgs)
                .build();

        //when
       packagesService.updatePackages(packages.getId(), updatePackageRequest);

        //then
        Packages updatePackage = packagesRepository.findById(packages.getId())
                .orElseThrow(() -> new PackageException(PACKAGE_NOT_EXIST, "존재하지 않는 패키지입니다."));

        assertEquals(updatePackageRequest.trackingNo(),updatePackage.getTrackingNo());
        for (int i = 0; i < updatePackage.getImages().size(); i++) {
            PackageImg expected = updatePackage.getImages().get(i);
            PackageImg actual = updateImgs.get(i);

            Assertions.assertEquals(expected.getFilename(), actual.getFilename());
            Assertions.assertEquals(expected.getType(), actual.getType());
        }
    }

    @Test
    @DisplayName("실패 : 존재하지 않는 Package 수정 시도")
    void updateFail() {

        //given
        Packages packages = savePackages();
        packagesRepository.save(packages);

        PackageImg img3 = PackageImg.builder()
                .filename("image3.png")
                .type("PKG")
                .build();
        PackageImg img4 = PackageImg.builder()
                .filename("image4.png")
                .type("PKG")
                .build();
        List<PackageImg> updateImgs = List.of(img3, img4);

        PackagesUpdateRequestDto updatePackageRequest = PackagesUpdateRequestDto.builder()
                .trackingNo(333322221111L)
                .images(updateImgs)
                .build();

        //when
        packagesService.updatePackages(packages.getId(), updatePackageRequest);

        PackageException exception = assertThrows(PackageException.class, () -> {
            packagesService.updatePackages(packages.getId() + 1L,updatePackageRequest);
        });
        assertEquals(exception.getErrorCode(), PACKAGE_NOT_EXIST);
    }

    @Test
    @DisplayName("성공 : 패키지 삭제")
    void delete() {

        //given
        Packages packages = savePackages();
        packagesRepository.save(packages);

        //when
        packagesService.deletePackages(packages.getId());

        //then
        assertEquals(packagesRepository.count(),0);
    }

    @Test
    @DisplayName("실패 : 존재하지 않는 패키지 삭제 시도")
    void deleteFail() {

        //given
        Packages packages = savePackages();
        packagesRepository.save(packages);

        //expected
        assertThrows(PackageException.class, () -> {
            packagesService.deletePackages(packages.getId() + 1);
        });
    }


    private static Packages savePackages() {
        PackageImg img1 = PackageImg.builder()
                .filename("image1.png")
                .type("PKG")
                .build();
        PackageImg img2 = PackageImg.builder()
                .filename("image2.png")
                .type("PKG")
                .build();
        List<PackageImg> imgs = List.of(img1, img2);

        Packages packages = Packages.builder()
                .trackingNo(111122223333L)
                .images(imgs)
                .build();
        return packages;
    }


}

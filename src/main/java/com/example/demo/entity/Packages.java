package com.example.demo.entity;

import com.example.demo.dto.PackagesUpdateRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Packages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long trackingNo;

    @Builder.Default
    @OneToMany(mappedBy = "packages", cascade = CascadeType.ALL)
    private List<PackageImg> images = new ArrayList<>();


    public void update(PackagesUpdateRequestDto dto) {
        trackingNo = dto.trackingNo();
        ArrayList<PackageImg> update = new ArrayList<>();
        for (PackageImg packageImg : dto.images()) {
            PackageImg setImg = new PackageImg(packageImg.getFilename(), packageImg.getType(), this);
            update.add(setImg);
        }
        images = update;
    }

    public void setPackageImg(List<PackageImg> packageImgs) {
        ArrayList<PackageImg> update = new ArrayList<>();
        for (PackageImg packageImg : packageImgs) {
            PackageImg setImg = new PackageImg(packageImg.getFilename(), packageImg.getType(), this);
            update.add(setImg);
        }
        this.images = update;
    }



}

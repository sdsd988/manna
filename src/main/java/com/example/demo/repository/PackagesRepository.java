package com.example.demo.repository;

import com.example.demo.entity.Packages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface PackagesRepository extends JpaRepository<Packages, Long> {

}

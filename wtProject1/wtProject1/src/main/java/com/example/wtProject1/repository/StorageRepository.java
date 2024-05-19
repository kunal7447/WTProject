package com.example.wtProject1.repository;

import com.example.wtProject1.entity.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface StorageRepository extends JpaRepository<ImageData, Long> {


Optional<ImageData> findByName(String filename);




}

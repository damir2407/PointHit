package com.example.weblabb4.repository;

import com.example.weblabb4.entity.PointEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointRepo extends JpaRepository<PointEntity, Integer> {

    Page<PointEntity> getAllByUsername(String username, Pageable pageable);

    void deleteAllByUsername(String username);


}

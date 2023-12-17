package com.example.weblabb4.service;

import com.example.weblabb4.dto.request.PointRequest;
import com.example.weblabb4.entity.PointEntity;
import com.example.weblabb4.repository.PointRepo;
import com.example.weblabb4.util.PointCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class PointService {

    private final PointRepo pointRepo;
    private final PointCreator pointCreator;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public PointService(PointRepo pointRepo, PointCreator pointCreator) {
        this.pointRepo = pointRepo;
        this.pointCreator = pointCreator;
    }

    public void addPoint(PointRequest pointRequest, String username) {
        Long executeTimeStart = System.nanoTime();
        PointEntity pointEntity = pointCreator.createPoint(pointRequest, executeTimeStart);
        pointEntity.setUsername(username);
        savePoint(pointEntity);
    }

    public void savePoint(PointEntity pointEntity) {
        log.info("Сохранение точки: " + pointEntity);
        pointRepo.save(pointEntity);
    }

    public Page<PointEntity> getAllPointsByUsername(String username, int page, int size) {
        log.info("Получение точек");
        Pageable pageable = PageRequest.of(page, size);
        return pointRepo.getAllByUsername(username, pageable);
    }

    @Transactional
    public void deleteAllPointsByUsername(String username) {
        log.info("Удаление точек");
        pointRepo.deleteAllByUsername(username);
    }

}

package com.example.weblabb4.service;

import com.example.weblabb4.entity.PointEntity;
import com.example.weblabb4.repository.PointRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class PointService {

    private final PointRepo pointRepo;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public PointService(PointRepo pointRepo) {
        this.pointRepo = pointRepo;
    }


    public void savePoint(PointEntity pointEntity) {
        log.info("Сохранение точки: " + pointEntity);
        pointRepo.save(pointEntity);
    }

    public List<PointEntity> getAllPointsByUsername(String username) {
        log.info("Получение точек");
        List<PointEntity> returnedList = pointRepo.getAllByUsername(username);
        returnedList.sort(Comparator.comparing(PointEntity::getCurrentTime));
        Collections.reverse(returnedList);
        return returnedList;
    }

    @Transactional
    public void deleteAllPointsByUsername(String username) {
        log.info("Удаление точек");
        pointRepo.deleteAllByUsername(username);
    }

}

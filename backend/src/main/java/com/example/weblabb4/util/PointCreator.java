package com.example.weblabb4.util;

import com.example.weblabb4.entity.PointEntity;
import com.example.weblabb4.dto.request.PointRequest;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class PointCreator {

    private final AreaChecker areaChecker;

    public PointCreator(AreaChecker areaChecker) {
        this.areaChecker = areaChecker;
    }

    public PointEntity createPoint(PointRequest pointRequest, Long executeTimeStart) throws NumberFormatException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = dateFormat.format(new Date());

        Boolean hitValue = (areaChecker.checkHit(
            pointRequest.getX(),
            pointRequest.getY(),
            pointRequest.getR()
        ));

        long executeTimeFinish = System.nanoTime();
        String executeTime = String.format("%.7f", (executeTimeFinish - executeTimeStart) / Math.pow(10, 9));

        return new PointEntity(
            pointRequest.getX(),
            pointRequest.getY(),
            pointRequest.getR(),
            currentTime,
            executeTime,
            hitValue);

    }
}

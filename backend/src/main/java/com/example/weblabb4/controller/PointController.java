package com.example.weblabb4.controller;

import com.example.weblabb4.config.jwt.JwtFilter;
import com.example.weblabb4.config.jwt.JwtProvider;
import com.example.weblabb4.entity.PointEntity;
import com.example.weblabb4.dto.request.PointRequest;
import com.example.weblabb4.service.PointService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/points")
@Validated
public class PointController {

    private final PointService pointService;
    private final JwtProvider jwtProvider;
    private final JwtFilter jwtFilter;

    public PointController(
        PointService pointService,
        JwtProvider jwtProvider,
        JwtFilter jwtFilter) {
        this.pointService = pointService;
        this.jwtProvider = jwtProvider;
        this.jwtFilter = jwtFilter;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping
    public void addPoint(@RequestBody PointRequest pointRequest, HttpServletRequest httpServletRequest) {

        String username = jwtProvider.getUsernameFromToken(jwtFilter.getTokenFromRequest(httpServletRequest));
        pointService.addPoint(pointRequest, username);
    }

    @GetMapping
    private Page<PointEntity> getPoints(
        HttpServletRequest httpServletRequest,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "30") int size
    ) {
        String username = jwtProvider.getUsernameFromToken(jwtFilter.getTokenFromRequest(httpServletRequest));
        return pointService.getAllPointsByUsername(username, page, size);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    private void deletePoints(HttpServletRequest httpServletRequest) {

        String username = jwtProvider.getUsernameFromToken(jwtFilter.getTokenFromRequest(httpServletRequest));
        pointService.deleteAllPointsByUsername(username);
    }

}

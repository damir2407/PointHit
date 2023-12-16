package com.example.weblabb4.controller;

import com.example.weblabb4.config.jwt.JwtFilter;
import com.example.weblabb4.config.jwt.JwtProvider;
import com.example.weblabb4.entity.PointEntity;
import com.example.weblabb4.requests.PointRequest;
import com.example.weblabb4.service.PointService;
import com.example.weblabb4.util.PointCreator;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/points")
@Validated
public class PointController {

    private final PointService pointService;
    private final JwtProvider jwtProvider;
    private final JwtFilter jwtFilter;
    private final PointCreator pointCreator;

    public PointController(
        PointService pointService,
        JwtProvider jwtProvider,
        JwtFilter jwtFilter,
        PointCreator pointCreator
    ) {
        this.pointService = pointService;
        this.jwtProvider = jwtProvider;
        this.jwtFilter = jwtFilter;
        this.pointCreator = pointCreator;
    }

    @PostMapping
    public ResponseEntity<String> addPoint(@RequestBody PointRequest pointRequest,
        BindingResult bindingResult,
        HttpServletRequest httpServletRequest
    ) {

        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            String errorMessage = "";
            for (FieldError error : errors) {
                errorMessage = errorMessage + error.getDefaultMessage() + '\n';
            }
            return ResponseEntity.badRequest().body(errorMessage);
        }
        try {
            Long executeTimeStart = System.nanoTime();
            PointEntity pointEntity = pointCreator.createPoint(pointRequest, executeTimeStart);
            String username = jwtProvider.getUsernameFromToken(jwtFilter.getTokenFromRequest(httpServletRequest));
            pointEntity.setUsername(username);
            pointService.savePoint(pointEntity);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Coordinates must be numbers!");
        } catch (ExpiredJwtException expiredJwtException) {
            return ResponseEntity.badRequest().body("Expired JWT token.");
        } catch (UnsupportedJwtException unsupportedJwtException) {
            return ResponseEntity.badRequest().body("Unsupported JWT token.");
        } catch (MalformedJwtException malformedJwtException) {
            return ResponseEntity.badRequest().body("Invalid JWT token.");
        } catch (SignatureException signatureException) {
            return ResponseEntity.badRequest().body("Invalid JWT signature.");
        } catch (IllegalArgumentException illegalArgumentException) {
            return ResponseEntity.badRequest().body("JWT token compact of handler are invalid.");
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    private ResponseEntity<Object> getPoints(HttpServletRequest httpServletRequest) {
        try {
            String username = jwtProvider.getUsernameFromToken(jwtFilter.getTokenFromRequest(httpServletRequest));
            return new ResponseEntity<>(pointService.getAllPointsByUsername(username), HttpStatus.OK);
        } catch (ExpiredJwtException expiredJwtException) {
            return ResponseEntity.badRequest().body("Expired JWT token.");
        } catch (UnsupportedJwtException unsupportedJwtException) {
            return ResponseEntity.badRequest().body("Unsupported JWT token.");
        } catch (MalformedJwtException malformedJwtException) {
            return ResponseEntity.badRequest().body("Invalid JWT token.");
        } catch (SignatureException signatureException) {
            return ResponseEntity.badRequest().body("Invalid JWT signature.");
        } catch (IllegalArgumentException illegalArgumentException) {
            return ResponseEntity.badRequest().body("JWT token compact of handler are invalid.");
        }
    }

    @DeleteMapping
    private ResponseEntity<Object> deletePoints(HttpServletRequest httpServletRequest) {
        try {
            String username = jwtProvider.getUsernameFromToken(jwtFilter.getTokenFromRequest(httpServletRequest));
            pointService.deleteAllPointsByUsername(username);
            return new ResponseEntity<>("All points successfully deleted", HttpStatus.OK);
        } catch (ExpiredJwtException expiredJwtException) {
            return ResponseEntity.badRequest().body("Expired JWT token.");
        } catch (UnsupportedJwtException unsupportedJwtException) {
            return ResponseEntity.badRequest().body("Unsupported JWT token.");
        } catch (MalformedJwtException malformedJwtException) {
            return ResponseEntity.badRequest().body("Invalid JWT token.");
        } catch (SignatureException signatureException) {
            return ResponseEntity.badRequest().body("Invalid JWT signature.");
        } catch (IllegalArgumentException illegalArgumentException) {
            return ResponseEntity.badRequest().body("JWT token compact of handler are invalid.");
        }
    }

}

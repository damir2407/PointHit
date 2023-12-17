package com.example.weblabb4.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "point")
public class PointEntity {

    @Id
    @SequenceGenerator(name = "pointSequence", sequenceName = "POINT_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pointSequence")
    private Long id;
    private Double x;
    private Double y;
    private Double r;
    private String currentTime;
    private String executeTime;
    private Boolean hitValue;
    private String username;


    public PointEntity(Double x, Double y, Double r, String currentTime, String executeTime, boolean hitValue) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.currentTime = currentTime;
        this.executeTime = executeTime;
        this.hitValue = hitValue;
    }

}

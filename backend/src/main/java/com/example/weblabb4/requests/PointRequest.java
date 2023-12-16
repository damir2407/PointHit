package com.example.weblabb4.requests;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PointRequest {

    @NotBlank(message = "X coordinate can't be NULL")
    @Min(value = -4, message = "X coordinate must be between [-4;4]")
    @Max(value = 4, message = "X coordinate must be between  [-4;4]")
    private String x;

    @Min(value = -3, message = "Y coordinate must be between [-3;3]")
    @Max(value = 3, message = "Y coordinate must be between  [-3;3]")
    @NotBlank(message = "Y coordinate can't be NULL")
    private String y;

    @Min(value = -4, message = "R coordinate must be between [-4;4]")
    @Max(value = 4, message = "R coordinate must be between  [-4;4]")
    @NotBlank(message = "R coordinate can't be NULL")
    private String r;
}

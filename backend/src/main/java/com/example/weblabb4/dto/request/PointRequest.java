package com.example.weblabb4.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PointRequest {

    @NotNull(message = "X coordinate can't be NULL")
    @Min(value = -4, message = "X coordinate must be between [-4;4]")
    @Max(value = 4, message = "X coordinate must be between  [-4;4]")
    private Double x;

    @Min(value = -3, message = "Y coordinate must be between [-3;3]")
    @Max(value = 3, message = "Y coordinate must be between  [-3;3]")
    @NotNull(message = "Y coordinate can't be NULL")
    private Double y;

    @Min(value = -4, message = "R coordinate must be between [-4;4]")
    @Max(value = 4, message = "R coordinate must be between  [-4;4]")
    @NotNull(message = "R coordinate can't be NULL")
    private Double r;
}

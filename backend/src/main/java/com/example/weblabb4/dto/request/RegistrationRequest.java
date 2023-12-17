package com.example.weblabb4.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegistrationRequest {

    @Size(min = 1, max = 15, message = "Username should not be null or empty and exceed 15 characters")
    @NotBlank(message = "Username can't be NULL")
    private String username;

    @NotBlank(message = "Password can't be NULL")
    @Size(min = 1, max = 15, message = "Password should not be null or empty and exceed 15 characters")
    private String password;
}

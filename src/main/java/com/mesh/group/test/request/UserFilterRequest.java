package com.mesh.group.test.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFilterRequest {
    @NotNull(message = "Date Of Birth can't be null")
    private LocalDate dateOfBirth;
    @Size(min = 13, max = 13, message = "Phone should be 13 digits")
    private String phone;
    @NotNull(message = "Name can't be null")
    private String name;
    @Pattern(regexp = "^(.+)@(\\\\S+)$", message = "Invalid email")
    private String email;
}

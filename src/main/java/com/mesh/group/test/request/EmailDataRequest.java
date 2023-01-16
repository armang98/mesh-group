package com.mesh.group.test.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailDataRequest {
    @Pattern(regexp = "^(.+)@(\\\\S+)$", message = "Invalid email")
    private String email;
}

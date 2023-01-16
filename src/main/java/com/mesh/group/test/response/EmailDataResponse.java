package com.mesh.group.test.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailDataResponse {
    private Long id;
    private UserResponse user;
    private String email;
}

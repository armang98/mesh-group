package com.mesh.group.test.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneDataUpdateRequest {
    @Size(min = 13, max = 13, message = "Phone should be 7 digits")
    private String oldPhone;
    @Size(min = 13, max = 13, message = "Phone should be 7 digits")
    private String newPhone;
}

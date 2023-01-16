package com.mesh.group.test.resource;

import com.mesh.group.test.request.UserFilterRequest;
import com.mesh.group.test.response.UserResponse;
import com.mesh.group.test.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
@Api(value = "Users API")
public class UserResource {

    private final UserService userService;

    @GetMapping
    @ApiOperation(value = "GET request to filter users by specific criteria", response = List.class)
    public List<UserResponse> filter(@RequestBody @Valid UserFilterRequest userFilterRequest) {
        return userService.filter(userFilterRequest);
    }
}

package com.mesh.group.test.service;

import com.mesh.group.test.model.User;
import com.mesh.group.test.request.UserFilterRequest;
import com.mesh.group.test.response.UserResponse;

import java.util.List;

public interface UserService {

    User loadLoggedInUser();

    List<UserResponse> filter(UserFilterRequest userFilterRequest);
}

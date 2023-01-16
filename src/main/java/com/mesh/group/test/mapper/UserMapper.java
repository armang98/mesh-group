package com.mesh.group.test.mapper;

import com.mesh.group.test.mapper.config.BaseMapper;
import com.mesh.group.test.model.User;
import com.mesh.group.test.request.UserRequest;
import com.mesh.group.test.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper implements BaseMapper<User, UserRequest, UserResponse> {
    private final ModelMapper mapper;

    @Override
    public User toEntity(UserRequest emailDataRequest) {
        return mapper.map(emailDataRequest, User.class);
    }

    @Override
    public UserResponse toResponse(User emailData) {
        return mapper.map(emailData, UserResponse.class);
    }
}

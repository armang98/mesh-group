package com.mesh.group.test.service.impl;

import com.mesh.group.test.mapper.UserMapper;
import com.mesh.group.test.model.QEmailData;
import com.mesh.group.test.model.QPhoneData;
import com.mesh.group.test.model.QUser;
import com.mesh.group.test.model.User;
import com.mesh.group.test.repository.UserRepository;
import com.mesh.group.test.request.UserFilterRequest;
import com.mesh.group.test.response.UserResponse;
import com.mesh.group.test.service.UserService;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDetailServiceImpl userDetailService;
    private final UserMapper mapper;

    @PersistenceContext
    private EntityManager em;

    @Override
    public User loadLoggedInUser() {
        return userDetailService.loadLoggedInUser();
    }

    @Override
    public List<UserResponse> filter(UserFilterRequest userFilterRequest) {
        final JPAQuery<User> jpaQuery = new JPAQuery<>(em);
        QUser qUser = QUser.user;
        JPAQuery<User> query = jpaQuery.from(qUser);
        if (isNotEmpty(userFilterRequest.getName())) {
            query.where(qUser.name.like(userFilterRequest.getName()));
        }
        if (userFilterRequest.getDateOfBirth() != null) {
            query.where(qUser.dateOfBirth.gt(userFilterRequest.getDateOfBirth()));
        }
        if (isNotEmpty(userFilterRequest.getEmail())) {
            QEmailData qEmailData = QEmailData.emailData;
            query.join(qEmailData).on(qEmailData.user.id.eq(qUser.id).and(qEmailData.email.eq(userFilterRequest.getEmail())));
        }
        if (isNotEmpty(userFilterRequest.getPhone())) {
            QPhoneData qPhoneData = QPhoneData.phoneData;
            query.join(qPhoneData).on(qPhoneData.user.id.eq(qUser.id).and(qPhoneData.phone.eq(userFilterRequest.getPhone())));
        }

        List<User> fetch = query.fetch();
        return fetch.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}

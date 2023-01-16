package com.mesh.group.test.service.impl;

import com.mesh.group.test.exception.PhoneDataExistsException;
import com.mesh.group.test.exception.PhoneDataLessCountException;
import com.mesh.group.test.exception.PhoneDataNotFoundException;
import com.mesh.group.test.mapper.PhoneDataMapper;
import com.mesh.group.test.model.PhoneData;
import com.mesh.group.test.model.User;
import com.mesh.group.test.repository.PhoneDataRepository;
import com.mesh.group.test.request.PhoneDataRequest;
import com.mesh.group.test.request.PhoneDataUpdateRequest;
import com.mesh.group.test.response.PhoneDataResponse;
import com.mesh.group.test.service.PhoneDataService;
import com.mesh.group.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhoneDataServiceImpl implements PhoneDataService {
    private final UserService userService;
    private final PhoneDataRepository phoneDataRepository;
    private final PhoneDataMapper mapper;
    
    @Override
    public PhoneDataResponse create(PhoneDataRequest phoneDataRequest) {
        User user = userService.loadLoggedInUser();
        boolean existsByPhone = phoneDataRepository.existsByPhone(phoneDataRequest.getPhone());
        if (existsByPhone) {
            throw new PhoneDataExistsException(phoneDataRequest.getPhone());
        }
        PhoneData phoneData = mapper.toEntity(phoneDataRequest);
        phoneData.setUser(user);
        PhoneData savedPhoneData = phoneDataRepository.save(phoneData);
        return mapper.toResponse(savedPhoneData);
    }

    @Override
    public PhoneDataResponse update(PhoneDataUpdateRequest phoneDataRequest) {
        boolean existsByPhone = phoneDataRepository.existsByPhone(phoneDataRequest.getNewPhone());
        if (existsByPhone) {
            throw new PhoneDataExistsException(phoneDataRequest.getNewPhone());
        }
        User user = userService.loadLoggedInUser();

        PhoneData phoneData = phoneDataRepository.findByPhoneAndUser_Id(phoneDataRequest.getOldPhone(), user.getId())
                .orElseThrow(() -> new PhoneDataNotFoundException(phoneDataRequest.getOldPhone(), user.getId()));
        phoneData.setPhone(phoneDataRequest.getNewPhone());
        phoneDataRepository.save(phoneData);

        return mapper.toResponse(phoneData);
    }

    @Override
    public void delete(PhoneDataRequest phoneDataRequest) {
        User user = userService.loadLoggedInUser();
        int count = phoneDataRepository.countAllByUser_Id(user.getId());
        if (count <= 1) {
            throw new PhoneDataLessCountException(user.getId());
        }

        PhoneData phoneData = phoneDataRepository.findByPhoneAndUser_Id(phoneDataRequest.getPhone(), user.getId())
                .orElseThrow(() -> new PhoneDataNotFoundException(phoneDataRequest.getPhone(), user.getId()));
        phoneDataRepository.deleteById(phoneData.getId());
    }
}

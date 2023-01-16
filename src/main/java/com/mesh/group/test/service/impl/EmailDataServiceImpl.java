package com.mesh.group.test.service.impl;

import com.mesh.group.test.exception.EmailDataExistsException;
import com.mesh.group.test.exception.EmailDataLessCountException;
import com.mesh.group.test.exception.EmailDataNotFoundException;
import com.mesh.group.test.mapper.EmailDataMapper;
import com.mesh.group.test.model.EmailData;
import com.mesh.group.test.model.User;
import com.mesh.group.test.repository.EmailDataRepository;
import com.mesh.group.test.request.EmailDataRequest;
import com.mesh.group.test.request.EmailDataUpdateRequest;
import com.mesh.group.test.response.EmailDataResponse;
import com.mesh.group.test.service.EmailDataService;
import com.mesh.group.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailDataServiceImpl implements EmailDataService {
    private final UserService userService;
    private final EmailDataRepository emailDataRepository;
    private final EmailDataMapper mapper;

    @Override
    public EmailDataResponse create(EmailDataRequest emailDataRequest) {
        User user = userService.loadLoggedInUser();
        boolean existsByEmail = emailDataRepository.existsByEmail(emailDataRequest.getEmail());
        if (existsByEmail) {
            throw new EmailDataExistsException(emailDataRequest.getEmail());
        }
        EmailData emailData = mapper.toEntity(emailDataRequest);
        emailData.setUser(user);
        EmailData savedEmailData = emailDataRepository.save(emailData);
        return mapper.toResponse(savedEmailData);
    }

    @Override
    public EmailDataResponse update(EmailDataUpdateRequest emailDataRequest) {
        boolean existsByEmail = emailDataRepository.existsByEmail(emailDataRequest.getNewEmail());
        if (existsByEmail) {
            throw new EmailDataExistsException(emailDataRequest.getNewEmail());
        }
        User user = userService.loadLoggedInUser();

        EmailData emailData = emailDataRepository.findByEmailAndUser_Id(emailDataRequest.getOldEmail(), user.getId())
                .orElseThrow(() -> new EmailDataNotFoundException(emailDataRequest.getOldEmail(), user.getId()));
        emailData.setEmail(emailDataRequest.getNewEmail());
        emailDataRepository.save(emailData);

        return mapper.toResponse(emailData);
    }

    @Override
    public void delete(EmailDataRequest emailDataRequest) {
        User user = userService.loadLoggedInUser();
        int count = emailDataRepository.countAllByUser_Id(user.getId());
        if (count <= 1) {
            throw new EmailDataLessCountException(user.getId());
        }

        EmailData emailData = emailDataRepository.findByEmailAndUser_Id(emailDataRequest.getEmail(), user.getId())
                .orElseThrow(() -> new EmailDataNotFoundException(emailDataRequest.getEmail(), user.getId()));
        emailDataRepository.deleteById(emailData.getId());
    }
}

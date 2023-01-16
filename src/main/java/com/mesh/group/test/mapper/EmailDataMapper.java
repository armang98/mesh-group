package com.mesh.group.test.mapper;

import com.mesh.group.test.mapper.config.BaseMapper;
import com.mesh.group.test.model.EmailData;
import com.mesh.group.test.request.EmailDataRequest;
import com.mesh.group.test.response.EmailDataResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailDataMapper implements BaseMapper<EmailData, EmailDataRequest, EmailDataResponse> {
    private final ModelMapper mapper;

    @Override
    public EmailData toEntity(EmailDataRequest emailDataRequest) {
        return mapper.map(emailDataRequest, EmailData.class);
    }

    @Override
    public EmailDataResponse toResponse(EmailData emailData) {
        return mapper.map(emailData, EmailDataResponse.class);
    }
}

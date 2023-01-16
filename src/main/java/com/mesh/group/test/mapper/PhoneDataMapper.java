package com.mesh.group.test.mapper;

import com.mesh.group.test.mapper.config.BaseMapper;
import com.mesh.group.test.model.PhoneData;
import com.mesh.group.test.request.PhoneDataRequest;
import com.mesh.group.test.response.PhoneDataResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhoneDataMapper implements BaseMapper<PhoneData, PhoneDataRequest, PhoneDataResponse> {
    private final ModelMapper mapper;

    @Override
    public PhoneData toEntity(PhoneDataRequest phoneDataRequest) {
        return mapper.map(phoneDataRequest, PhoneData.class);
    }

    @Override
    public PhoneDataResponse toResponse(PhoneData phoneData) {
        return mapper.map(phoneData, PhoneDataResponse.class);
    }
}

package com.mesh.group.test.service;

import com.mesh.group.test.request.PhoneDataRequest;
import com.mesh.group.test.request.PhoneDataUpdateRequest;
import com.mesh.group.test.response.PhoneDataResponse;

public interface PhoneDataService {
    PhoneDataResponse create(PhoneDataRequest phoneDataRequest);

    PhoneDataResponse update(PhoneDataUpdateRequest phoneDataRequest);

    void delete(PhoneDataRequest phoneDataRequest);
}

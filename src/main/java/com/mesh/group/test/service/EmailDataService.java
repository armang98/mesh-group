package com.mesh.group.test.service;

import com.mesh.group.test.request.EmailDataRequest;
import com.mesh.group.test.request.EmailDataUpdateRequest;
import com.mesh.group.test.response.EmailDataResponse;

public interface EmailDataService {
    EmailDataResponse create(EmailDataRequest emailDataRequest);

    EmailDataResponse update(EmailDataUpdateRequest emailDataRequest);

    void delete(EmailDataRequest emailDataRequest);
}

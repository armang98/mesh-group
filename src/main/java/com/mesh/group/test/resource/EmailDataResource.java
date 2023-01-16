package com.mesh.group.test.resource;

import com.mesh.group.test.request.EmailDataRequest;
import com.mesh.group.test.request.EmailDataUpdateRequest;
import com.mesh.group.test.response.EmailDataResponse;
import com.mesh.group.test.service.EmailDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/emails")
@Api(value = "Email Data API")
public class EmailDataResource {

    private final EmailDataService emailDataService;

    @PostMapping
    @ApiOperation(value = "POST request to create an email for the logged in user", response = EmailDataResponse.class)
    public ResponseEntity<EmailDataResponse> createEmail(@RequestBody EmailDataRequest emailDataRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(emailDataService.create(emailDataRequest));
    }

    @PutMapping
    @ApiOperation(value = "PUT request to update an existing email for the logged in user", response = EmailDataResponse.class)
    public ResponseEntity<EmailDataResponse> updateEmail(@RequestBody EmailDataUpdateRequest emailDataRequest) {
        return ResponseEntity.ok(emailDataService.update(emailDataRequest));
    }

    @DeleteMapping
    @ApiOperation(value = "DELETE request to delete an email for the logged in user")
    public void deleteEmail(@RequestBody EmailDataRequest emailDataRequest) {
        emailDataService.delete(emailDataRequest);
    }
}

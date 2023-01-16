package com.mesh.group.test.resource;

import com.mesh.group.test.request.PhoneDataRequest;
import com.mesh.group.test.request.PhoneDataUpdateRequest;
import com.mesh.group.test.response.PhoneDataResponse;
import com.mesh.group.test.service.PhoneDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/phones")
@Api(value = "Phone Data API")
public class PhoneDataResource {

    private final PhoneDataService phoneDataService;

    @PostMapping
    @ApiOperation(value = "POST request to create an phone for the logged in user", response = PhoneDataResponse.class)
    public ResponseEntity<PhoneDataResponse> createPhone(@RequestBody PhoneDataRequest phoneDataRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(phoneDataService.create(phoneDataRequest));
    }

    @PutMapping
    @ApiOperation(value = "PUT request to update an existing phone for the logged in user", response = PhoneDataResponse.class)
    public ResponseEntity<PhoneDataResponse> updatePhone(@RequestBody PhoneDataUpdateRequest phoneDataRequest) {
        return ResponseEntity.ok(phoneDataService.update(phoneDataRequest));
    }

    @DeleteMapping
    @ApiOperation(value = "DELETE request to delete an phone for the logged in user")
    public void deletePhone(@RequestBody PhoneDataRequest phoneDataRequest) {
        phoneDataService.delete(phoneDataRequest);
    }
}

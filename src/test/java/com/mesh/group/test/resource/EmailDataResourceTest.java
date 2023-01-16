package com.mesh.group.test.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mesh.group.test.request.EmailDataRequest;
import com.mesh.group.test.request.EmailDataUpdateRequest;
import com.mesh.group.test.service.impl.AccountServiceScheduler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MockBean(AccountServiceScheduler.class)
public class EmailDataResourceTest {
    private static final String USER_TEST = "test@test.ru";
    private static final String USER_TEST_NEW = "test@new.ru";
    private static final String USER_TEST_DELETE = "test@delete.ru";
    private static final String USER_TEST_2 = "test2@test.ru";
    private static final String USER_TEST_3 = "test3@test.ru";
    private static final String USER_PASS = "test";

    @Container
    private static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:11.1");
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private ObjectMapper objectMapper;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", database::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", database::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", database::getPassword);
    }

    @Test
    @WithMockUser(username = USER_TEST_2, password = USER_PASS)
    public void emailData_createEmailData() throws Exception {
        EmailDataRequest emailDataRequest = getEmailDataRequest();
        String emailDataJson = objectMapper.writeValueAsString(emailDataRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/emails")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emailDataJson))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = USER_TEST, password = USER_PASS)
    public void emailData_updateEmailData() throws Exception {
        EmailDataUpdateRequest emailDataUpdateRequest = getEmailDataUpdateRequest();
        String emailDataJson = objectMapper.writeValueAsString(emailDataUpdateRequest);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/v1/emails")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emailDataJson))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertTrue(mvcResult.getResponse().getContentAsString().contains(USER_TEST_NEW));
    }

    @Test
    @WithMockUser(username = USER_TEST_2, password = USER_PASS)
    public void emailData_deleteEmail() throws Exception {
        EmailDataRequest emailDataRequest = getEmailDataRequest();
        emailDataRequest.setEmail(USER_TEST_DELETE);
        String emailDataJson = objectMapper.writeValueAsString(emailDataRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/emails")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emailDataJson))
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/emails")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emailDataJson))
                .andExpect(status().isOk());
    }

    private EmailDataRequest getEmailDataRequest() {
        return EmailDataRequest.builder()
                .email(USER_TEST_3)
                .build();
    }

    private EmailDataUpdateRequest getEmailDataUpdateRequest() {
        return EmailDataUpdateRequest.builder()
                .oldEmail(USER_TEST)
                .newEmail(USER_TEST_NEW)
                .build();
    }
}
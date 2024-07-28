package gr.pwc.assignment.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.pwc.assignment.AbstractTestContainer;
import gr.pwc.assignment.AssignmentApplication;
import gr.pwc.assignment.entities.Operator;
import gr.pwc.assignment.entities.Role;
import gr.pwc.assignment.models.OperatorRequest;
import gr.pwc.assignment.repositories.OperatorRepository;
import gr.pwc.assignment.repositories.RoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Set;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {AssignmentApplication.class})
@AutoConfigureMockMvc
public class OperatorControllerTests extends AbstractTestContainer {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private static Operator savedOperator;

    @BeforeEach
    public void beforeEach() {
        Role roleOperator = new Role();
        roleOperator.setName("ROLE_OPERATOR");
        roleRepository.save(roleOperator);
        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");
        roleRepository.save(roleAdmin);

        Operator operator = new Operator();
        operator.setName("test operator name");
        operator.setUsername("test operator username");
        operator.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
        operator.setRoles(Set.of(roleOperator));
        savedOperator = operatorRepository.save(operator);
    }

    @AfterEach
    public void afterEach() {
        operatorRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    public void addOperatorUnauthorized() throws Exception {

        OperatorRequest operatorRequest = new OperatorRequest();
        operatorRequest.setName("test request operator name");
        operatorRequest.setUsername("test request operator username");
        operatorRequest.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
        operatorRequest.setRole("ROLE_OPERATOR");

        this.mockMvc.perform(
                        post("/api/v1/operators/add")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(operatorRequest)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "OPERATOR")
    public void addOperatorForbidden() throws Exception {

        OperatorRequest operatorRequest = new OperatorRequest();
        operatorRequest.setName("test request operator name");
        operatorRequest.setUsername("test request operator username");
        operatorRequest.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
        operatorRequest.setRole("ROLE_OPERATOR");

        this.mockMvc.perform(
                        post("/api/v1/operators/add")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(operatorRequest)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    public void addOperatorAuthorized() throws Exception {

        OperatorRequest operatorRequest = new OperatorRequest();
        operatorRequest.setName("test request operator name");
        operatorRequest.setUsername("test request operator username");
        operatorRequest.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
        operatorRequest.setRole("ROLE_OPERATOR");

        this.mockMvc.perform(
                        post("/api/v1/operators/add")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(operatorRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(operatorRequest.getName()))
                .andExpect(jsonPath("$.username").value(operatorRequest.getUsername()))
                .andExpect(jsonPath("$.roles[0].name").value(operatorRequest.getRole()));
    }

    @Test
    public void removeOperatorUnauthorized() throws Exception {
        this.mockMvc.perform(
                        delete("/api/v1/operators/" + savedOperator.getId().toString() + "/remove"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "OPERATOR")
    public void removeOperatorForbidden() throws Exception {
        this.mockMvc.perform(
                        delete("/api/v1/operators/" + savedOperator.getId().toString() + "/remove"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    public void removeOperatorAuthorized() throws Exception {

        this.mockMvc.perform(
                        delete("/api/v1/operators/" + savedOperator.getId().toString() + "/remove"))
                .andDo(print())
                .andExpect(status().isOk());
    }


}

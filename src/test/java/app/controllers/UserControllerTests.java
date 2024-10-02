package app.controllers;

import app.entities.User;
import app.repositories.UserRepository;
import app.AbstractTestContainer;
import app.AssignmentApplication;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {AssignmentApplication.class})
@AutoConfigureMockMvc
public class UserControllerTests extends AbstractTestContainer {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private static User savedUser;

    @BeforeEach
    public void beforeEach() {
        User user = new User();
        user.setUserId(UUID.randomUUID());
        user.setName(UUID.randomUUID().toString());
        user.setBalance(100L);
        user.setAddress(UUID.randomUUID().toString());
        user.setCounterId(12345L);
        user.setVat(346746L);
        user.setDateOfBirth("01/01/1990");
        user.setPhone("+306944556677");
        user.setPostCode("145145");
        savedUser = userRepository.save(user);
    }

    @AfterEach
    public void afterEach() {
        userRepository.deleteAll();
    }

    @Test
    public void listUsers() throws Exception {
        this.mockMvc.perform(
                        get("/api/v1/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", IsCollectionWithSize.hasSize(1)))
                .andExpect(jsonPath("$[0].userId").value(savedUser.getUserId().toString()))
                .andExpect(jsonPath("$[0].name").value(savedUser.getName()))
                .andExpect(jsonPath("$[0].balance").value(savedUser.getBalance()))
                .andExpect(jsonPath("$[0].address").value(savedUser.getAddress()))
                .andExpect(jsonPath("$[0].counterId").value(savedUser.getCounterId().toString()))
                .andExpect(jsonPath("$[0].vat").value(savedUser.getVat()))
                .andExpect(jsonPath("$[0].dateOfBirth").value(savedUser.getDateOfBirth()))
                .andExpect(jsonPath("$[0].postCode").value(savedUser.getPostCode()));
    }

    @Test
    public void getUser() throws Exception {
        this.mockMvc.perform(
                        get("/api/v1/users/" + savedUser.getUserId().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(savedUser.getUserId().toString()))
                .andExpect(jsonPath("$.name").value(savedUser.getName()))
                .andExpect(jsonPath("$.balance").value(savedUser.getBalance()))
                .andExpect(jsonPath("$.address").value(savedUser.getAddress()))
                .andExpect(jsonPath("$.counterId").value(savedUser.getCounterId().toString()))
                .andExpect(jsonPath("$.vat").value(savedUser.getVat()))
                .andExpect(jsonPath("$.dateOfBirth").value(savedUser.getDateOfBirth()))
                .andExpect(jsonPath("$.postCode").value(savedUser.getPostCode()));
    }

    @Test
    public void getUserNotFound() throws Exception {
        this.mockMvc.perform(
                        get("/api/v1/users/" + UUID.randomUUID().toString()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void userBalanceIncrementUnauthorized() throws Exception {
        this.mockMvc.perform(
                        get("/api/v1/users/" + savedUser.getUserId().toString() + "/balance/increment"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "OPERATOR")
    public void userBalanceIncrementAuthorized() throws Exception {
        this.mockMvc.perform(
                        patch("/api/v1/users/" + savedUser.getUserId().toString() + "/balance/increment"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(savedUser.getUserId().toString()))
                .andExpect(jsonPath("$.balance").value(savedUser.getBalance() + 1));
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    public void userBalanceIncrementForbidden() throws Exception {
        this.mockMvc.perform(
                        patch("/api/v1/users/" + savedUser.getUserId().toString() + "/balance/increment"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void userBalanceDecrementUnauthorized() throws Exception {
        this.mockMvc.perform(
                        get("/api/v1/users/" + savedUser.getUserId().toString() + "/balance/increment"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "OPERATOR")
    public void userBalanceDecrementAuthorized() throws Exception {
        this.mockMvc.perform(
                        patch("/api/v1/users/" + savedUser.getUserId().toString() + "/balance/decrement"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(savedUser.getUserId().toString()))
                .andExpect(jsonPath("$.balance").value(savedUser.getBalance() - 1));
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    public void userBalanceDecrementBadRole() throws Exception {
        this.mockMvc.perform(
                        patch("/api/v1/users/" + savedUser.getUserId().toString() + "/balance/decrement"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}

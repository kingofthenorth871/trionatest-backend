package trionabackend.trionatestbackend.unittests;

import static org.hamcrest.Matchers.matchesPattern;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import trionabackend.trionatestbackend.controller.UserController;
import trionabackend.trionatestbackend.model.User;
import trionabackend.trionatestbackend.repository.UserRepository;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    private User testUserAll;

    @BeforeEach
    void setUp() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.JANUARY, 1);

        User testUser = new User();
        testUser.setName("John Doe");
        testUser.setAddress("123 Street");
        testUser.setCountrycode(47);
        testUser.setPhonenumber(1234567890);
        testUser.setBirthdate(calendar.getTime());

        testUserAll = testUser;
    }

    @Test
    void testNewUser() throws Exception {

        // Stub the behavior of userRepository.save()
        when(userRepository.save(any(User.class))).thenReturn(testUserAll);

        // Stub the behavior of userRepository.findById()
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(testUserAll));

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\",\"address\":\"123 Street\",\"countrycode\":\"47\",\"phonenumber\":\"1234567890\",\"birthdate\":\"2000-01-01\"}"))
                .andDo(print()) // Add this line to print the response content
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.address").value("123 Street"))
                .andExpect(jsonPath("$.countrycode").value(47))
                .andExpect(jsonPath("$.phonenumber").value(1234567890))
                .andExpect(jsonPath("$.birthdate", matchesPattern("^2000-01-01.*")));
    }

    @Test
    void testGetUserById() throws Exception {

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUserAll));

        mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.address").value("123 Street"))
                .andExpect(jsonPath("$.countrycode").value("47"))
                .andExpect(jsonPath("$.phonenumber").value("1234567890"))
                .andExpect(jsonPath("$.birthdate").value(matchesPattern("^2000-01-01.*")));
    }

    @Test
    void testUpdateUser() throws Exception {

        // Stub the behavior of userRepository.findById()
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUserAll));

        // Stub the behavior of userRepository.save()
        when(userRepository.save(any(User.class))).thenReturn(testUserAll);

        mockMvc.perform(put("/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Jane Doe\",\"address\":\"456 Avenue\",\"countrycode\":\"47\",\"phonenumber\":\"987654321\",\"birthdate\":\"2000-02-02\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.address").value("456 Avenue"))
                .andExpect(jsonPath("$.countrycode").value(47))
                .andExpect(jsonPath("$.phonenumber").value(987654321))
                .andExpect(jsonPath("$.birthdate").value("2000-02-02T00:00:00.000+00:00"));
    }

    @Test
    void testDeleteUser() throws Exception {

        when(userRepository.existsById(1L)).thenReturn(true);

        mockMvc.perform(delete("/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User with id 1 has been deleted success."));
    }

    @Test
    void testDeleteNonExistingUser() throws Exception {

        when(userRepository.existsById(1L)).thenReturn(false);

        mockMvc.perform(delete("/user/1"))
                .andExpect(status().isNotFound());
    }
}

package PS72021.WIA2.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)

public class UserControllerTest {
    private final String userId = "1";
    @Autowired
    private MockMvc mockMvc;

    @Test
    void user() throws Exception {
        this.mockMvc.perform(get("/users?userId=" + userId)).andExpect(status().isOk());
    }

    @Test
    void getUsers() throws Exception {
        this.mockMvc.perform(get("/users")).andExpect(status().isOk());
    }
}

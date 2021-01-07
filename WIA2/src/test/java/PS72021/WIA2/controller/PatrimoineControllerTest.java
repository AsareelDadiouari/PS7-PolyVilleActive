package PS72021.WIA2.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatrimoineController.class)
public class PatrimoineControllerTest {
    private final String patrimoineId = "1";
    private final String userId = "1";
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getPatrimoines() throws Exception {
        this.mockMvc.perform(get("/patrimoines")).andExpect(status().isOk());
    }

    @Test
    void addLike() throws Exception {
        this.mockMvc.perform(post("/patrimoines/" + patrimoineId + "/like/" + userId)).andExpect(status().isOk());

    }

    @Test
    void unLike() throws Exception {
        this.mockMvc.perform(post("/patrimoines/" + patrimoineId + "/unlike/" + userId)).andExpect(status().isOk());
    }
}

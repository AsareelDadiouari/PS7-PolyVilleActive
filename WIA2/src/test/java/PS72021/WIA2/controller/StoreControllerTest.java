package PS72021.WIA2.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StoreController.class)
public class StoreControllerTest {
    private final String storeId = "75531";
    private final String userId = "1";
    @Autowired
    private MockMvc mockMvc;

    @Test
    void stores() throws Exception {
        this.mockMvc.perform(get("/stores")).andExpect(status().isOk());
    }

    @Test
    void addLike() throws Exception {
        this.mockMvc.perform(post("/stores/" + storeId + "/like/" + userId)).andExpect(status().isOk());
    }

    @Test
    void unLike() throws Exception {
        this.mockMvc.perform(post("/stores/" + storeId + "/unlike/" + userId)).andExpect(status().isOk());
    }
}

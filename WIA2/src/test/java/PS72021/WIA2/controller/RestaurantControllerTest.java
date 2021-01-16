package PS72021.WIA2.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {
    private final String restaurantId = "72832";
    private final String userId = "1";
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getRestaurants() throws Exception {
        this.mockMvc.perform(get("/restaurants")).andExpect(status().isOk());
    }

    @Test
    void addLike() throws Exception {
        this.mockMvc.perform(post("/restaurants/" + restaurantId + "/like/" + userId)).andExpect(status().isOk());
    }

    @Test
    void unLike() throws Exception {
        this.mockMvc.perform(post("/restaurants/" + restaurantId + "/unlike/" + userId)).andExpect(status().isOk());
    }
}

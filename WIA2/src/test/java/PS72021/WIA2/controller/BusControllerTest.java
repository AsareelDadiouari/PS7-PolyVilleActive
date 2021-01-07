package PS72021.WIA2.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BusController.class)
public class BusControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getBus() throws Exception {
        this.mockMvc.perform(get("/bus")).andExpect(status().isOk());
    }

    @Test
    void getBusEvenement() throws Exception {
        double latitude = 43.7903291586, longitude = 7.20952126;
        this.mockMvc.perform(get("/busRecommandations?latitude=" + latitude + "&longitude=" + longitude)).andExpect(status().isOk());
    }


}

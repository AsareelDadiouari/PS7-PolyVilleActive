package PS72021.WIA2.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VisiteController.class)
public class VisitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getVisite() throws Exception {
        String date = "2021-01-07";
        this.mockMvc.perform(get("/visites?date=" + date)).andExpect(status().isOk());
    }

}

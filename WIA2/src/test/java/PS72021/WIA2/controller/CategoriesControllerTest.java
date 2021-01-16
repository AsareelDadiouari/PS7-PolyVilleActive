package PS72021.WIA2.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategorieController.class)
public class CategoriesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void categorie() throws Exception {
        String id = "1";
        this.mockMvc.perform(get("/categories?categorieId=" + id)).andExpect(status().isOk());
    }

    @Test
    void categories() throws Exception {
        this.mockMvc.perform(get("/categories")).andExpect(status().isOk());
    }
}

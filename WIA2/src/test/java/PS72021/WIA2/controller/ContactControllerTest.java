package PS72021.WIA2.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContactController.class)
public class ContactControllerTest {
    private final String userId = "1";
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getContactsRecommended() throws Exception {
        this.mockMvc.perform(get("/contactsRecommended/" + userId)).andExpect(status().isOk());
    }

    @Test
    void getContacts() throws Exception {
        this.mockMvc.perform(get("/contacts/" + userId)).andExpect(status().isOk());
    }
}

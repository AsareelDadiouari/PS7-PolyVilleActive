package PS72021.WIA2.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
public class EventControllerTest {
    private final String userId = "1";
    private final String eventId = "13148";
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getEvents() throws Exception {
        this.mockMvc.perform(get("/events/")).andExpect(status().isOk());
    }

    @Test
    void eventsWithInterests() throws Exception {
        this.mockMvc.perform(get("/eventsRecommandations?userId=" + userId)).andExpect(status().isOk());
    }

    @Test
    void joinEvent() throws Exception {
        this.mockMvc.perform(post("/events/" + eventId)).andExpect(status().is(201));
    }

    @Test
    void checkParticipant() throws Exception {
        this.mockMvc.perform(get("/events/" + eventId + "/user/" + userId)).andExpect(status().isOk());
    }

    @Test
    void addLike() throws Exception {
        this.mockMvc.perform(post("/events/" + eventId + "/like/" + userId)).andExpect(status().isOk());
    }

    @Test
    void unLike() throws Exception {
        this.mockMvc.perform(post("/events/" + eventId + "/unlike/" + userId)).andExpect(status().isOk());
    }
}

package PS72021.WIA2.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GroupController.class)
public class GroupControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private final String groupId = "1";
    private final String userId = "1";

    @Test
    void group() throws Exception {
        this.mockMvc.perform(get("/group?groupeId=" + groupId)).andExpect(status().isOk());
    }

    @Test
    void getGroups() throws Exception {
        this.mockMvc.perform(get("/groups?userId=" + userId)).andExpect(status().isOk());
    }

    @Test
    void getMyGroups() throws Exception {
        this.mockMvc.perform(get("/mygroups?userId=" + userId)).andExpect(status().isOk());
    }

    @Test
    void addMember() throws Exception {
        this.mockMvc.perform(post("/addmember?groupId=" + groupId + "&userId=" + userId)).andExpect(status().isOk());
    }

    @Test
    void removeMember() throws Exception {
        this.mockMvc.perform(post("/removemember?groupId=" + groupId + "&userId=" + userId)).andExpect(status().isOk());
    }

    @Test
    void recommendedGroup() throws Exception {
        this.mockMvc.perform(get("/recommendedGroups?userId=" + userId)).andExpect(status().isOk());
    }
}

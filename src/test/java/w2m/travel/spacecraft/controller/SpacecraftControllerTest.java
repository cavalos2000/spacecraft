package w2m.travel.spacecraft.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import w2m.travel.spacecraft.model.Spacecraft;
import w2m.travel.spacecraft.service.SpacecraftService;
import w2m.travel.spacecraft.web.SpacecraftController;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SpacecraftController.class)
public class SpacecraftControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpacecraftService spacecraftService;

    private Spacecraft spacecraft;

    @BeforeEach
    void setUp() {
        spacecraft = new Spacecraft();
        spacecraft.setId(1L);
        spacecraft.setName("Apollo");

    }

    @Test
    void getAllSpacecrafts() throws Exception {
        given(spacecraftService.findAll(any())).willReturn(Page.empty());

        mockMvc.perform(get("/api/spacecrafts")
                        .with(httpBasic("user", "pass"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getSpacecraft() throws Exception {
        given(spacecraftService.getSpacecraft(anyLong())).willReturn(spacecraft);

        mockMvc.perform(get("/api/spacecrafts/1")
                        .with(httpBasic("user", "pass"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Apollo"));
    }

    @Test
    void createSpacecraft() throws Exception {
        given(spacecraftService.saveSpacecraft(any(Spacecraft.class))).willReturn(spacecraft);

        mockMvc.perform(post("/api/spacecrafts")
                        .with(httpBasic("user", "pass"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Apollo\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Apollo"));
    }

    @Test
    void updateSpacecraft() throws Exception {
        given(spacecraftService.saveSpacecraft(any(Spacecraft.class))).willReturn(spacecraft);

        mockMvc.perform(put("/api/spacecrafts/1")
                        .with(httpBasic("user", "pass"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Apollo Updated\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Apollo Updated"));
    }

    @Test
    void deleteSpacecraft() throws Exception {
        mockMvc.perform(delete("/api/spacecrafts/1")
                        .with(httpBasic("user", "pass"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}


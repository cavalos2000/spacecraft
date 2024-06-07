package worktomeet.travel.spacecraft.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import worktomeet.travel.spacecraft.dto.SpacecraftDto;
import worktomeet.travel.spacecraft.dto.SpacecraftRequestDto;
import worktomeet.travel.spacecraft.service.SpacecraftService;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SpacecraftControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpacecraftService spacecraftService;

    private SpacecraftDto spacecraftDto;

    @BeforeEach
    void setUp() {
        spacecraftDto = new SpacecraftDto(1L,
                "Apollo",
                "model",
                LocalDate.now(),
                BigDecimal.valueOf(100));


    }

    @Test
    @WithMockUser(username = "user", password = "pass", roles = "USER")
    void getAllSpacecrafts() throws Exception {
        given(spacecraftService.findAll(any())).willReturn(Page.empty());

        mockMvc.perform(get("/api/spacecrafts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", password = "pass", roles = "USER")
    void getSpacecraft() throws Exception {
        given(spacecraftService.getSpacecraft(anyLong())).willReturn(spacecraftDto);

        mockMvc.perform(get("/api/spacecrafts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Apollo"))
                .andExpect(jsonPath("$.weight").value(100));
    }

    @Test
    @WithMockUser(username = "user", password = "pass", roles = "USER")
    void createSpacecraft() throws Exception {
        given(spacecraftService.createSpacecraft(any(SpacecraftRequestDto.class))).willReturn(spacecraftDto);

        mockMvc.perform(post("/api/spacecrafts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Apollo\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Apollo"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass", roles = "USER")
    void updateSpacecraft() throws Exception {
        SpacecraftDto spacecraft = new SpacecraftDto(1L, "Apollo Updated", null, null, null);

        given(spacecraftService.updatetSpacecraft(any(Long.class), any(SpacecraftRequestDto.class))).willReturn(spacecraft);

        mockMvc.perform(put("/api/spacecrafts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Apollo Updated\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Apollo Updated"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass", roles = "USER")
    void deleteSpacecraft() throws Exception {
        mockMvc.perform(delete("/api/spacecrafts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}


package w2m.travel.spacecraft.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
    public void givenPrivateEndpoint_whenNoAuth_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/any"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser(username = "user", password = "pass", roles = "USER")
    public void givenPrivateEndpoint_whenWithAuth_thenSuccess() throws Exception {
        mockMvc.perform(get("/api/spacecrafts"))
                .andExpect(status().isOk());
    }

}

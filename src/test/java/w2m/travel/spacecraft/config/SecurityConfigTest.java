package w2m.travel.spacecraft.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
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
    public void givenPublicEndpoint_whenGetRequest_thenSuccess() throws Exception {
        mockMvc.perform(get("/public/test"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenPrivateEndpoint_whenNoAuth_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/private/test"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void givenPrivateEndpoint_whenWithAuth_thenSuccess() throws Exception {
        mockMvc.perform(get("/private/test")
                        .with(httpBasic("user", "pass")))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void givenPrivateEndpoint_whenWithMockUser_thenSuccess() throws Exception {
        mockMvc.perform(get("/private/test"))
                .andExpect(status().isOk());
    }
}

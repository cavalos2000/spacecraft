package worktomeet.travel.spacecraft.mapper;

import org.junit.jupiter.api.Test;
import worktomeet.travel.spacecraft.dto.SpacecraftRequestDTO;
import worktomeet.travel.spacecraft.model.Spacecraft;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SpacecraftRequestDTOMapperTest {

    private final SpacecraftRequestDTOMapper mapper = SpacecraftRequestDTOMapper.INSTANCE;

    @Test
    void testToModel() {
        SpacecraftRequestDTO dto = new SpacecraftRequestDTO("name", "model", null, null);

        Spacecraft spacecraft = mapper.toModel(dto);
        assertNotNull(spacecraft);
        assertEquals(dto.getName(), spacecraft.getName());

    }
}


package worktomeet.travel.spacecraft.mapper;

import org.junit.jupiter.api.Test;
import worktomeet.travel.spacecraft.dto.SpacecraftRequestDto;
import worktomeet.travel.spacecraft.model.Spacecraft;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SpacecraftRequestDtoMapperTest {

    private final SpacecraftRequestDtoMapper mapper = SpacecraftRequestDtoMapper.instance;

    @Test
    void testToModel() {
        SpacecraftRequestDto dto = new SpacecraftRequestDto("name", "model", null, null);

        Spacecraft spacecraft = mapper.toModel(dto);
        assertNotNull(spacecraft);
        assertEquals(dto.getName(), spacecraft.getName());

    }
}


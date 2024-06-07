package worktomeet.travel.spacecraft.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import worktomeet.travel.spacecraft.dto.SpacecraftDto;
import worktomeet.travel.spacecraft.model.Spacecraft;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpacecraftMapperTest {

    private SpacecraftMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(SpacecraftMapper.class);
    }

    @Test
    void testToModel() {
        SpacecraftDto dto = new SpacecraftDto(1L,
                "Apollo",
                "model",
                LocalDate.now(),
                BigDecimal.valueOf(100));


        Spacecraft model = mapper.toModel(dto);
        assertNotNull(model);
        assertEquals(1L, model.getId());
        assertEquals("Apollo", model.getName());
    }

    @Test
    void testToDto() {
        Spacecraft model = new Spacecraft();
        model.setId(1L);
        model.setName("Apollo");

        SpacecraftDto dto = mapper.toDto(model);
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Apollo", dto.getName());
    }

    @Test
    void testToModelList() {
        SpacecraftDto dto1 = new SpacecraftDto(1L, "Apollo", "model", null, null);
        SpacecraftDto dto2 = new SpacecraftDto(2L, "Challenger", "model", null, null);


        List<Spacecraft> models = mapper.toModelList(Arrays.asList(dto1, dto2));
        assertNotNull(models);
        assertEquals(2, models.size());
        assertEquals("Apollo", models.get(0).getName());
        assertEquals("Challenger", models.get(1).getName());
    }

    @Test
    void testToDtoList() {
        Spacecraft model1 = new Spacecraft();
        model1.setId(1L);
        model1.setName("Apollo");

        Spacecraft model2 = new Spacecraft();
        model2.setId(2L);
        model2.setName("Challenger");

        List<SpacecraftDto> dtos = mapper.toDtoList(Arrays.asList(model1, model2));
        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals("Apollo", dtos.get(0).getName());
        assertEquals("Challenger", dtos.get(1).getName());
    }

    @Test
    void testToDtoPage() {
        Spacecraft model1 = new Spacecraft();
        model1.setId(1L);
        model1.setName("Apollo");

        Spacecraft model2 = new Spacecraft();
        model2.setId(2L);
        model2.setName("Challenger");

        Page<Spacecraft> modelPage = new PageImpl<>(Arrays.asList(model1, model2), PageRequest.of(0, 10), 2);
        Page<SpacecraftDto> dtoPage = mapper.toDtoPage(modelPage);

        assertNotNull(dtoPage);
        assertEquals(2, dtoPage.getTotalElements());
        assertEquals("Apollo", dtoPage.getContent().get(0).getName());
        assertEquals("Challenger", dtoPage.getContent().get(1).getName());
    }
}


package worktomeet.travel.spacecraft.service;

import com.google.common.cache.LoadingCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import worktomeet.travel.spacecraft.dto.SpacecraftDto;
import worktomeet.travel.spacecraft.dto.SpacecraftRequestDto;
import worktomeet.travel.spacecraft.model.Spacecraft;
import worktomeet.travel.spacecraft.repository.SpacecraftRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class SpacecraftServiceTest {

    @MockBean
    private SpacecraftRepository repository;

    @Autowired
    private SpacecraftService service;

    @MockBean
    private LoadingCache<Long, Spacecraft> spacecraftCache;

    private Spacecraft spacecraft;
    private SpacecraftRequestDto spacecraftRequestDto;

    @BeforeEach
    void setUp() {
        spacecraft = new Spacecraft();
        spacecraft.setId(1L);
        spacecraft.setName("Apollo");

        spacecraftRequestDto = new SpacecraftRequestDto("Apollo", "model", null, null);

        MockitoAnnotations.openMocks(this);

        when(spacecraftCache.getUnchecked(any(Long.class))).thenReturn(spacecraft);
        when(repository.findById(1L)).thenReturn(Optional.of(spacecraft));
        when(repository.save(any(Spacecraft.class))).thenReturn(spacecraft);
    }

    @Test
    void testGetSpacecraft() {
        SpacecraftDto dto = service.getSpacecraft(1L);
        assertNotNull(dto);
        assertEquals("Apollo", dto.getName());
    }

    @Test
    void testCreateSpacecraft() {
        SpacecraftDto dto = service.createSpacecraft(spacecraftRequestDto);
        assertNotNull(dto);
        assertEquals("Apollo", dto.getName());
    }

    @Test
    void testUpdateSpacecraft() {
        SpacecraftDto dto = service.updatetSpacecraft(1L, spacecraftRequestDto);
        assertNotNull(dto);
        assertEquals("Apollo", dto.getName());
    }

    @Test
    void testDeleteSpacecraft() {
        assertDoesNotThrow(() -> service.deleteSpacecraft(1L));
        verify(repository, times(1)).deleteById(1L);
    }

}

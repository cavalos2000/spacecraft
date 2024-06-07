package worktomeet.travel.spacecraft.service;


import com.google.common.cache.LoadingCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import worktomeet.travel.spacecraft.model.Spacecraft;
import worktomeet.travel.spacecraft.repository.SpacecraftRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SpacecraftServiceTest {

    @Mock
    private SpacecraftRepository repository;

    @InjectMocks
    private SpacecraftService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service.init();
    }

    @Test
    void init_shouldInitializeCache() throws ExecutionException {
        LoadingCache<Long, Spacecraft> cache = service.spacecraftCache;
        Spacecraft spacecraft = new Spacecraft();
        spacecraft.setId(1L);

        given(repository.findById(1L)).willReturn(Optional.of(spacecraft));

        assertEquals(spacecraft, cache.get(1L));
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void getSpacecraft_shouldReturnSpacecraft_whenIdExists() {
        Spacecraft spacecraft = new Spacecraft();
        spacecraft.setId(1L);

        given(repository.findById(1L)).willReturn(Optional.of(spacecraft));

        assertEquals(spacecraft.getId(), service.getSpacecraft(1L).getId());
    }

    @Test
    void getSpacecraft_shouldThrowException_whenIdDoesNotExist() {
        given(repository.findById(1L)).willReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.getSpacecraft(1L));
    }

    @Test
    void createSpacecraft_shouldThrowIllegalArgumentException_whenInputIsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.createSpacecraft(null));
    }

    @Test
    void deleteSpacecraft_shouldDeleteSpacecraft() {
        Spacecraft spacecraft = new Spacecraft();
        spacecraft.setId(1L);

        service.deleteSpacecraft(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void findAll_shouldReturnPageOfSpacecrafts() {
        Spacecraft spacecraft = new Spacecraft();
        spacecraft.setId(1L);
        List<Spacecraft> spacecrafts = Collections.singletonList(spacecraft);
        Page<Spacecraft> page = new PageImpl<>(spacecrafts);
        Pageable pageable = PageRequest.of(0, 10);

        given(repository.findAll(pageable)).willReturn(page);

        assertEquals(page.stream().findFirst().get().getId(), service.findAll("", pageable).stream().findFirst().get().getId());
        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    void findAll_shouldReturnPageOfSpacecraftsWithName() {
        Spacecraft spacecraft = new Spacecraft();
        spacecraft.setId(1L);
        spacecraft.setName("name");
        spacecraft.setModel("model");

        List<Spacecraft> spacecrafts = Arrays.asList(spacecraft);
        Page<Spacecraft> page = new PageImpl<>(spacecrafts);
        Pageable pageable = PageRequest.of(0, 10);

        given(repository.findByNameContaining("name", pageable)).willReturn(page);

        assertEquals(page.stream().findFirst().get().getId(), service.findAll("name", pageable).stream().findFirst().get().getId());
        verify(repository, times(1)).findByNameContaining("name", pageable);
    }
}


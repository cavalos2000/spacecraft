package worktomeet.travel.spacecraft.service;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import worktomeet.travel.spacecraft.aspect.CheckNegativeId;
import worktomeet.travel.spacecraft.dto.PageableDto;
import worktomeet.travel.spacecraft.dto.SpacecraftDto;
import worktomeet.travel.spacecraft.dto.SpacecraftRequestDto;
import worktomeet.travel.spacecraft.exception.InvalidSpacecraftDataException;
import worktomeet.travel.spacecraft.exception.SpacecraftNotFoundException;
import worktomeet.travel.spacecraft.mapper.SpacecraftMapper;
import worktomeet.travel.spacecraft.mapper.SpacecraftRequestDtoMapper;
import worktomeet.travel.spacecraft.model.Spacecraft;
import worktomeet.travel.spacecraft.repository.SpacecraftRepository;
import worktomeet.travel.spacecraft.util.PageableUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class SpacecraftService {

    @Autowired
    private Validator validator;
    @Autowired
    private SpacecraftRepository repository;
    LoadingCache<Long, Spacecraft> spacecraftCache;

    @PostConstruct
    public void init() {
        CacheBuilder<Object, Object> objectObjectCacheBuilder = CacheBuilder.newBuilder();
        objectObjectCacheBuilder.maximumSize(100);
        objectObjectCacheBuilder.expireAfterAccess(10, TimeUnit.MINUTES);
        spacecraftCache = objectObjectCacheBuilder
                .build(new CacheLoader<>() {
                    @Override
                    public Spacecraft load(Long id) {
                        return repository.findById(id)
                                .orElseThrow(() -> {
                                    log.error("Spacecraft not found for ID: {}", id);
                                    return new SpacecraftNotFoundException("Spacecraft not found for this ID :: " + id);
                                });
                    }
                });
    }

    @CheckNegativeId
    public SpacecraftDto getSpacecraft(Long id) {
        return SpacecraftMapper.instance.toDto(spacecraftCache.getUnchecked(id));
    }

    public SpacecraftDto createSpacecraft(SpacecraftRequestDto spacecraftRequestDto) {
        if (spacecraftRequestDto == null) {
            throw new InvalidSpacecraftDataException("SpacecraftDto cannot be null");
        }
        var violations = validator.validate(spacecraftRequestDto);
        if (!violations.isEmpty()) {
            throw new InvalidSpacecraftDataException(violations.iterator().next().getMessage());
        }

        Spacecraft spacecraft = SpacecraftRequestDtoMapper.instance.toModel(spacecraftRequestDto);
        Spacecraft savedSpacecraft = repository.save(spacecraft);

        return SpacecraftMapper.instance.toDto(savedSpacecraft);

    }
    public SpacecraftDto updatetSpacecraft(Long id, SpacecraftRequestDto spacecraftRequestDto) {
        var violations = validator.validate(spacecraftRequestDto);
        if (!violations.isEmpty()) {
            throw new InvalidSpacecraftDataException(violations.iterator().next().getMessage());
        }
        if (id == null) {
            throw new InvalidSpacecraftDataException("SpacecraftDto cannot be null");
        }
        Spacecraft spacecraft = SpacecraftRequestDtoMapper.instance.toModel(spacecraftRequestDto);
        spacecraft.setId(id);

        return SpacecraftMapper.instance.toDto(repository.save(spacecraft));
    }

    public void deleteSpacecraft(Long id) {
        spacecraftCache.invalidate(id);
        repository.deleteById(id);
    }

    public Page<SpacecraftDto> findAll(PageableDto pageable) {
        return SpacecraftMapper.instance.toDtoPage(repository.findAll(PageableUtil.createPageable(pageable)));
    }

    public List<SpacecraftDto> findByNameContaining(String name) {
        return SpacecraftMapper.instance.toDtoList(repository.findByNameContaining(name));
    }
}

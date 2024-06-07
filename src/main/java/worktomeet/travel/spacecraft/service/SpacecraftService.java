package worktomeet.travel.spacecraft.service;


import ch.qos.logback.core.util.StringUtil;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import worktomeet.travel.spacecraft.aspect.CheckNegativeId;
import worktomeet.travel.spacecraft.dto.SpacecraftDTO;
import worktomeet.travel.spacecraft.dto.SpacecraftRequestDTO;
import worktomeet.travel.spacecraft.exception.SpacecraftNotFoundException;
import worktomeet.travel.spacecraft.mapper.SpacecraftMapper;
import worktomeet.travel.spacecraft.mapper.SpacecraftRequestDTOMapper;
import worktomeet.travel.spacecraft.model.Spacecraft;
import worktomeet.travel.spacecraft.repository.SpacecraftRepository;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class SpacecraftService {

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
    public SpacecraftDTO getSpacecraft(Long id) {
        return SpacecraftMapper.INSTANCE.toDTO(spacecraftCache.getUnchecked(id));
    }

    public SpacecraftDTO createSpacecraft(SpacecraftRequestDTO spacecraftRequestDTO) {
        if (spacecraftRequestDTO == null) {
            throw new IllegalArgumentException("SpacecraftDTO cannot be null");
        }

        try {
            Spacecraft spacecraft = SpacecraftRequestDTOMapper.INSTANCE.toModel(spacecraftRequestDTO);
            Spacecraft savedSpacecraft = repository.save(spacecraft);
            SpacecraftDTO savedSpacecraftDTO = SpacecraftMapper.INSTANCE.toDTO(savedSpacecraft);

            return savedSpacecraftDTO;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create spacecraft", e);
        }
    }
    public SpacecraftDTO updatetSpacecraft(Long id, SpacecraftRequestDTO spacecraftRequestDTO) {
        if (id == null) {
            throw new IllegalArgumentException("SpacecraftDTO cannot be null");
        }
        Spacecraft spacecraft = SpacecraftRequestDTOMapper.INSTANCE.toModel(spacecraftRequestDTO);
        spacecraft.setId(id);

        return SpacecraftMapper.INSTANCE.toDTO(repository.save(spacecraft));
    }

    public void deleteSpacecraft(Long id) {
        spacecraftCache.invalidate(id);
        repository.deleteById(id);
    }

    public Page<SpacecraftDTO> findAll(String name, Pageable pageable) {
        if (StringUtil.isNullOrEmpty(name)) {
            return SpacecraftMapper.INSTANCE.toDTOPage(repository.findAll(pageable));
        } else {
            return SpacecraftMapper.INSTANCE.toDTOPage(repository.findByNameContaining(name, pageable));
        }
    }
}

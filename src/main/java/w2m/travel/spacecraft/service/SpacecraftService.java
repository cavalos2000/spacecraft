package w2m.travel.spacecraft.service;


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
import w2m.travel.spacecraft.aspect.CheckNegativeId;
import w2m.travel.spacecraft.exception.SpacecraftNotFoundException;
import w2m.travel.spacecraft.model.Spacecraft;
import w2m.travel.spacecraft.repository.SpacecraftRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class SpacecraftService {

    @Autowired
    private SpacecraftRepository repository;

    LoadingCache<Long, Spacecraft> spacecraftCache;
    private LoadingCache<String, List<Spacecraft>> spacecraftListCache;

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
    public Spacecraft getSpacecraft(Long id) {
        return spacecraftCache.getUnchecked(id);
    }

    public Spacecraft saveSpacecraft(Spacecraft spacecraft) {
        return repository.save(spacecraft);
    }

    public void deleteSpacecraft(Long id) {
        spacecraftCache.invalidate(id);
        repository.deleteById(id);
    }

    public Page<Spacecraft> findAll(String name, Pageable pageable) {
        if (StringUtil.isNullOrEmpty(name)) {
            return repository.findAll(pageable);
        } else {
            return repository.findByNameContaining(name, pageable);
        }
    }
}

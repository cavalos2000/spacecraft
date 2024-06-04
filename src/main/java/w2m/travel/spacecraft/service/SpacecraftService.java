package w2m.travel.spacecraft.service;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import w2m.travel.spacecraft.aspect.CheckNegativeId;
import w2m.travel.spacecraft.model.Spacecraft;
import w2m.travel.spacecraft.repository.SpacecraftRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SpacecraftService {

    @Autowired
    private SpacecraftRepository repository;

    LoadingCache<Long, Spacecraft> spacecraftCache;
    private LoadingCache<String, List<Spacecraft>> spacecraftListCache;

    @PostConstruct
    public void init() {
        spacecraftCache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .build(new CacheLoader<>() {
                    @Override
                    public Spacecraft load(Long id) {
                        return repository.findById(id).orElseThrow();
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

    public Page<Spacecraft> findAll(Pageable pageable) {
        return repository.findAll(pageable);

    }
}

package worktomeet.travel.spacecraft.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import worktomeet.travel.spacecraft.model.Spacecraft;

import java.util.List;

public interface SpacecraftRepository extends JpaRepository<Spacecraft, Long> {

    List<Spacecraft> findByNameContaining(String name);
}

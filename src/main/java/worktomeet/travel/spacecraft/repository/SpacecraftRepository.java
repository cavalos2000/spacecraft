package worktomeet.travel.spacecraft.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import worktomeet.travel.spacecraft.model.Spacecraft;

public interface SpacecraftRepository extends JpaRepository<Spacecraft, Long> {

    Page<Spacecraft> findByNameContaining(String name, Pageable pageable);
}

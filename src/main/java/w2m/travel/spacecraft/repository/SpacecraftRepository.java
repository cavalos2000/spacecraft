package w2m.travel.spacecraft.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import w2m.travel.spacecraft.model.Spacecraft;

public interface SpacecraftRepository extends JpaRepository<Spacecraft, Long> {
}

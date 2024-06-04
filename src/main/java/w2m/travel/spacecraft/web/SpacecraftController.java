package w2m.travel.spacecraft.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import w2m.travel.spacecraft.model.Spacecraft;
import w2m.travel.spacecraft.service.SpacecraftService;

@RestController
@RequestMapping("/api/spacecrafts")
public class SpacecraftController {

    @Autowired
    private SpacecraftService service;


    @GetMapping
    public Page<Spacecraft> getAllSpacecrafts(Pageable pageable) {
        return service.findAll(pageable);
    }


    @GetMapping("/{id}")
    public Spacecraft getSpacecraft(@PathVariable Long id) {
        return service.getSpacecraft(id);
    }


    @PostMapping
    public Spacecraft createSpacecraft(@RequestBody Spacecraft spacecraft) {
        return service.saveSpacecraft(spacecraft);
    }


    @PutMapping("/{id}")
    public Spacecraft updateSpacecraft(@PathVariable Long id, @RequestBody Spacecraft spacecraft) {
        spacecraft.setId(id);
        return service.saveSpacecraft(spacecraft);
    }


    @DeleteMapping("/{id}")
    public void deleteSpacecraft(@PathVariable Long id) {
        service.deleteSpacecraft(id);
    }
}


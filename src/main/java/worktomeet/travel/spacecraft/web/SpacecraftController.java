package worktomeet.travel.spacecraft.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import worktomeet.travel.spacecraft.dto.PageableDto;
import worktomeet.travel.spacecraft.dto.SpacecraftDto;
import worktomeet.travel.spacecraft.dto.SpacecraftRequestDto;
import worktomeet.travel.spacecraft.service.SpacecraftService;

import java.util.List;

@RestController
@RequestMapping("/api/spacecrafts")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Spacecraft Controller", description = "Allow to manage spacecrafts")

public class SpacecraftController {

    @Autowired
    private SpacecraftService service;


    @Operation(summary = "get all spacecrafts", description = "Returns a paged list defining page ans size")
    @GetMapping
    public Page<SpacecraftDto> getPagedSpacecrafts(PageableDto pageable) {
        return service.findAll(pageable);
    }

    @Operation(summary = "get all spacecrafts that contain name", description = "Returns a  list , can be filtered by %name%")
    @GetMapping("/byName")
    public List<SpacecraftDto> getSpacecraftsByNameContaining(@RequestParam(required = true) String name) {
        return service.findByNameContaining(name);
    }


    @Operation(summary = "get a spacecraft by id", description = "Returns a spacecraft by a required id")
    @GetMapping("/{id}")
    public SpacecraftDto getSpacecraft(@PathVariable Long id) {
        return service.getSpacecraft(id);
    }

    @Operation(summary = "create a spacecraft", description = "Returns a created spacecraft")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SpacecraftDto createSpacecraft(@RequestBody SpacecraftRequestDto spacecraftRequestDto) {

        return service.createSpacecraft(spacecraftRequestDto);
    }

    @Operation(summary = "update a spacecraft", description = "Returns an updated spacecraft")
    @PutMapping("/{id}")
    public SpacecraftDto updateSpacecraft(@PathVariable Long id, @RequestBody SpacecraftRequestDto spacecraftRequestDto) {
        return service.updatetSpacecraft(id, spacecraftRequestDto);
    }

    @Operation(summary = "delete a spacecraft", description = "Delete a spacecraft")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSpacecraft(@PathVariable Long id) {
        service.deleteSpacecraft(id);
    }
}


package worktomeet.travel.spacecraft.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import worktomeet.travel.spacecraft.dto.SpacecraftDTO;
import worktomeet.travel.spacecraft.dto.SpacecraftRequestDTO;
import worktomeet.travel.spacecraft.service.SpacecraftService;

@RestController
@RequestMapping("/api/spacecrafts")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Spacecraft Controller", description = "Allow to manage spacecrafts")

public class SpacecraftController {

    @Autowired
    private SpacecraftService service;


    @Operation(summary = "get all spacecrafts", description = "Returns a paged list , can be filtered by %name% , and ask for a specific page and/or define a page size")
    @GetMapping
    public Page<SpacecraftDTO> getAllSpacecrafts(@RequestParam(required = false) String name, Pageable pageable) {
        return service.findAll(name, pageable);
    }


    @Operation(summary = "get a spacecraft by id", description = "Returns a spacecraft by a required id")
    @GetMapping("/{id}")
    public SpacecraftDTO getSpacecraft(@PathVariable Long id) {
        return service.getSpacecraft(id);
    }

    @Operation(summary = "create a spacecraft", description = "Returns a created spacecraft")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SpacecraftDTO createSpacecraft(@RequestBody SpacecraftRequestDTO spacecraftRequestDTO) {

        return service.createSpacecraft(spacecraftRequestDTO);
    }

    @Operation(summary = "update a spacecraft", description = "Returns an updated spacecraft")
    @PutMapping("/{id}")
    public SpacecraftDTO updateSpacecraft(@PathVariable Long id, @RequestBody SpacecraftRequestDTO spacecraftRequestDTO) {
        return service.updatetSpacecraft(id, spacecraftRequestDTO);
    }

    @Operation(summary = "delete a spacecraft", description = "Delete a spacecraft")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSpacecraft(@PathVariable Long id) {
        service.deleteSpacecraft(id);
    }
}


package worktomeet.travel.spacecraft.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import worktomeet.travel.spacecraft.dto.SpacecraftRequestDTO;
import worktomeet.travel.spacecraft.model.Spacecraft;

@Mapper
public interface SpacecraftRequestDTOMapper {

    SpacecraftRequestDTOMapper INSTANCE = Mappers.getMapper(SpacecraftRequestDTOMapper.class);

    Spacecraft toModel(SpacecraftRequestDTO spacecraftDTO);
}

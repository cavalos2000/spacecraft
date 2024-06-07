package worktomeet.travel.spacecraft.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import worktomeet.travel.spacecraft.dto.SpacecraftRequestDto;
import worktomeet.travel.spacecraft.model.Spacecraft;

@Mapper
public interface SpacecraftRequestDtoMapper {

    SpacecraftRequestDtoMapper instance = Mappers.getMapper(SpacecraftRequestDtoMapper.class);

    Spacecraft toModel(SpacecraftRequestDto spacecraftDto);
}

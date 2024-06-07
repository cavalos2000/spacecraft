package worktomeet.travel.spacecraft.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import worktomeet.travel.spacecraft.dto.SpacecraftDto;
import worktomeet.travel.spacecraft.model.Spacecraft;

import java.util.List;

@Mapper
public interface SpacecraftMapper {
    SpacecraftMapper instance = Mappers.getMapper(SpacecraftMapper.class);

    Spacecraft toModel(SpacecraftDto spacecraftDto);
    SpacecraftDto toDto(Spacecraft spacecraft);
    List<Spacecraft> toModelList(List<SpacecraftDto> spacecraftDtoList);
    List<SpacecraftDto> toDtoList(List<Spacecraft> SpacecraftList);

    default Page<SpacecraftDto> toDtoPage(Page<Spacecraft> spacecraftPage) {
        List<SpacecraftDto> dtoList = toDtoList(spacecraftPage.getContent());
        return new PageImpl<>(dtoList, spacecraftPage.getPageable(), spacecraftPage.getTotalElements());
    }
}

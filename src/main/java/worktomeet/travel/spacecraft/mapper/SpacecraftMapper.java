package worktomeet.travel.spacecraft.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import worktomeet.travel.spacecraft.dto.SpacecraftDTO;
import worktomeet.travel.spacecraft.model.Spacecraft;

import java.util.List;

@Mapper
public interface SpacecraftMapper {
    SpacecraftMapper INSTANCE = Mappers.getMapper(SpacecraftMapper.class);

    Spacecraft toModel(SpacecraftDTO spacecraftDTO);
    SpacecraftDTO toDTO(Spacecraft spacecraft);
    List<Spacecraft> toModelList(List<SpacecraftDTO> spacecraftDTOList);
    List<SpacecraftDTO> toDTOList(List<Spacecraft> SpacecraftList);

    default Page<SpacecraftDTO> toDTOPage(Page<Spacecraft> spacecraftPage) {
        List<SpacecraftDTO> dtoList = toDTOList(spacecraftPage.getContent());
        return new PageImpl<>(dtoList, spacecraftPage.getPageable(), spacecraftPage.getTotalElements());
    }
}

package worktomeet.travel.spacecraft.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import worktomeet.travel.spacecraft.dto.PageableDto;

public class PageableUtil {

    public static Pageable createPageable(PageableDto pageableDto) {
        Sort sort = Sort.unsorted();
        if (pageableDto.getSort() != null && !pageableDto.getSort().isEmpty()) {
            String[] sortParams = pageableDto.getSort().split(",");
            if (sortParams.length == 2) {
                String property = sortParams[0];
                String direction = sortParams[1];
                sort = Sort.by(Sort.Direction.fromString(direction), property);
            } else {
                sort = Sort.by(pageableDto.getSort());
            }
        }
        return PageRequest.of(pageableDto.getPage(), pageableDto.getSize(), sort);
    }
}

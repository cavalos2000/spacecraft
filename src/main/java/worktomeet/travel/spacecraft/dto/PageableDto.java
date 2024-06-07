package worktomeet.travel.spacecraft.dto;



import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dto for pagination information")
public class PageableDto {

    @Schema(description = "Page number (0-based)", example = "0", defaultValue = "0")
    private int page;

    @Schema(description = "Size of the page", example = "10", defaultValue = "10")
    private int size;

    @Schema(description = "Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported.", example = "name,asc")
    private String sort;

    // Getters and Setters

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}


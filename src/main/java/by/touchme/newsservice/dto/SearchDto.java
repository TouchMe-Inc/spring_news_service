package by.touchme.newsservice.dto;

import by.touchme.newsservice.criteria.SearchCriteria;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto {
    @NotNull
    private List<SearchCriteria> criteriaList;
}

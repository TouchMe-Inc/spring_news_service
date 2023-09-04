package by.touchme.newsservice.dto;

import by.touchme.newsservice.criteria.SearchCriteria;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class SearchDto {
    @NotNull
    private List<SearchCriteria> criteriaList;
}

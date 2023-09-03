package by.touchme.newsservice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class PageDto<T> {
    List<T> content;
}

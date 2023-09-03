package by.touchme.newsservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class CommentDto {

    private Long id;

    @NotNull
    private Long newsId;

    @NotNull
    private String username;

    @NotNull
    private String text;

    private Date time;
}

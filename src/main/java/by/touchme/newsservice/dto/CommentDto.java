package by.touchme.newsservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

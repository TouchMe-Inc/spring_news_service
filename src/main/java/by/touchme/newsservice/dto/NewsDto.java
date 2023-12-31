package by.touchme.newsservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class NewsDto implements AclEntity {

    private Long id;

    @NotNull
    private String author;

    @NotNull
    private String title;

    @NotNull
    private String text;

    private Date time;
}

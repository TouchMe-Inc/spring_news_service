package by.touchme.newsservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsDto {

    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String text;

    private Date time;
}

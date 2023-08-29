package by.touchme.newsservice.record;

import java.util.Date;

public record ErrorMessage(Date timestamp, String message, String details) {
}

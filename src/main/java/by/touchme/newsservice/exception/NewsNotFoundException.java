package by.touchme.newsservice.exception;

public class NewsNotFoundException extends RuntimeException {

    private final Long newsId;

    public NewsNotFoundException(Long newsId) {
        this.newsId = newsId;
    }

    @Override
    public String getMessage() {
        return "News with id = " + newsId + " not found";
    }
}

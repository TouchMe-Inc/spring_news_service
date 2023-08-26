package by.touchme.newsservice.exception;

/**
 * The class {@code NewsNotFoundException} and its subclasses
 * are a form of {@code RuntimeException} that indicates conditions
 * that a reasonable application might want to catch.
 */
public class NewsNotFoundException extends RuntimeException {

    /**
     * Unique identifier of the news.
     */
    private final Long newsId;

    /**
     * Constructs a new runtime exception with the news identifier.
     *
     * @param   newsId   the news identifier. The news identifier is saved for
     *          later retrieval by the {@link #getMessage()} method.
     */
    public NewsNotFoundException(Long newsId) {
        this.newsId = newsId;
    }

    @Override
    public String getMessage() {
        return "News with id = " + newsId + " not found";
    }
}

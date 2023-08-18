package by.touchme.newsservice.exception;

public class CommentNotFoundException extends RuntimeException {

    private final Long commentId;

    public CommentNotFoundException(Long commentId) {
        this.commentId = commentId;
    }

    @Override
    public String getMessage() {
        return "Comment with id = " + commentId + " not found";
    }
}

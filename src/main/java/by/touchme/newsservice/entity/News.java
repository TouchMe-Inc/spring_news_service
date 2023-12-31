package by.touchme.newsservice.entity;

import by.touchme.newsservice.dto.AclEntity;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

/**
 * The news object contains main details about News.
 */
@Data
@Table(name = "news")
@Entity
public class News {

    /**
     * Unique identifier of the news.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Author of the news.
     */
    @Column(name = "author", nullable = false)
    private String author;

    /**
     * Title of the news.
     */
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * Main content of the news.
     */
    @Column(name = "text", nullable = false, columnDefinition = "TEXT")
    private String text;

    /**
     * Time of creation of the news.
     */
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time")
    private Date time;
}

package by.touchme.newsservice.controller;

import by.touchme.newsservice.dto.NewsDto;
import by.touchme.newsservice.dto.PageDto;
import by.touchme.newsservice.service.NewsService;
import by.touchme.newsservice.service.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;


/**
 * CRUD controller for news.
 */
@RequestMapping("/v1/news")
@RequiredArgsConstructor
@RestController
public class NewsController {

    private final NewsService newsService;
    private final PermissionService permissionService;

    /**
     * Endpoint for retrieving news by its Identifier.
     *
     * @return NewsDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<NewsDto> getById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(newsService.getById(id), HttpStatus.OK);
    }

    /**
     * Endpoint for receiving paginated news.
     *
     * @param pageable Pagination options
     * @return PageDto with NewsDto
     */
    @GetMapping
    public ResponseEntity<PageDto<NewsDto>> getPage(Pageable pageable) {
        return new ResponseEntity<>(newsService.getPage(pageable), HttpStatus.OK);
    }

    /**
     * Endpoint for creating a comment.
     * Requires authorization with journalist or admin role.
     * After creation, the user has the right to delete and edit the created entry.
     *
     * @param news NewsDto without id
     * @return NewsDto
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_JOURNALIST')")
    @PostMapping
    public ResponseEntity<NewsDto> create(@Valid @RequestBody NewsDto news) {
        NewsDto createdNews = newsService.create(news);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        permissionService.addPermissionForUser(createdNews, BasePermission.DELETE, username);
        permissionService.addPermissionForUser(createdNews, BasePermission.WRITE, username);

        return new ResponseEntity<>(createdNews, HttpStatus.CREATED);
    }

    /**
     * Endpoint for update news by its Identifier.
     * Requires administrator role or write permission.
     *
     * @param news NewsDto without id
     * @return NewsDto
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasPermission(#id, 'by.touchme.newsservice.dto.NewsDto', 'WRITE')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<NewsDto> updateById(@PathVariable(name = "id") Long id, @Valid @RequestBody NewsDto news) {
        return new ResponseEntity<>(newsService.updateById(id, news), HttpStatus.NO_CONTENT);
    }

    /**
     * Endpoint for delete a news by its Identifier.
     * Requires administrator role or delete permission.
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasPermission(#id, 'by.touchme.newsservice.dto.NewsDto', 'DELETE')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "id") Long id) {
        newsService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


/**
 * CRUD controller for news.
 */
@RequestMapping("/v1/news")
@RequiredArgsConstructor
@RestController
public class NewsController {

    private final NewsService newsService;
    private final PermissionService permissionService;

    @GetMapping("/{id}")
    public ResponseEntity<NewsDto> getById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(newsService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageDto<NewsDto>> getPage(Pageable pageable) {
        return new ResponseEntity<>(newsService.getPage(pageable), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_JOURNALIST')")
    @PostMapping
    public ResponseEntity<NewsDto> create(@Valid @RequestBody NewsDto news, Authentication authentication) {
        NewsDto createdNews = newsService.create(news);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        permissionService.addPermissionForUser(createdNews, BasePermission.DELETE, username);
        permissionService.addPermissionForUser(createdNews, BasePermission.WRITE, username);

        return new ResponseEntity<>(createdNews, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasPermission(#id, 'by.touchme.newsservice.dto.NewsDto', 'WRITE')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<NewsDto> updateById(@PathVariable(name = "id") Long id, @Valid @RequestBody NewsDto news) {
        return new ResponseEntity<>(newsService.updateById(id, news), HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasPermission(#id, 'by.touchme.newsservice.dto.NewsDto', 'DELETE')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "id") Long id) {
        newsService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

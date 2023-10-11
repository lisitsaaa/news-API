package com.example.newsapi.controller;

import com.example.newsapi.dto.news.NewsDto;
import com.example.newsapi.dto.news.PostDto;
import com.example.newsapi.dto.news.TextDto;
import com.example.newsapi.dto.news.TitleDto;
import com.example.newsapi.entity.news.News;
import com.example.newsapi.entity.user.User;
import com.example.newsapi.service.NewsService;
import com.example.newsapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.newsapi.controller.util.Validator.checkBindingResult;
import static com.example.newsapi.mapper.NewsMapper.INSTANCE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<NewsDto> create(@RequestBody @Valid PostDto postDto,
                                          BindingResult bindingResult,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        checkBindingResult(bindingResult);
        return ok(INSTANCE.newsToNewsDto(newsService.save(getSavedNews(postDto, userDetails))));
    }

    private News getSavedNews(PostDto postDto, UserDetails userDetails){
        News news = INSTANCE.postDtoToNews(postDto);
        User user = userService.findByUsername(userDetails.getUsername());

        news.setInsertedById(user);
        news.setUpdatedById(user);
        news.setCreationDate(LocalDate.now());
        news.setLastEditDate(LocalDate.now());
        return news;
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable long id) {
        newsService.remove(id);
    }

    @GetMapping("/get-all-with-pagination")
    public ResponseEntity<List<NewsDto>> getAllWithPagination(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int size){
        List<NewsDto> newsList = new ArrayList<>();

        newsService.findAllWithPagination(PageRequest.of(page, size))
                .forEach(news -> newsList.add(INSTANCE.newsToNewsDto(news)));
        return ok(newsList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsDto> update(@RequestBody @Valid PostDto postDto,
                                          BindingResult bindingResult,
                                          @PathVariable long id,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        checkBindingResult(bindingResult);
        return ok(INSTANCE.newsToNewsDto(newsService.update(getUpdatedNews(postDto, id, userDetails))));
    }

    private News getUpdatedNews(PostDto postDto, long postId, UserDetails userDetails){
        News news = newsService.findById(postId);
        User user = userService.findByUsername(userDetails.getUsername());
        news.setUpdatedById(user);

        if(!String.valueOf(postDto.getTitle()).isEmpty()) news.setTitle(postDto.getTitle());
        if(!String.valueOf(postDto.getText()).isEmpty()) news.setText(postDto.getText());

        news.setLastEditDate(LocalDate.now());

        return news;
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsDto> getById(@PathVariable long id) {
        return ok(INSTANCE.newsToNewsDto(newsService.findById(id)));
    }

    @PostMapping("/find-by-title")
    public ResponseEntity<List<NewsDto>> getAllByTitle(@RequestBody TitleDto title) {
        List<NewsDto> newsDtoList = new ArrayList<>();

        newsService.findByTitle(title.getTitle())
                .forEach(news -> newsDtoList.add(INSTANCE.newsToNewsDto(news)));
        return ok(newsDtoList);
    }

    @PostMapping("/find-by-text")
    public ResponseEntity<List<NewsDto>> getAllByText(@RequestBody TextDto text) {
        List<NewsDto> newsDtoList = new ArrayList<>();

        newsService.findByTitle(text.getText())
                .forEach(news -> newsDtoList.add(INSTANCE.newsToNewsDto(news)));
        return ok(newsDtoList);
    }
}

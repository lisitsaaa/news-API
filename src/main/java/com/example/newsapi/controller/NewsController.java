package com.example.newsapi.controller;

import com.example.newsapi.dto.news.NewsDto;
import com.example.newsapi.dto.news.PostDto;
import com.example.newsapi.entity.news.News;
import com.example.newsapi.entity.user.User;
import com.example.newsapi.service.NewsService;
import com.example.newsapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.time.LocalDate;

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
        return ok(INSTANCE.newsToNewsDto(newsService.save(getNews(postDto, userDetails))));
    }

    private News getNews(PostDto postDto, UserDetails userDetails){
        News news = INSTANCE.postDtoToNews(postDto);
        User user = userService.findByUsername(userDetails.getUsername());

        news.setInsertedById(user);
        news.setUpdatedById(user);
        news.setCreationDate(LocalDate.now());
        news.setLastEditDate(LocalDate.now());
        return news;
    }
}

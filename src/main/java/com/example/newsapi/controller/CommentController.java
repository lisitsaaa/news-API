package com.example.newsapi.controller;

import com.example.newsapi.dto.comment.CommentDto;
import com.example.newsapi.dto.comment.TextDto;
import com.example.newsapi.dto.news.NewsAndCommentDto;
import com.example.newsapi.dto.news.NewsDto;
import com.example.newsapi.entity.news.Comment;
import com.example.newsapi.entity.news.News;
import com.example.newsapi.entity.user.User;
import com.example.newsapi.service.CommentService;
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
import static com.example.newsapi.mapper.CommentMapper.INSTANCE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;
    private final NewsService newsService;

    @PostMapping("/{newsId}")
    public ResponseEntity<CommentDto> create(@RequestBody @Valid TextDto textDto,
                                             BindingResult bindingResult,
                                             @AuthenticationPrincipal UserDetails userDetails,
                                             @PathVariable long newsId) {
        checkBindingResult(bindingResult);
        return ok(INSTANCE.commentToDto(commentService.save(getSavedComment(textDto, userDetails, newsId))));
    }

    private Comment getSavedComment(TextDto textDto, UserDetails userDetails, long newsId){
        Comment comment = INSTANCE.textDtoToComment(textDto);

        User user = userService.findByUsername(userDetails.getUsername());
        comment.setInsertedById(user);

        News news = newsService.findById(newsId);
        comment.setNews(news);

        comment.setCreationDate(LocalDate.now());
        comment.setLastEditDate(LocalDate.now());

        return comment;
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable long id) {
        commentService.remove(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getById(@PathVariable long id) {
        return ok(INSTANCE.commentToDto(commentService.findById(id)));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<NewsAndCommentDto> getNewsWithAllComment(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int size,
            @PathVariable long postId){
        News news = newsService.findById(postId);
        List<Comment> comments = commentService
                .getAllByNewsWithPagination(PageRequest.of(page, size), news);

        return ok(NewsAndCommentDto.builder()
                .news(news)
                .comments(comments)
                .build());
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> update(@RequestBody @Valid TextDto textDto,
                                             BindingResult bindingResult,
                                             @PathVariable long commentId){
        checkBindingResult(bindingResult);

        return ok(INSTANCE.commentToDto(commentService.update(getUpdatedComment(textDto, commentId))));
    }

    private Comment getUpdatedComment(TextDto textDto, long commentId){
        Comment comment = commentService.findById(commentId);

        comment.setLastEditDate(LocalDate.now());
        if(!String.valueOf(textDto.getText()).isEmpty()) comment.setText(textDto.getText());

        return comment;
    }
}

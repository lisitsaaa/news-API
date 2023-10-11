package com.example.newsapi.service;

import com.example.newsapi.entity.news.Comment;
import com.example.newsapi.entity.news.News;
import com.example.newsapi.exception.NotFoundException;
import com.example.newsapi.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Value("Not found") private String NOT_FOUND_MESSAGE;
    @Value("There is no news") private String NO_COMMENTS;

    public Comment save(Comment comment){
        return commentRepository.save(comment);
    }

    public void remove(long id) {
        commentRepository.delete(findById(id));
    }

    public Comment update(Comment comment) {
        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public List<Comment> getAllByNewsWithPagination(PageRequest pageRequest, News news){
        return commentsCheck(commentRepository.findAllByNews(pageRequest).getContent());
    }

    @Transactional(readOnly = true)
    public Comment findById(long id) {
        return commentCheck(commentRepository.findById(id));
    }

    private Comment commentCheck(Optional<Comment> comment) {
        if (comment.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        return comment.get();
    }

    private List<Comment> commentsCheck(List<Comment> comments) {
        if (comments.isEmpty()) {
            throw new NotFoundException(NO_COMMENTS);
        }
        return comments;
    }
}

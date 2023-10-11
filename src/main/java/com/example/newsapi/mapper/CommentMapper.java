package com.example.newsapi.mapper;

import com.example.newsapi.dto.comment.CommentDto;
import com.example.newsapi.dto.comment.TextDto;
import com.example.newsapi.entity.news.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    Comment textDtoToComment(TextDto dto);
    CommentDto commentToDto(Comment comment);
}

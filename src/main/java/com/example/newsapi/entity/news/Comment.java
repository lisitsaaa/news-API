package com.example.newsapi.entity.news;

import com.example.newsapi.entity.AbstractEntity;
import com.example.newsapi.entity.user.User;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Entity
@Setter @Getter
@Builder @NoArgsConstructor @AllArgsConstructor
public class Comment extends AbstractEntity {
    private String text;
    private LocalDate creationDate;
    private LocalDate lastEditDate;
    @OneToOne
    private User insertedById;

    @ManyToOne(fetch = FetchType.LAZY)
    private News news;
}

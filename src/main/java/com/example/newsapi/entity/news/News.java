package com.example.newsapi.entity.news;

import com.example.newsapi.entity.AbstractEntity;
import com.example.newsapi.entity.user.User;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Entity
@Setter @Getter
@Builder @NoArgsConstructor @AllArgsConstructor
public class News extends AbstractEntity {
    private String title;
    private String text;
    private LocalDate creationDate;
    private LocalDate lastEditDate;

    @OneToOne
    private User insertedById;

    @OneToOne
    private User updatedById;
}

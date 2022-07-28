package com.ll.exam.article.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleDto {
    private long id;
    private String title;
    private String body;
}

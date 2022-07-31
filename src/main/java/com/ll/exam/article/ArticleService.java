package com.ll.exam.article;

import com.ll.exam.article.dto.ArticletDto;

import java.util.List;

public class ArticleService {
    private ArticleRepository articleRepository;

    public ArticleService() {
        articleRepository = new ArticleRepository();
    }

    public long write(String title, String body) {
        return articleRepository.write(title, body);
    }

    public List<ArticletDto> findAll() {
        return articleRepository.findAll();
    }

    public ArticletDto findById(long id) {
        return articleRepository.findById(id);
    }

    public void delete(long id) {
        articleRepository.delete(id);
    }

    public void modify(long id, String title, String body) {
        articleRepository.modify(id, title, body);
    }

    public List<ArticletDto> findIdGreaterThan(long fromId) {
        return articleRepository.findAllIdGreaterThan(fromId);
    }
}

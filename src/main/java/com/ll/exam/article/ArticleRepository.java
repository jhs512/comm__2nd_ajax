package com.ll.exam.article;

import com.ll.exam.article.dto.ArticletDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArticleRepository {
    private static List<ArticletDto> datum;
    private static long lastId;

    static {
        datum = new ArrayList<>();
        lastId = 0;

        makeTestData();
    }

    private static void makeTestData() {
        IntStream.rangeClosed(1, 10).forEach(id -> {
            String title = "제목%d".formatted(id);
            String body = "내용%d".formatted(id);
            write(title, body);
        });
    }

    public static long write(String title, String body) {
        long id = ++lastId;
        ArticletDto newArticleDto = new ArticletDto(id, title, body);

        datum.add(newArticleDto);

        return id;
    }

    public static List<ArticletDto> findAll() {
        return datum;
    }

    public static ArticletDto findById(long id) {
        for (ArticletDto articleDto : datum) {
            if (articleDto.getId() == id) {
                return articleDto;
            }
        }

        return null;
    }

    public void delete(long id) {
        ArticletDto articleDto = findById(id);

        if (articleDto == null) return;

        datum.remove(articleDto);
    }

    public void modify(long id, String title, String body) {
        ArticletDto articleDto = findById(id);

        if (articleDto == null) return;

        articleDto.setTitle(title);
        articleDto.setBody(body);
    }

    public List<ArticletDto> findAllIdGreaterThan(long fromId) {
        return datum
                .stream()
                .filter(articleDto -> articleDto.getId() > fromId)
                .collect(Collectors.toList());
    }
}

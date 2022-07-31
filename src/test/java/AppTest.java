import com.fasterxml.jackson.core.type.TypeReference;
import com.ll.exam.article.dto.ArticletDto;
import com.ll.exam.util.Ut;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {
    @Test
    void mapOf() {
        Map<String, Object> map = Ut.mapOf("age", 11, "name", "Paul");

        assertThat(map.get("age")).isEqualTo(11);
        assertThat(map.get("name")).isEqualTo("Paul");
    }

    @Test
    void assertJ__assertThat() {
        int rs = 10 + 20;
        assertThat(rs).isEqualTo(30);
    }

    @Test
        // ArticleDto => JS객체(단순)
    void ObjectMapper__articleDtoToJsonStr() {
        ArticletDto articleDto = new ArticletDto(1, "제목", "내용");

        String jsonStr = Ut.json.toStr(articleDto, "");
        assertThat(jsonStr).isNotBlank();
        assertThat(jsonStr).isEqualTo("""
                {"id":1,"title":"제목","body":"내용"}
                """.trim());
    }

    @Test
        // List<ArticleDto> => JS배열
    void ObjectMapper__articleDtoListToJsonStr() {
        List<ArticletDto> articleDtos = new ArrayList<>();
        articleDtos.add(new ArticletDto(1, "제목1", "내용1"));
        articleDtos.add(new ArticletDto(2, "제목2", "내용2"));

        String jsonStr = Ut.json.toStr(articleDtos, "");
        assertThat(jsonStr).isEqualTo("""
                [{"id":1,"title":"제목1","body":"내용1"},{"id":2,"title":"제목2","body":"내용2"}]
                """.trim());
    }

    @Test
        // Map<String, ArticleDto> => JS객체(복잡)
    void ObjectMapper__articleDtoMapToJsonStr() {
        Map<String, ArticletDto> articleDtoMap = new HashMap<>();
        articleDtoMap.put("가장오래된", new ArticletDto(1, "제목1", "내용1"));
        articleDtoMap.put("최신", new ArticletDto(2, "제목2", "내용2"));
        String jsonStr = Ut.json.toStr(articleDtoMap, "");
        assertThat(jsonStr).isEqualTo("""
                {"가장오래된":{"id":1,"title":"제목1","body":"내용1"},"최신":{"id":2,"title":"제목2","body":"내용2"}}
                """.trim());
    }

    @Test
        // JS객체(단순) => ArticleDto
    void ObjectMapper__jsonStrToObj() {
        ArticletDto articleDtoOrigin = new ArticletDto(1, "제목", "내용");
        String jsonStr = Ut.json.toStr(articleDtoOrigin, "");

        ArticletDto articleDtoFromJson = Ut.json.toObj(jsonStr, ArticletDto.class, null);
        assertThat(articleDtoOrigin).isEqualTo(articleDtoFromJson);
    }

    @Test
        // JS배열 => List<ArticleDto>
    void ObjectMapper__jsonStrToArticleDtoList() {
        List<ArticletDto> articleDtos = new ArrayList<>();
        articleDtos.add(new ArticletDto(1, "제목1", "내용1"));
        articleDtos.add(new ArticletDto(2, "제목2", "내용2"));

        String jsonStr = Ut.json.toStr(articleDtos, "");

        List<ArticletDto> articleDtosFromJson = Ut.json.toObj(jsonStr, new TypeReference<>() {
        }, null);

        assertThat(articleDtosFromJson).isEqualTo(articleDtos);
    }

    @Test
        // JS객체(복잡) => Map<String, ArticleDto>
    void ObjectMapper__jsonStrToArticleDtoMap() {
        Map<String, ArticletDto> articleDtoMap = new HashMap<>();
        articleDtoMap.put("가장오래된", new ArticletDto(1, "제목1", "내용1"));
        articleDtoMap.put("최신", new ArticletDto(2, "제목2", "내용2"));
        String jsonStr = Ut.json.toStr(articleDtoMap, "");

        Map<String, ArticletDto> articleDtoMapFromJson = Ut.json.toObj(jsonStr, new TypeReference<>() {
        }, null);

        assertThat(articleDtoMapFromJson).isEqualTo(articleDtoMap);
    }
}
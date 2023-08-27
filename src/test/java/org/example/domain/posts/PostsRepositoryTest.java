package org.example.domain.posts;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @BeforeEach
    void setup() {
        String title = "테스트 게시글";
        String content = "테스트 본문";
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("matthew633jdi@gmail.com")
                .build());
        String title1 = "테스트 게시글1";
        String content1 = "테스트 본문1";
        postsRepository.save(Posts.builder()
                .title(title1)
                .content(content1)
                .author("matthew633jdi@gmail.com")
                .build());
        String title2 = "테스트 게시글2";
        String content2 = "테스트 본문2";
        postsRepository.save(Posts.builder()
                .title(title2)
                .content(content2)
                .author("matthew633jdi@gmail.com")
                .build());
    }

    @AfterEach
    void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    void 게시글_불러오기() {
        String title = "테스트 게시글";
        String content = "테스트 본문";
        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    void BaseTimeEntity_등록() {
        // given
        LocalDateTime now = LocalDateTime.of(2023, 8, 27, 0, 0, 0);

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        System.out.println(">>>>>>>> createdDate=" + posts.getCreatedDate() + ", modifiedDate="+posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }

    @DisplayName("Querydsl")
    @Test
    void querydsl_게시글조회() {
        // when
        List<Posts> postsList = postsRepository.findAllDesc();

        // then
        postsList.forEach(System.out::println);
        assertThat(postsList).isSortedAccordingTo(Comparator.comparingLong(Posts::getId).reversed());
    }
}
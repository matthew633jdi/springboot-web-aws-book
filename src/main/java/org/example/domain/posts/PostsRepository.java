package org.example.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

// iBatis, Mybatis 등에서 Dao라고 불리는 DB Layer 접근자
// @Repository 생략 가능
public interface PostsRepository extends JpaRepository<Posts, Long> {
}

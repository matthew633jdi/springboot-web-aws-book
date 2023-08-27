package org.example.domain.posts;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.example.domain.posts.QPosts.posts;

@RequiredArgsConstructor
@Repository
public class PostRepositoryCustomImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Posts> findAllDesc() {
        return queryFactory
                .selectFrom(posts)
                .orderBy(posts.id.desc())
                .fetch();
    }

}

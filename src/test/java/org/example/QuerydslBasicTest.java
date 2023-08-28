package org.example;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.example.entity.Mb;
import org.example.entity.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.entity.QMb.mb;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    void before() {
        queryFactory = new JPAQueryFactory(em);
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamA");
        em.persist(teamA);
        em.persist(teamB);

        Mb mb1 = new Mb("member1", 10, teamA);
        Mb mb2 = new Mb("member2", 20, teamA);

        Mb mb3 = new Mb("member3", 30, teamB);
        Mb mb4 = new Mb("member4", 40, teamB);

        em.persist(mb1);
        em.persist(mb2);
        em.persist(mb3);
        em.persist(mb4);
    }

    @Test
    void startJPQL() {
        String qlString = "select m from Mb m " +
                "where m.username = :username";
        Mb findMb = em.createQuery(qlString, Mb.class)
                .setParameter("username", "member1")
                .getSingleResult();

        assertThat(findMb.getUsername()).isEqualTo("member1");
    }

    @Test
    void startQuerydsl() {
                Mb findMb = queryFactory
                .select(mb)
                .from(mb)
                .where(mb.username.eq("member1"))
                .fetchOne();

        assertThat(findMb.getUsername()).isEqualTo("member1");
    }

    @Test
    void searchAndParam() {
        Mb findMb = queryFactory
                .selectFrom(mb)
//                .where(member.username.eq("member1").and(member.age.eq(10)))
                .where(
                        mb.username.eq("member1"),
                        null,   // null 값은 무시함 => 메서드 추출을 활용해서 동적 쿼리를 깔끔하게 만들 수 있음
                        mb.age.eq(10)
                )
                .fetchOne();

        assertThat(findMb.getUsername()).isEqualTo("member1");
    }


    void fetchList() {
        // list
        List<Mb> fetch = queryFactory
                .selectFrom(mb)
                .fetch();

        // 단 건
        Mb findMb1 = queryFactory
                .selectFrom(mb)
                .fetchOne();

        // 처음 한 건 조회
        Mb findMb2 = queryFactory
                .selectFrom(mb)
                .fetchFirst();

        // 페이징에 사용. 전체 쿼리와 카운트 조회 2번 수행
        QueryResults<Mb> results = queryFactory
                .selectFrom(mb)
                .fetchResults();

        List<Mb> results1 = results.getResults();

        // 카운트 쿼리
        long count = queryFactory
                .selectFrom(mb)
                .fetchCount();
    }
}

package org.example.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class HelloTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("queryDsl Test")
    void firstTest() {
        Hello hello = Hello.builder().name("hee").build();
        em.persist(hello);

        QHello query = QHello.hello;

        JPAQueryFactory factory = new JPAQueryFactory(em);
        Hello one = factory.selectFrom(query)
                .where(query.eq(hello))
                .fetchOne();

        assertThat(hello).isSameAs(one);
    }
}
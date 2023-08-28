package org.example.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

@SpringBootTest
@Transactional
@Commit
class MbTest {

    @Autowired
    EntityManager em;

    @Test
    void testEntity() {
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

        // 초기화
        em.flush();
        em.clear();

        List<Mb> mbs = em.createQuery("select m from Mb m", Mb.class).getResultList();

        for (Mb mb : mbs) {
            System.out.println("member = " + mb);
            System.out.println("-> member.getTeam() = " + mb.getTeam());
        }
    }

}
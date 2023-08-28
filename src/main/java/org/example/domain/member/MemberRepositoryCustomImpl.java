package org.example.domain.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.example.domain.member.QMember.member;

@RequiredArgsConstructor
@Repository
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    @Override
    public Optional<Member> findByEmail(String email) {
        return Optional.ofNullable(queryFactory
                .selectFrom(member)
                .where(member.email.eq(email))
                .fetchOne());
    }
}

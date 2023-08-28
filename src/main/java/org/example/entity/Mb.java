package org.example.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"id", "username", "age"})
public class Mb {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Mb(String username) {
        this(username, 0);
    }

    public Mb(String username, int age) {
        this(username, age, null);
    }

    public Mb(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if (team != null) {
            changeTeam(team);
        }
    }

    // 양방향 연관관계 한번에 처리(연관관계 편의 메서드)
    public void changeTeam(Team team) {
        this.team = team;
        team.getMbs().add(this);
    }
}

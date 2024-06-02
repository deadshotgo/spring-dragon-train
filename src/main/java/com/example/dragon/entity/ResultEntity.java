package com.example.dragon.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "result")
public class ResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String score;

    @ManyToOne
    @JoinColumn(name = "competition_id")
    private CompetitionEntity competition;

    @ManyToOne
    @JoinColumn(name = "participant_id")
    private ParticipantEntity participant;
}

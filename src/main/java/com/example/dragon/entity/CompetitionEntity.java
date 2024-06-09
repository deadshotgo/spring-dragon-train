package com.example.dragon.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "competition")
@NoArgsConstructor
public class CompetitionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "date_start")
    private String dateStart;
    @Column(name = "date_end")
    private String dateEnd;


    @OneToMany(mappedBy = "competition", cascade = CascadeType.ALL)
    private List<ResultEntity> results;

    public CompetitionEntity(String name, String dateStart, String dateEnd) {
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public CompetitionEntity(Long id, String name, String dateStart, String dateEnd, List<ResultEntity> results) {
        this.id = id;
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.results = results;
    }
}

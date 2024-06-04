package com.example.dragon.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "participant")
@NoArgsConstructor
public class ParticipantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL)
    private List<ResultEntity> results;

    public ParticipantEntity(Long id, String name, List<ResultEntity> results) {
        this.id = id;
        this.name = name;
        this.results = results;
    }
}

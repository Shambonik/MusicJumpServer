package com.example.musicjump.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "skins_musicjump")
@Data
public class Skin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int cost;

    @ManyToMany(mappedBy = "skins",fetch = FetchType.EAGER)
    @JsonIgnore
    private List<User> users;


    public Skin(String name, int cost){
        this.name = name;
        this.cost = cost;
    }
}

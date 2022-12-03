package com.epam.training.ticketservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@Table(name = "movies")
public class MovieEntity {

    public MovieEntity(String title, String genre, Integer length) {
        this.title = title;
        this.genre = genre;
        this.length = length;
    }

    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    private String genre;

    private Integer length;

}

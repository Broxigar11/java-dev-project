package com.epam.training.ticketservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "screenings")
public class ScreeningEntity {

    public ScreeningEntity(MovieEntity movie, RoomEntity room, Date screeningTime) {
        this.movie = movie;
        this.room = room;
        this.screeningTime = screeningTime;
    }

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private MovieEntity movie;

    @ManyToOne
    private RoomEntity room;

    @DateTimeFormat(pattern = "YYYY-MM-DD hh:mm")
    @Column(name = "screening_time")
    private Date screeningTime;

}

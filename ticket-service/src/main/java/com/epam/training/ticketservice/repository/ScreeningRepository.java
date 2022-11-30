package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.entity.MovieEntity;
import com.epam.training.ticketservice.entity.RoomEntity;
import com.epam.training.ticketservice.entity.ScreeningEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<ScreeningEntity, String> {



    Optional<ScreeningEntity> findByMovieAndRoomAndDate(MovieEntity movie, RoomEntity room, Date date);

}

package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, String> {

    Optional<RoomEntity> findByName(String name);

    @Transactional
    @Modifying
    @Query("update RoomEntity r set r.rows = :rows, r.columns = :columns where r.name = :name")
    void updateRoomEntity(String name, Integer rows, Integer columns);

}

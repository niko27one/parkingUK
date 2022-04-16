package com.parkinguk.parkinguk.repository;
import com.parkinguk.parkinguk.entity.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FloorsRepository extends JpaRepository<Floor,Long>{
    Optional<Floor> findByDescription (String description);
}

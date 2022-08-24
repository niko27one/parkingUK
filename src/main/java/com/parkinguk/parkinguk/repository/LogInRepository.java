package com.parkinguk.parkinguk.repository;

import com.parkinguk.parkinguk.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LogInRepository extends JpaRepository<Login, Long>{

    Optional<List<Login>> findByEmailAndPassword (String email,String password);// tis use JPQL to make the query

    /*
    @Query(value = "select * from login where email = ?1 and password = ?2",nativeQuery = true)
    Optional<List<Login>> findByEmailAndPasswordNative(String email, String password);//uses SQL language to make the query*/

    Optional<List<Login>> findByEmail(String email);//JPQL language
}

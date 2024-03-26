package com.example.demo.Repository;

import com.example.demo.Model.Pocket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PocketRepository extends JpaRepository<Pocket, Integer> {

    @Query("SELECT p.balance FROM Pocket p WHERE p.user.email = :email")
    Double findBalanceByUserEmail(@Param("email") String email);

    Pocket findByUserEmail(String email);
}

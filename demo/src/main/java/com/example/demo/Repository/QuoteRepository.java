package com.example.demo.Repository;

import com.example.demo.Model.Invoice;
import com.example.demo.Model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Integer> {

//    @Query("SELECT q FROM Quote q INNER JOIN q.client c INNER JOIN c.user u WHERE u.email = :email ORDER BY i.date DESC")
//    List<Quote> findTop5QuotesByUserEmailOrderByDateDesc(@Param("email") String email);

//    @Query("SELECT q FROM Quote")
//    List<Quote> getAllQuotes(@Param("email") String email);


}

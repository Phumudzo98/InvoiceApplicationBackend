package com.example.demo.Repository;

import com.example.demo.Model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Integer> {

    List<Quote> findTop5ByOrderByDateDesc();
}

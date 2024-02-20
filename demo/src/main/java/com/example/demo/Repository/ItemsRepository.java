package com.example.demo.Repository;

import com.example.demo.Model.Items;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsRepository extends JpaRepository<Items, Integer> {


}

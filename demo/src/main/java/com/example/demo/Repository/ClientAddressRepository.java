package com.example.demo.Repository;

import com.example.demo.Model.ClientAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientAddressRepository extends JpaRepository<ClientAddress, Integer> {
}

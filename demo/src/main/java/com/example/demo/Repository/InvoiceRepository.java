package com.example.demo.Repository;

import com.example.demo.Model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    List<Invoice> findTop5ByOrderByDateDesc();

}

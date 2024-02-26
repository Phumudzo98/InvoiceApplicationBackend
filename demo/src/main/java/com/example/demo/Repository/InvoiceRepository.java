package com.example.demo.Repository;

import com.example.demo.Model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {


    //5 invoices for home
//    @Query("SELECT i FROM Invoice i INNER JOIN i.client c INNER JOIN c.user u WHERE u.email = :email ORDER BY i.date DESC")
//    List<Invoice> findTop5InvoicesByUserEmailOrderByDateDesc(@Param("email") String email);

//    @Query("SELECT i FROM Invoice i, User u, Client c WHERE c.user_id=:email")
//    List<Invoice> getAllInvoices(@Param("email") String email);

//    @Query("DELETE FROM Invoice i, Client c WHERE c.user_id=:email AND i.invoiceId=:id")
//    void deleteInvoice(@Param("id") int id,@Param("email") String email);

//    @Query("SELECT i FROM Invoice i JOIN i.client c JOIN c.user u WHERE c.user.email = :email AND i.invoiceId LIKE %:search%")
//    List<Invoice> searchInvoice(@Param("email")String email,@Param("search")int search);
}

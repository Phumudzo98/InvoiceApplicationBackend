package com.example.demo.Repository;

import com.example.demo.Model.BusinessInfo;
import com.example.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessInfoRepository extends JpaRepository<BusinessInfo,Integer> {

    BusinessInfo findByUser(User user);
}

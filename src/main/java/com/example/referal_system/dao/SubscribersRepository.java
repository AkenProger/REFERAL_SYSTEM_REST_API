package com.example.referal_system.dao;

import com.example.referal_system.models.Subscribers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribersRepository extends JpaRepository<Subscribers, Long> {

}

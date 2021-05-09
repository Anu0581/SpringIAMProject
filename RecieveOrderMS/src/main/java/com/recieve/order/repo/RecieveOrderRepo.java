package com.recieve.order.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recieve.order.entity.TokenEntity;

public interface RecieveOrderRepo extends JpaRepository<TokenEntity, String> {

}

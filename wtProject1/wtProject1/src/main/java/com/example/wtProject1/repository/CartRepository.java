package com.example.wtProject1.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class CartRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void createCartTable(String tableName) {
        String sql = "CREATE TABLE " + tableName + " (fav INT)";
        entityManager.createNativeQuery(sql).executeUpdate();
    }
}
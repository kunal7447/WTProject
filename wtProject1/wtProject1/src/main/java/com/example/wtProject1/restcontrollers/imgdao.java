package com.example.wtProject1.restcontrollers;

import com.example.wtProject1.entity.ImageData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class imgdao {
    @Autowired
    private EntityManager entityManager;

    public List<ImageData> psearch(String ss){
        String sqlQuery = "SELECT * FROM image_data WHERE name LIKE :ss";
        System.out.println(sqlQuery);
        Query query = entityManager.createNativeQuery(sqlQuery, ImageData.class); // Specify the entity type
        query.setParameter("ss", "%" + ss + "%");
        List<ImageData> resultList = query.getResultList();
        System.out.println(resultList.size());
        for(int i = 0;i< resultList.size();i++){
            System.out.println(resultList.get(i).name);
        }
        return resultList;
    }
}

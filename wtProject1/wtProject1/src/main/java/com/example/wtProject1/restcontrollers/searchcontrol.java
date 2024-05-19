package com.example.wtProject1.restcontrollers;

import com.example.wtProject1.entity.ImageData;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class searchcontrol {
    private EntityManager em;
    @Autowired
    private imgdao im;

    @GetMapping("/search/{query}")
    public ResponseEntity<?> search(@PathVariable("query") String query){

        System.out.println("Query: "+query);
        // User user = this.userRepository.getUserByUserName(principal.getName());
        List<ImageData> contacts = im.psearch(query);
        System.out.println(contacts.size());
        for (int i = 0;i<contacts.size();i++){
            System.out.println(contacts.get(i).name);
        }
        System.out.println("sendind");
        return ResponseEntity.ok(contacts);
    }

}

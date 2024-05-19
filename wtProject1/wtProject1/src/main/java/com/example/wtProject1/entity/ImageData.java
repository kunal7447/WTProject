package com.example.wtProject1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.net.SocketOption;
import java.util.Date;

@Entity
@Table(name = "ImageData")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageData {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String name;


    public String type;
    public Date dateUploaded;

    @Lob
    @Column(name = "imagedata",length = 1000000)
   public byte[] imagedata;
    public ImageData(Long id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }



}

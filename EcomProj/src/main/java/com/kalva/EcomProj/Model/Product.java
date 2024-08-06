package com.kalva.EcomProj.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pid;
    private String pname;
    private String description;
    private String brand;
    private BigDecimal price;
    private String category;
    // we removed this date coz we have handled this in the UI , in the latest version ECOM-FRONTEND-3
//    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private Date releaseDate;
    private boolean productAvailable;
    private int stockQuantity;
    private String imageName;
    private String imageType;
    @Lob//byte[] is a large file so we need to say the spring-boot that this is large, so we are using Lob it stands for LargeObject
    private byte[] imageData;

}

package com.kalva.EcomProj.Repo;

import com.kalva.EcomProj.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//MODEL
//This is DAO
@Repository
public interface ProductRepo extends JpaRepository<Product,Integer>
{
    //JPQL- it is sql of class names and field name instead of rows and columns in db
    //CONCAT('% :keyword %') concats the %% with the keyword
    @Query("SELECT p from Product p WHERE "+
            "LOWER(p.pname) LIKE LOWER(CONCAT('%', :keyword, '%')) OR "+
            "LOWER(p.description) LIKE LOWER(CONCAT('%',:keyword,'%')) OR "+
            "LOWER(p.brand) LIKE LOWER(CONCAT('%',:keyword, '%')) OR "+
            "LOWER(p.category) LIKE LOWER(CONCAT('%',:keyword, '%'))"
    )
    List<Product> searchProducts(String keyword);
}

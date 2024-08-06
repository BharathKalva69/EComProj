package com.kalva.EcomProj.Service;

import com.kalva.EcomProj.Model.Product;
import com.kalva.EcomProj.Repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService
{
    @Autowired
    private ProductRepo repo;
    public List<Product> getAllProducts()
    {
        return repo.findAll();
    }

    public Product getProductById(int pid)
    {
        return repo.findById(pid).orElse(null);//returns optional param;
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return repo.save(product);
    }

    public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
            product.setImageData(imageFile.getBytes());
        product.setImageType(imageFile.getContentType());
        product.setImageName(imageFile.getOriginalFilename());
        return repo.save(product);

    }
public List<Product> searchProducts(String keyword)
{
    return repo.searchProducts(keyword);
}

    public void deleteProduct(int id)
    {
        repo.deleteById(id);
    }
}

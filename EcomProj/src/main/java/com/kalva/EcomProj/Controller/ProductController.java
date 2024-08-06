package com.kalva.EcomProj.Controller;

import com.kalva.EcomProj.Model.Product;
import com.kalva.EcomProj.Service.ProductService;
import jdk.jfr.Percentage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ProductController
{
    @Autowired
    private ProductService service;
    @RequestMapping("/")
    public String greet()
    {
        return "Namaste Anna!";
    }
    @GetMapping("/products")
    public ResponseEntity<List<Product>>getAllProducts()
    {
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }
    @GetMapping("/product/{pid}")

    public ResponseEntity<Product> getProduct (@PathVariable int pid)
    {
        Product product=service.getProductById(pid);
        if (product!=null)
        {
            return new ResponseEntity<>(product,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/product")//as we are using image we need to handle it in parts so dividing it into parts
    public ResponseEntity<?> addProduct(@RequestPart Product product,
                                        @RequestPart MultipartFile imageFile) {
        try {
            Product product1 = service.addProduct(product, imageFile);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("product/{pid}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int pid)
    {
        Product p= service.getProductById(pid);
        byte[]imageFile=p.getImageData();
        return ResponseEntity.ok().contentType(MediaType.valueOf(p.getImageType())).body(imageFile);
    }
    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart MultipartFile imageFile,@RequestPart Product product)
    {
        Product UpdatedProduct1=null;
       try{
           UpdatedProduct1=service.updateProduct(id, product, imageFile);
       } catch (IOException e)
       {
           return  new ResponseEntity<>("Updation Failed",HttpStatus.BAD_REQUEST);
       }
        if(UpdatedProduct1!=null)
        {
            return new ResponseEntity<>("Succes Updating",HttpStatus.OK);
        }
        else
        {
            return  new ResponseEntity<>("Updation Failed",HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id)
    {
        Product product=service.getProductById(id);
        if(product!=null) {
            service.deleteProduct(id);
            return new ResponseEntity<>("Deletion Success",HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>("Deletion failed (might not have a product)",HttpStatus.BAD_GATEWAY);
        }
    }

    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword)
    {
        List<Product> res= service.searchProducts(keyword);
        return  new ResponseEntity<>(res, HttpStatus.OK);
    }
}

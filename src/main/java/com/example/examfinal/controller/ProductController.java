package com.example.examfinal.controller;

import com.example.examfinal.entity.Product;
import com.example.examfinal.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
@Slf4j
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public String getAllProduct(Model model){
        List<Product> productList = (List<Product>) productService.findAll();
        model.addAttribute("productList", productList);
        return "index";
    }

    @GetMapping("/addProduct")
    public String addProduct(Model model){
        model.addAttribute("product", new Product());
        return "add";
    }

    @PostMapping("/addProduct")
    public String addProduct(@Valid Product product){
        productService.save(product);
        return "redirect:/product";
    }

    @GetMapping("/{id}-{quantity}")
    public String sellProduct(@PathVariable Long id, @PathVariable int quantity){
        Optional<Product> product1 = productService.findById(id);
        if (!productService.findById(id).isPresent()){
            log.error("id not exist");
        }

        if (quantity <= product1.get().getQuantity()){
            int prodQuantity = product1.get().getQuantity() - quantity;
            product1.get().setQuantity(prodQuantity);
            product1.get().setId(id);
        }

        return "redirect:/product";
    }
}

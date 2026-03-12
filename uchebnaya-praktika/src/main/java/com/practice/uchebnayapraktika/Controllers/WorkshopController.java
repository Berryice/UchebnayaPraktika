package com.practice.uchebnayapraktika.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.practice.uchebnayapraktika.Entitys.Product;
import com.practice.uchebnayapraktika.Repositories.ProductRepository;

@Controller
public class WorkshopController {
    
    @Autowired
    private ProductRepository productRepository;
    
    @GetMapping("/workshops/{productId}")
    public String showWorkshops(@PathVariable Integer productId, Model model, RedirectAttributes redirectAttributes) {
        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Продукт не найден"));
            
            model.addAttribute("product", product);
            model.addAttribute("workshops", product.getProductWorkshops());
            return "workshops";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Продукт с ID " + productId + " не найден");
            return "redirect:/";
        }
    }
}
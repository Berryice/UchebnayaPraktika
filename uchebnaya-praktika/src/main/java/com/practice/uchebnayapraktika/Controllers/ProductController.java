package com.practice.uchebnayapraktika.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.practice.uchebnayapraktika.Entitys.MaterialType;
import com.practice.uchebnayapraktika.Entitys.Product;
import com.practice.uchebnayapraktika.Entitys.ProductType;
import com.practice.uchebnayapraktika.Repositories.MaterialTypeRepository;
import com.practice.uchebnayapraktika.Repositories.ProductRepository;
import com.practice.uchebnayapraktika.Repositories.ProductTypeRepository;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
     
    @Autowired
    private ProductTypeRepository productTypeRepository;
    
    @Autowired
    private MaterialTypeRepository materialTypeRepository;

    @GetMapping("/")
    public String index(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/add_product")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("productTypes", productTypeRepository.findAll());
        model.addAttribute("materials", materialTypeRepository.findAll());
        return "add_edit";
    }
    @GetMapping("/update_product/{id}")
    public String showEditForm(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
            model.addAttribute("product", product);
            model.addAttribute("productTypes", productTypeRepository.findAll());
            model.addAttribute("materials", materialTypeRepository.findAll());
            return "add_edit";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Продукт с ID " + id + " не найден");
            return "redirect:/";
        }
    }
    @PostMapping("/save_product")
    public String saveProduct(@ModelAttribute Product product, 
                            @RequestParam("type.id") Integer typeId,
                            @RequestParam("material.id") Integer materialId,
                            RedirectAttributes redirectAttributes) {
        try {
            // Валидация цены
            if (product.getPrice() == null || product.getPrice().doubleValue() < 0) {
                throw new IllegalArgumentException("Цена не может быть отрицательной");
            }
            
            // Устанавливаем связанные сущности
            ProductType type = productTypeRepository.findById(typeId)
                    .orElseThrow(() -> new IllegalArgumentException("Неверный тип продукта"));
            MaterialType material = materialTypeRepository.findById(materialId)
                    .orElseThrow(() -> new IllegalArgumentException("Неверный тип материала"));
            
            product.setType(type);
            product.setMaterial(material);
            
            // Сохраняем
            productRepository.save(product);
            redirectAttributes.addFlashAttribute("successMessage", "Продукт успешно сохранен");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при сохранении: " + e.getMessage());
            return "redirect:/add_product";
        }
        
        return "redirect:/";
    }
}
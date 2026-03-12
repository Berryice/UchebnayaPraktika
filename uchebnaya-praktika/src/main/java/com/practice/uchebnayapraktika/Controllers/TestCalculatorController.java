package com.practice.uchebnayapraktika.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.practice.uchebnayapraktika.Services.MaterialCalculator;

@Controller
public class TestCalculatorController {
    
    @Autowired
    private MaterialCalculator materialCalculator;
    
    @GetMapping("/test-calc")
    public String testCalc(
            @RequestParam(defaultValue = "1") Integer productTypeId,
            @RequestParam(defaultValue = "1") Integer materialTypeId,
            @RequestParam(defaultValue = "10") Integer quantity,
            @RequestParam(defaultValue = "1.5") Double param1,
            @RequestParam(defaultValue = "2.0") Double param2,
            Model model) {
        
        int result = materialCalculator.calculateRawMaterialQuantity(
            productTypeId, materialTypeId, quantity, param1, param2);
        
        model.addAttribute("result", result);
        model.addAttribute("productTypeId", productTypeId);
        model.addAttribute("materialTypeId", materialTypeId);
        model.addAttribute("quantity", quantity);
        model.addAttribute("param1", param1);
        model.addAttribute("param2", param2);
        
        return "calc-result";
    }
}
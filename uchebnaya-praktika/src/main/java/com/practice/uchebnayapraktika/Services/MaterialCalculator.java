package com.practice.uchebnayapraktika.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.practice.uchebnayapraktika.Entitys.MaterialType;
import com.practice.uchebnayapraktika.Entitys.ProductType;
import com.practice.uchebnayapraktika.Repositories.MaterialTypeRepository;
import com.practice.uchebnayapraktika.Repositories.ProductTypeRepository;

@Component
public class MaterialCalculator {
    
    @Autowired
    private ProductTypeRepository productTypeRepository;
    
    @Autowired
    private MaterialTypeRepository materialTypeRepository;
    
    public int calculateRawMaterialQuantity(
            Integer productTypeId,
            Integer materialTypeId,
            Integer productQuantity,
            Double param1,
            Double param2) {
        
        // Проверка входных данных
        if (productTypeId == null || materialTypeId == null || 
            productQuantity == null || param1 == null || param2 == null) {
            return -1;
        }
        if (productQuantity <= 0 || param1 <= 0 || param2 <= 0) {
            return -1;
        }
        
        // Получаем тип продукции из БД
        Optional<ProductType> productTypeOpt = productTypeRepository.findById(productTypeId);
        if (productTypeOpt.isEmpty()) {
            return -1; // несуществующий тип продукции
        }
        
        // Получаем тип материала из БД
        Optional<MaterialType> materialTypeOpt = materialTypeRepository.findById(materialTypeId);
        if (materialTypeOpt.isEmpty()) {
            return -1; // несуществующий тип материала
        }
        
        ProductType productType = productTypeOpt.get();
        MaterialType materialType = materialTypeOpt.get();
        
        // Берем коэффициенты из БД (с защитой от null)
        double coefficient = productType.getCoefficient() != null ? 
                             productType.getCoefficient().doubleValue() : 1.0;
        double lossPercent = materialType.getLossPercent() != null ? 
                             materialType.getLossPercent().doubleValue() : 0.0;
        
        // Расчет
        double materialPerUnit = param1 * param2 * coefficient;
        double totalMaterial = materialPerUnit * productQuantity;
        double materialWithLosses = totalMaterial * (1 + lossPercent / 100);
        
        return (int) Math.ceil(materialWithLosses);
    }
}
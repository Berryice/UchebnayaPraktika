package com.practice.uchebnayapraktika.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.uchebnayapraktika.Entitys.MaterialType;

@Repository
public interface MaterialTypeRepository extends JpaRepository<MaterialType, Integer> {
}
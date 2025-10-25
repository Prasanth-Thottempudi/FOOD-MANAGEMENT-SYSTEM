package com.food.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
    
    @Column(name = "category_image", length = 10000)
    private String categoryImage;

    // One category can have many food items
    @OneToMany(
        mappedBy = "category", 
        cascade = CascadeType.ALL, // propagate persist, remove, merge, etc.
        orphanRemoval = true      // delete child items if removed from collection
    )
    @JsonManagedReference // counterpart of @JsonBackReference
    private List<FoodItem> foodItems;
}

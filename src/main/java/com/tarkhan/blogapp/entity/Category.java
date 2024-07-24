package com.tarkhan.blogapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Category name cannot be blank.")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Category description cannot be blank.")
    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "category")
    private List<Post> posts;
}

package com.tarkhan.blogapp.model.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCategoryDto {
    private Long id;
    private String name;
    private String description;
}

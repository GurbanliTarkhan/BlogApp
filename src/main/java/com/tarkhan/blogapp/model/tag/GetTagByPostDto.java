package com.tarkhan.blogapp.model.tag;

import com.tarkhan.blogapp.entity.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetTagByPostDto {
    private Long id;

    @NotBlank(message = "Tag name cannot be blank.")
    private String name;

    private List<Post> posts;

}

package com.tarkhan.blogapp.config;

import com.tarkhan.blogapp.entity.Post;
import com.tarkhan.blogapp.model.post.GetPostDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        // Custom mapping for GetPostDto
        mapper.addMappings(new PropertyMap<Post, GetPostDto>() {
            @Override
            protected void configure() {
                map().setAuthor_id(source.getAuthor().getId());
            }
        });
        return mapper;
    }
}

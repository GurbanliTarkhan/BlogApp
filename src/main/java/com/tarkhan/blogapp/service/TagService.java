package com.tarkhan.blogapp.service;

import com.tarkhan.blogapp.model.tag.GetTagByPostDto;
import com.tarkhan.blogapp.model.tag.GetTagDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TagService {
    void addTag(String tagName);
    void deleteTag(Long tagId);
    void updateTag(Long tagId, String tagName);
    Page<GetTagDto> getTags(int page, int pageSize);
    GetTagDto getTagById(Long tagId);
    GetTagDto getTagByName(String name);
    List<GetTagDto> getSearchTags(String prefix);
    GetTagByPostDto getTagByPosts(Long tagId);
}

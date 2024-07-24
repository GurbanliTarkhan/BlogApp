package com.tarkhan.blogapp.service.impl;

import com.tarkhan.blogapp.entity.Tag;
import com.tarkhan.blogapp.exception.BlogApiException;
import com.tarkhan.blogapp.exception.ResourceNotFoundException;
import com.tarkhan.blogapp.model.post.GetPostDto;
import com.tarkhan.blogapp.model.tag.GetTagByPostDto;
import com.tarkhan.blogapp.model.tag.GetTagDto;
import com.tarkhan.blogapp.repository.TagRepository;
import com.tarkhan.blogapp.service.TagService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    @Override
    public void addTag(String tagName) {
        boolean exists = tagRepository.existsByName(tagName);

        if (exists) {
            throw new BlogApiException("Tag name already exists: " + tagName);
        } else {
            Tag tag = new Tag();
            tag.setName(tagName);
            tagRepository.save(tag);
        }
    }

    @Override
    public void deleteTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));
        tagRepository.delete(tag);
    }

    @Override
    public void updateTag(Long tagId, String tagName) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));

        tag.setName(tagName);
        tagRepository.save(tag);
    }

    @Override
    public Page<GetTagDto> getTags(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Tag> tags = tagRepository.findAll(pageable);
        return tags.map(cat -> modelMapper.map(cat, GetTagDto.class));
    }

    @Override
    public GetTagDto getTagById(Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));

        GetTagDto result = modelMapper.map(tag, GetTagDto.class);
        return result;
    }

    @Override
    public GetTagDto getTagByName(String name) {
        Tag tag = tagRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "name", name));

        GetTagDto result = modelMapper.map(tag, GetTagDto.class);
        return result;
    }

    @Override
    public List<GetTagDto> getSearchTags(String prefix) {
        List<Tag> tags = tagRepository.findAll();
        List<GetTagDto> result = tags.stream()
                .filter(tag -> tag.getName().toLowerCase()
                        .startsWith(prefix.toLowerCase()
                        )).map(tag -> modelMapper.map(tag, GetTagDto.class))
                .collect(Collectors.toList());
        return result;
    }

    @Override
    public GetTagByPostDto getTagByPosts(Long tagid) {
        Tag tag = tagRepository.findById(tagid)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "Tag ID", tagid));

        GetTagByPostDto dto = modelMapper.map(tag, GetTagByPostDto.class);
        List<GetPostDto> postDto = tag.getPosts().stream()
                .map(post -> modelMapper.map(post, GetPostDto.class))
                .collect(Collectors.toList());

        return dto;
    }
}

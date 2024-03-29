package com.FinalProject.mapper;

import com.FinalProject.dto.CategoryDto;
import com.FinalProject.model.Category;
import com.FinalProject.dto.CategoryRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {
    public CategoryDto categoryToCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public Category categoryDtoToCategory(CategoryDto categoryDto) {
        return Category.builder()
                .name(categoryDto.getName())
                .book(categoryDto.getBooks())
                .build();
    }

    public Category categoryRequestToCategory(CategoryRequest categoryRequest) {
        return Category.builder()
                .name(categoryRequest.getName())
                .build();
    }

    public List<CategoryDto> categoryDtoListToCategoryList(List<Category> categories) {
        return categories.stream()
                .map(this::categoryToCategoryDto)
                .collect(Collectors.toList());
    }
}


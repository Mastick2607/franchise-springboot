package com.franquiciasSpringBoot.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Branch {
    private String id = UUID.randomUUID().toString();
    private String name;
    private List<Product> products = new ArrayList<>();
}


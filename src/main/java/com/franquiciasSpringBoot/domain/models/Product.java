package com.franquiciasSpringBoot.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String id = UUID.randomUUID().toString();
    private String name;
    private int stock;
}

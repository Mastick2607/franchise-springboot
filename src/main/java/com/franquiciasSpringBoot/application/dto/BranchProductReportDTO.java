package com.franquiciasSpringBoot.application.dto;


import com.franquiciasSpringBoot.domain.models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchProductReportDTO {
    private String branchName;
    private Product topProduct;
}

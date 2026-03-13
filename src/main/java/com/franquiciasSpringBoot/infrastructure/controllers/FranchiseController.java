package com.franquiciasSpringBoot.infrastructure.controllers;

import com.franquiciasSpringBoot.application.dto.*;
import com.franquiciasSpringBoot.application.services.FranchiseService;
import com.franquiciasSpringBoot.domain.models.Franchise;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/franchises")
@RequiredArgsConstructor
public class FranchiseController {

    private final FranchiseService franchiseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Franchise> createFranchise(@RequestBody FranchiseRequestDTO dto) {
        return franchiseService.createFranchise(dto);
    }

    @PostMapping("/{franchiseId}/branches")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Franchise> addBranch(
            @PathVariable String franchiseId,
            @RequestBody BranchRequestDTO branchDTO) {
        return franchiseService.addBranch(franchiseId, branchDTO.getName());
    }

    @PostMapping("/{franchiseId}/branches/{branchId}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Franchise> addProduct(
            @PathVariable String franchiseId,
            @PathVariable String branchId,
            @RequestBody ProductRequestDTO productDTO) {
        return franchiseService.addProductToBranch(franchiseId, branchId,
                productDTO.getName(),
                productDTO.getStock()
        );
    }

    @DeleteMapping("/{franchiseId}/branches/{branchId}/products/{productId}")
    @ResponseStatus(HttpStatus.OK) // Devolvemos 200 OK con el objeto actualizado
    public Mono<Franchise> deleteProduct(
            @PathVariable String franchiseId,
            @PathVariable String branchId,
            @PathVariable String productId) {
        return franchiseService.deleteProductFromBranch(franchiseId, branchId, productId);
    }

    @PatchMapping("/{franchiseId}/branches/{branchId}/products/{productId}/stock")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Franchise> updateStock(
            @PathVariable String franchiseId,
            @PathVariable String branchId,
            @PathVariable String productId,
            @RequestBody ProductStockRequestDTO stockDTO) {
        return franchiseService.updateProductStock(franchiseId, branchId, productId, stockDTO.getStock()
        );
    }

    @GetMapping("/{franchiseId}/top-products")
    public Flux<BranchProductReportDTO> getTopProducts(@PathVariable String franchiseId) {
        return franchiseService.getTopStockProductsByFranchise(franchiseId);
    }

    @PatchMapping("/{id}/name")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Franchise> updateName(
            @PathVariable String id,
            @RequestBody FranchiseRequestDTO nameDTO) {
        return franchiseService.updateFranchiseName(id, nameDTO.getName());
    }

    @PatchMapping("/{franchiseId}/branches/{branchId}/name")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Franchise> updateBranchName(
            @PathVariable String franchiseId,
            @PathVariable String branchId,
            @RequestBody BranchRequestDTO nameDTO) {
        return franchiseService.updateBranchName(franchiseId, branchId, nameDTO.getName()
        );
    }

    @PatchMapping("/{franchiseId}/branches/{branchId}/products/{productId}/name")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Franchise> updateProductName(
            @PathVariable String franchiseId,
            @PathVariable String branchId,
            @PathVariable String productId,
            @RequestBody ProductNameRequestDTO nameDTO) {
        return franchiseService.updateProductName(franchiseId, branchId, productId,
                nameDTO.getName()
        );
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<Franchise> getAll() {
        return franchiseService.getAllFranchises();
    }

}

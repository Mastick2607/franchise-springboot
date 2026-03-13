package com.franquiciasSpringBoot.application.services;

import com.franquiciasSpringBoot.application.dto.BranchProductReportDTO;
import com.franquiciasSpringBoot.application.dto.FranchiseRequestDTO;
import com.franquiciasSpringBoot.domain.models.Branch;
import com.franquiciasSpringBoot.domain.models.Franchise;
import com.franquiciasSpringBoot.domain.models.Product;
import com.franquiciasSpringBoot.domain.ports.FranchiseRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class FranchiseService {

    private final FranchiseRepositoryPort franchiseRepositoryPort;

    public Mono<Franchise> createFranchise(FranchiseRequestDTO dto) {
        Franchise franchise = new Franchise();
        franchise.setName(dto.getName());
        franchise.setBranches(new ArrayList<>()); // Inicializamos la lista de sucursales vacía

        return franchiseRepositoryPort.save(franchise);
    }


    public Mono<Franchise> addBranch(String franchiseId, String branchName) {
        return franchiseRepositoryPort.findById(franchiseId)
                .flatMap(franchise -> {

                    // Creamos el objeto de la nueva sucursal
                    Branch newBranch = new Branch();
                    newBranch.setName(branchName);
                    newBranch.setProducts(new ArrayList<>());

                    // Si la lista es nula por alguna razón, la inicializamos
                    if (franchise.getBranches() == null) {
                        franchise.setBranches(new ArrayList<>());
                    }

                    franchise.getBranches().add(newBranch);


                    return franchiseRepositoryPort.save(franchise);
                            //.thenReturn(newBranch);

                })
                .switchIfEmpty(Mono.error(new RuntimeException("No se encontró la franquicia con ID: " + franchiseId)));
    }

    public Mono<Franchise> addProductToBranch(String franchiseId, String branchId, String productName, int stock) {
        return franchiseRepositoryPort.findById(franchiseId)
                .flatMap(franchise -> {
                    // Buscamos la sucursal específica dentro de la franquicia
                    return Mono.justOrEmpty(franchise.getBranches().stream()
                                    .filter(branch -> branch.getId().equals(branchId))
                                    .findFirst())
                            .flatMap(branch -> {
                                // Creamos el nuevo producto (el ID se genera solo en el modelo)
                                Product newProduct = new Product();
                                newProduct.setName(productName);
                                newProduct.setStock(stock);

                                // Añadimos a la sucursal (si product es null lo agregamos la lista vacia)
                                if (branch.getProducts() == null) {
                                    branch.setProducts(new ArrayList<>());
                                }
                                branch.getProducts().add(newProduct);

                                // Guardammos toda la franquicia y retornar solo el producto
                                return franchiseRepositoryPort.save(franchise);
                                        //.thenReturn(newProduct);
                            });
                })
                .switchIfEmpty(Mono.error(new RuntimeException("No se encontró la franquicia o la sucursal")));
    }

    public Mono<Franchise> deleteProductFromBranch(String franchiseId, String branchId, String productId) {
        return franchiseRepositoryPort.findById(franchiseId)
                .flatMap(franchise -> {
                    //  Buscamos la sucursal
                    return Mono.justOrEmpty(franchise.getBranches().stream()
                                    .filter(branch -> branch.getId().equals(branchId))
                                    .findFirst())
                            .flatMap(branch -> {
                                // Eliminamos el producto de la lista de la sucursal
                                boolean removed = branch.getProducts()
                                        .removeIf(product -> product.getId().equals(productId));

                                if (!removed) {
                                    return Mono.error(new RuntimeException("Producto no encontrado en esta sucursal"));
                                }

                                // Guardamos los cambios en la base de datos
                                return franchiseRepositoryPort.save(franchise);
                            });
                })
                .switchIfEmpty(Mono.error(new RuntimeException("No se encontró la franquicia o la sucursal")));
    }

    public Mono<Franchise> updateProductStock(String franchiseId, String branchId, String productId, int newStock) {
        return franchiseRepositoryPort.findById(franchiseId)
                .flatMap(franchise -> {
                    // Buscamos la sucursal por ID
                    return Mono.justOrEmpty(franchise.getBranches().stream()
                                    .filter(branch -> branch.getId().equals(branchId))
                                    .findFirst())
                            .flatMap(branch -> {
                                // Buscamos el producto por ID dentro de esa sucursal
                                return Mono.justOrEmpty(branch.getProducts().stream()
                                                .filter(product -> product.getId().equals(productId))
                                                .findFirst())
                                        .flatMap(product -> {
                                            //Modificamos el stock
                                            product.setStock(newStock);

                                            // Guardamos los cambios
                                            return franchiseRepositoryPort.save(franchise);
                                        });
                            });
                })
                .switchIfEmpty(Mono.error(new RuntimeException("No se encontró la franquicia, sucursal o producto")));
    }

    public Flux<BranchProductReportDTO> getTopStockProductsByFranchise(String franchiseId) {
        return franchiseRepositoryPort.findById(franchiseId)
                .flatMapMany(franchise -> Flux.fromIterable(franchise.getBranches())
                        .flatMap(branch -> {
                            // Buscamos el producto con mayor stock en esta sucursal
                            return Mono.justOrEmpty(branch.getProducts().stream()
                                            .max(Comparator.comparingInt(Product::getStock)))
                                    .map(product -> new BranchProductReportDTO(branch.getName(), product));
                        })
                );
    }

    public Mono<Franchise> updateFranchiseName(String id, String newName) {
        return franchiseRepositoryPort.findById(id)
                .flatMap(franchise -> {
                    franchise.setName(newName);
                    return franchiseRepositoryPort.save(franchise);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Franquicia no encontrada con ID: " + id)));
    }

    public Mono<Franchise> updateBranchName(String franchiseId, String branchId, String newName) {
        return franchiseRepositoryPort.findById(franchiseId)
                .flatMap(franchise -> {
                    // 1. Buscamos la sucursal específica dentro de la lista
                    return Mono.justOrEmpty(franchise.getBranches().stream()
                                    .filter(branch -> branch.getId().equals(branchId))
                                    .findFirst())
                            .flatMap(branch -> {
                                // 2. Actualizamos el nombre
                                branch.setName(newName);
                                // 3. Guardamos la franquicia completa y la retornamos
                                return franchiseRepositoryPort.save(franchise);
                            });
                })
                .switchIfEmpty(Mono.error(new RuntimeException("No se encontró la franquicia o la sucursal")));
    }

    public Mono<Franchise> updateProductName(String franchiseId, String branchId, String productId, String newName) {
        return franchiseRepositoryPort.findById(franchiseId)
                .flatMap(franchise -> {
                    // 1. Buscamos la sucursal
                    return Mono.justOrEmpty(franchise.getBranches().stream()
                                    .filter(branch -> branch.getId().equals(branchId))
                                    .findFirst())
                            .flatMap(branch -> {
                                // 2. Buscamos el producto dentro de esa sucursal
                                return Mono.justOrEmpty(branch.getProducts().stream()
                                                .filter(product -> product.getId().equals(productId))
                                                .findFirst())
                                        .flatMap(product -> {
                                            // 3. Actualizamos el nombre y guardamos la raíz
                                            product.setName(newName);
                                            return franchiseRepositoryPort.save(franchise);
                                        });
                            });
                })
                .switchIfEmpty(Mono.error(new RuntimeException("No se encontró la ruta: Franquicia/Sucursal/Producto")));
    }

    public Flux<Franchise> getAllFranchises() {
        return franchiseRepositoryPort.findAll();
    }
}

package com.franquiciasSpringBoot.infrastructure.adapters;

import com.franquiciasSpringBoot.domain.models.Franchise;
import com.franquiciasSpringBoot.domain.ports.FranchiseRepositoryPort;
import com.franquiciasSpringBoot.infrastructure.adapters.mongodb.SpringDataFranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FranchiseRepositoryAdapter implements FranchiseRepositoryPort {
    private final SpringDataFranchiseRepository repository;

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        return repository.save(franchise);
    }

    @Override
    public Mono<Franchise> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Flux<Franchise> findAll() {
        return repository.findAll();
    }
}

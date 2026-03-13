package com.franquiciasSpringBoot.infrastructure.adapters.mongodb;

import com.franquiciasSpringBoot.domain.models.Franchise;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SpringDataFranchiseRepository  extends ReactiveMongoRepository<Franchise, String> {
}



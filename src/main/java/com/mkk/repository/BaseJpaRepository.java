package com.mkk.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface BaseJpaRepository<T, K > extends JpaRepository<T, K>, JpaSpecificationExecutor<T> {
    Page<T> findAll(Specification<T> specification, Pageable pageable);
}

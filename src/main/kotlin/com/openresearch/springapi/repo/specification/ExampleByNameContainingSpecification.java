package com.openresearch.springapi.repo.specification;

import com.openresearch.springapi.model.Example;
import com.openresearch.springapi.model.Example_;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * This is in java because we compile kotlin before java so we can't access the generated Example_ from kotlin.
 * TODO: find another solution for this
 */
public class ExampleByNameContainingSpecification implements Specification<Example> {

  private final String name;

  public ExampleByNameContainingSpecification(final String name) {
    this.name = name;
  }

  @Override
  public Predicate toPredicate(
    @NotNull final Root<Example> root,
    @NotNull final CriteriaQuery<?> query,
    @NotNull final CriteriaBuilder criteriaBuilder
  ) {
    return criteriaBuilder.like(root.get(Example_.NAME), "%" + name + "%");
  }
}

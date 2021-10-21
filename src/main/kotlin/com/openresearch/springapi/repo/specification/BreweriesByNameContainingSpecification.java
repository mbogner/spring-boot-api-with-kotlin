package com.openresearch.springapi.repo.specification;

import com.openresearch.springapi.model.Brewery;
import com.openresearch.springapi.model.Brewery_;
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
public class BreweriesByNameContainingSpecification implements Specification<Brewery> {

  private final String name;

  public BreweriesByNameContainingSpecification(final String name) {
    this.name = name;
  }

  @Override
  public Predicate toPredicate(
    @NotNull final Root<Brewery> root,
    @NotNull final CriteriaQuery<?> query,
    @NotNull final CriteriaBuilder criteriaBuilder
  ) {
    return criteriaBuilder.like(root.get(Brewery_.NAME), "%" + name + "%");
  }
}

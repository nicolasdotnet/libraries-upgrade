package com.bigcity.apiweb.specifications;

import com.bigcity.apiweb.entity.User;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author nicolasdotnet
 *
 * This class is used to search Users specifying the criteria
 *
 */
public class UserSpecification implements Specification<User> {

    private UserCriteria filter;

    public UserSpecification(UserCriteria filter) {
        super();
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();

        if (filter.getEmail() != null) {
            predicate.getExpressions().add(criteriaBuilder.equal(root.get("email"), filter.getEmail()));
        }

        if (filter.getFirstname() != null) {
            predicate.getExpressions().add(criteriaBuilder.equal(root.get("firstname"), filter.getFirstname()));
        }

        if (filter.getLastname() != null) {
            predicate.getExpressions().add(criteriaBuilder.equal(root.get("lastname"), filter.getLastname()));
        }

        if (filter.getRole() != null) {
            predicate.getExpressions().add(criteriaBuilder.equal(root.join("role").get("roleName"), filter.getRole()));
        }
        
        return criteriaBuilder.and(predicate);

    }

}

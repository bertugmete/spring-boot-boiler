package com.mkk.dataaccess.common.specification;

import com.mkk.dataaccess.common.AbstractQuerySpecification;
import com.mkk.entity.user.MkkUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class MkkUserSpec extends AbstractQuerySpecification<MkkUser> {

    private String firstName;
    private String lastName;
    @Override
    public Predicate toPredicate(Root<MkkUser> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();
        addFieldEqualsPredicate(cb, root, predicates, "firstName");
        addFieldEqualsPredicate(cb, root, predicates, "lastName");

        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}

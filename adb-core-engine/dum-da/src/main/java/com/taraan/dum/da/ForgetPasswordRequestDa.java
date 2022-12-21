package com.taraan.dum.da;

import com.taraan.dum.model.hibernate.ForgetPasswordRequest;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class ForgetPasswordRequestDa extends GenericDa<ForgetPasswordRequest> {

    public ForgetPasswordRequest getByEmail(String email) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<ForgetPasswordRequest> criteria = builder.createQuery(getType());
        Root<ForgetPasswordRequest> root = criteria.from(getType());
        criteria.select(root);
        Predicate where = builder.equal(root.get("email"), email);
        criteria.where(where);
        List<ForgetPasswordRequest> resultList = getSession().createQuery(criteria).setMaxResults(1).getResultList();
        if (resultList.isEmpty())
            return null;
        return resultList.get(0);
    }

}

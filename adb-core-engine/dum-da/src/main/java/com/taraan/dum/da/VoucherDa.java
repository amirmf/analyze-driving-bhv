package com.taraan.dum.da;

import com.taraan.dum.model.hibernate.Voucher;
import com.taraan.dum.model.hibernate.VoucherState;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.Queue;


@Component
public class VoucherDa extends GenericDa<Voucher> {
    public Voucher get(String code, Long reward) {
        return (Voucher) this.getSession().createQuery("select voucher from Voucher voucher where voucher.code = :CODE and voucher.reward.id = :REWARD")
                .setParameter("CODE", code).setParameter("REWARD", reward).uniqueResult();
    }

    public List<Voucher> get(Long reward, VoucherState voucherState) {
        return  this.getSession().createQuery("select voucher from Voucher voucher where voucher.voucherState = :STATE and voucher.reward.id = :REWARD")
                .setParameter("STATE", voucherState).setParameter("REWARD", reward).list();
    }

    public List<Voucher> get(Long reward, String state, String code, Date fromDate, Date toDate, Long from, Long size) {
        String queryString = "select voucher from Voucher voucher ";
        String whereString = "";
        if (reward != null) {
            whereString += " and voucher.reward.id = :REWARD_ID ";
        }
        if (state != null && !state.isEmpty()) {
            whereString += " and voucher.voucherState = :STATE ";
        }
        if (code != null && !code.isEmpty()) {
            whereString += " and voucher.code = :CODE ";
        }
        if (fromDate != null) {
            whereString += " and voucher.date > :FROM_DATE ";

        }
        if (toDate != null) {
            whereString += " and voucher.date < :TO_DATE ";
        }
        if (!whereString.isEmpty())
            queryString += " where " + whereString.substring(4);
        Query query = this.getSession().createQuery(queryString);
        if (reward != null) {
            query.setParameter("REWARD_ID", reward);
        }
        if (state != null && !state.isEmpty()) {
            query.setParameter("STATE", VoucherState.valueOf(state));
        }
        if (code != null && !code.isEmpty()) {
            query.setParameter("CODE", code);
        }
        if (fromDate != null) {
            query.setParameter("FROM_DATE", fromDate);
        }
        if (toDate != null) {
            query.setParameter("TO_DATE", toDate);
        }
        if (from != null)
            query.setFirstResult(from.intValue());
        if (size != null)
            query.setMaxResults(size.intValue());

        return query.getResultList();
    }

    public Long getCount(Long reward, String state, String code, Date fromDate, Date toDate) {
        String queryString = "select count(voucher.id) from Voucher voucher ";
        String whereString = "";
        if (reward != null) {
            whereString += " and voucher.reward.id = :REWARD_ID ";
        }
        if (state != null && !state.isEmpty()) {
            whereString += " and voucher.voucherState = :STATE ";
        }
        if (code != null && !code.isEmpty()) {
            whereString += " and voucher.code = :CODE ";
        }
        if (fromDate != null) {
            whereString += " and voucher.date > :FROM_DATE ";

        }
        if (toDate != null) {
            whereString += " and voucher.date < :TO_DATE ";
        }
        if (!whereString.isEmpty())
            queryString += " where " + whereString.substring(4);
        Query query = this.getSession().createQuery(queryString);
        if (reward != null) {
            query.setParameter("REWARD_ID", reward);
        }
        if (state != null && !state.isEmpty()) {
            query.setParameter("STATE", VoucherState.valueOf(state));
        }
        if (code != null && !code.isEmpty()) {
            query.setParameter("CODE", code);
        }
        if (fromDate != null) {
            query.setParameter("FROM_DATE", fromDate);
        }
        if (toDate != null) {
            query.setParameter("TO_DATE", toDate);
        }
        return (Long) query.getSingleResult();
    }
}

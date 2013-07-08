/**
 * <pre>
 * Copyright:		Copyright(C) 2012-2013, feinno.com
 * Filename:		com.feinno.pay.service.impl.OrderDayServiceImpl.java
 * Class:			OrderDayServiceImpl
 * Date:			2013-4-21
 * Author:			<a href="mailto:sundful@gmail.com">sundful</a>
 * Version          2.0.0
 * Description:		
 *
 * </pre>
 **/

package com.feinno.pay.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.feinno.pay.dao.OrderDayDAO;
import com.feinno.pay.entity.OrderDay;
import com.feinno.pay.service.OrderDayService;
import com.feinno.security.util.dwz.Page;
import com.feinno.security.util.dwz.PageUtils;

/**
 * 
 * @author <a href="mailto:sundful@gmail.com">sundful</a>
 * @version 2.0.0
 * @since 2013-4-21 下午7:56:11
 */
@Service
@Transactional
public class OrderDayServiceImpl implements OrderDayService {

	@Autowired
	private OrderDayDAO orderDayDAO;

	@Override
	public void delete(Long id) {
		this.orderDayDAO.delete(id);

	}

	@Override
	public OrderDay get(Long id) {
		return this.orderDayDAO.findOne(id);
	}

	@Override
	public void update(OrderDay orderDay) {
		this.orderDayDAO.save(orderDay);

	}

	@Override
	public List<OrderDay> findAll(Page page) {
		page.setOrderField("statDate");
		page.setOrderDirection(Page.ORDER_DIRECTION_ASC);
		org.springframework.data.domain.Page<OrderDay> springDataPage = this.orderDayDAO
				.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public List<OrderDay> find(Page page, String channel, Date fromDate,
			Date toDate) {

		page.setOrderField("statDate");
		page.setOrderDirection(Page.ORDER_DIRECTION_ASC);
		org.springframework.data.domain.Page<OrderDay> springDataPage = (org.springframework.data.domain.Page<OrderDay>) orderDayDAO
				.findAll(buildOrderDaySpecification(channel, fromDate, toDate),
						PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	private Specification<OrderDay> buildOrderDaySpecification(
			final String channel, final Date fromDate, final Date toDate) {
		return new Specification<OrderDay>() {
			public Predicate toPredicate(Root<OrderDay> root,
					CriteriaQuery<?> q, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (StringUtils.hasText(channel)) {
					Path<String> np = root.get("channel");
					predicates.add(cb.like(np, "%" + channel + "%"));
				}
				if (fromDate != null) {
					Path<Date> np = root.get("statDate");
					predicates.add(cb.greaterThanOrEqualTo(np, fromDate));
				}
				if (toDate != null) {
					Path<Date> np = root.get("statDate");
					predicates.add(cb.lessThanOrEqualTo(np, toDate));
				}

				if (predicates.size() > 0) {
					return cb.and(predicates.toArray(new Predicate[predicates
							.size()]));
				}
				return cb.conjunction();
			}

		};
	}

}

/**
 * <pre>
 * Copyright:		Copyright(C) 2012-2013, feinno.com
 * Filename:		com.feinno.pay.service.impl.OrderServiceImpl.java
 * Class:			OrderServiceImpl
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

import com.feinno.pay.dao.OrderDAO;
import com.feinno.pay.entity.Order;
import com.feinno.pay.service.OrderService;
import com.feinno.security.util.dwz.EmagUtil.StatusCode;
import com.feinno.security.util.dwz.Page;
import com.feinno.security.util.dwz.PageUtils;
import com.feinno.security.util.dwz.StringUtil;

/**
 * 
 * @author <a href="mailto:sundful@gmail.com">sundful</a>
 * @version 2.0.0
 * @since 2013-4-21 下午7:56:11
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDAO orderDAO;

	@Override
	public void save(Order order) {
		this.orderDAO.save(order);

	}

	@Override
	public void save(String code, String phone, String money, String orderId) {
		if (StringUtil.isEmpty(code, phone, money, orderId)) {
			return;
		}
		Order order = new Order();
		order.setChannel(code);
		order.setPhone(phone);
		order.setMoney(Integer.parseInt(money));
		order.setOrderId(orderId);
		order.setCreateTime(new Date());
		// 设置订单状态为短信下发未确认的状态
		order.setStatus(StatusCode.PENDING_INFO.getCode());
		order.setNotifyTime(new Date());
		this.orderDAO.save(order);
	}

	@Override
	public void delete(Long id) {
		this.orderDAO.delete(id);

	}

	@Override
	public Order get(Long id) {
		return this.orderDAO.findOne(id);
	}

	@Override
	public Order get(String orderId) {
		return this.orderDAO.findByOrderId(orderId);
	}

	@Override
	public void update(Order order) {
		this.orderDAO.save(order);

	}

	@Override
	public void update(Order order, String resultCode) {
		if (StringUtil.isEmpty(order, resultCode)) {
			return;
		}
		order.setStatus(resultCode);
		order.setNotifyTime(new Date());
		this.orderDAO.save(order);
	}

	@Override
	public List<Order> findAll(Page page) {
		org.springframework.data.domain.Page<Order> springDataPage = this.orderDAO
				.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public List<Order> find(Page page, String channel, String orderId,
			Date fromDate, Date toDate) {

		page.setOrderField("createTime");
		page.setOrderDirection(Page.ORDER_DIRECTION_DESC);
		org.springframework.data.domain.Page<Order> springDataPage = (org.springframework.data.domain.Page<Order>) orderDAO
				.findAll(
						buildOrderSpecification(channel, true, orderId,
								fromDate, toDate), PageUtils
								.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public List<Order> find(Page page, String channel, boolean status,
			String orderId, Date fromDate, Date toDate) {
		page.setOrderField("createTime");
		page.setOrderDirection(Page.ORDER_DIRECTION_DESC);
		org.springframework.data.domain.Page<Order> springDataPage = (org.springframework.data.domain.Page<Order>) orderDAO
				.findAll(
						buildOrderSpecification(channel, status, orderId,
								fromDate, toDate), PageUtils
								.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	private Specification<Order> buildOrderSpecification(final String channel,
			final boolean status, final String orderId, final Date fromDate,
			final Date toDate) {
		return new Specification<Order>() {
			public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> q,
					CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (StringUtils.hasText(channel)) {
					Path<String> np = root.get("channel");
					predicates.add(cb.like(np, "%" + channel + "%"));
				}
				if (status) {
					Path<String> np = root.get("status");
					predicates.add(cb.equal(np,
							StatusCode.SUCCESS_INFO.getCode()));
				} else {
					Path<String> np = root.get("status");
					predicates.add(cb.notEqual(np,
							StatusCode.SUCCESS_INFO.getCode()));
				}
				if (StringUtils.hasText(orderId)) {
					Path<String> np = root.get("orderId");
					predicates.add(cb.like(np, "%" + orderId + "%"));
				}
				if (fromDate != null) {
					Path<Date> np = root.get("createTime");
					predicates.add(cb.greaterThanOrEqualTo(np, fromDate));
				}
				if (toDate != null) {
					Path<Date> np = root.get("createTime");
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

	@Override
	public Order checkOrderLegal(String phone, String orderId) {
		Order order = this.orderDAO.findByPhoneAndOrderId(phone, orderId);
		if (order == null) {
			return null;
		}
		String status = order.getStatus();

		if (status != null
				&& StatusCode.SUCCESS_INFO.getCode().equals(status.trim())) {
			return null;
		}
		return order;
	}

}

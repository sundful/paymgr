package com.feinno.pay.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.feinno.pay.dao.DataDAO;
import com.feinno.pay.entity.Data;
import com.feinno.pay.service.DataService;
import com.feinno.security.exception.ExistedException;
import com.feinno.security.exception.ServiceException;
import com.feinno.security.util.dwz.Page;
import com.feinno.security.util.dwz.PageUtils;

@Component
@Transactional
public class DataServiceImpl implements DataService {

	@Autowired
	private DataDAO dataDAO;

	@Override
	public void save(Data data) throws ExistedException {
		String code = data.getCode();
		if (this.dataDAO.findByCode(code) != null) {
			throw new ExistedException("游戏添加失败，游戏代码：" + code + "已存在。");
		}
		this.dataDAO.save(data);

	}

	@Override
	public void delete(Long id) throws ServiceException {
		Data data = this.dataDAO.findOne(id);

		if (data.getChannel().size() > 0) {
			throw new ServiceException(data.getName() + "游戏下存在渠道信息，不允许删除。");
		}

		if (data.getProduct().size() > 0) {
			throw new ServiceException(data.getName() + "游戏下存在计费代码，不允许删除。");
		}
		this.dataDAO.delete(id);

	}

	@Override
	public void delete(Data data) throws ServiceException {
		if (data == null) {
			throw new ServiceException("游戏对象为null，不能删除。");
		}

		if (data.getChannel().size() > 0) {
			throw new ServiceException(data.getName() + "游戏下存在渠道信息，不允许删除。");
		}

		if (data.getProduct().size() > 0) {
			throw new ServiceException(data.getName() + "游戏下存在计费代码，不允许删除。");
		}
		this.dataDAO.delete(data);
	}

	@Override
	public Data get(Long id) {

		return this.dataDAO.findOne(id);
	}

	@Override
	public void update(Data data) {
		this.dataDAO.save(data);

	}

	@Override
	public List<Data> findAll(Page page) {
		org.springframework.data.domain.Page<Data> springDataPage = this.dataDAO
				.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public List<Data> findAll() {
		return this.dataDAO.findAll();
	}

	@Override
	public List<Data> find(Page page, String keywords) {
		page.setOrderField("id");
		page.setOrderDirection(Page.ORDER_DIRECTION_DESC);
		org.springframework.data.domain.Page<Data> springDataPage = (org.springframework.data.domain.Page<Data>) dataDAO
				.findAll(buildDataSpecification(keywords),
						PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	private Specification<Data> buildDataSpecification(final String keywords) {

		return new Specification<Data>() {

			@Override
			public Predicate toPredicate(Root<Data> root, CriteriaQuery<?> q,
					CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (StringUtils.hasText(keywords)) {
					Path<String> np = root.get("name");
					predicates.add(cb.like(np, "%" + keywords + "%"));
					Path<String> np1 = root.get("code");
					predicates.add(cb.like(np1, "%" + keywords + "%"));
				}
				if (predicates.size() > 0) {
					return cb.or(predicates.toArray(new Predicate[predicates
							.size()]));
				}
				return cb.conjunction();
			}
		};
	}

}

package com.feinno.pay.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.feinno.pay.dao.ProductDAO;
import com.feinno.pay.entity.Product;
import com.feinno.pay.service.ProductService;
import com.feinno.security.exception.ExistedException;
import com.feinno.security.exception.ServiceException;
import com.feinno.security.util.dwz.Page;
import com.feinno.security.util.dwz.PageUtils;

@Component
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDAO productDAO;

	@Override
	public void save(Product product) throws ExistedException {
		String code = product.getCode();
		if (this.productDAO.findByCode(code) != null) {
			throw new ExistedException("计费代码添加失败，计费代码：" + code + "已存在。");
		}
		this.productDAO.save(product);

	}

	@Override
	public void delete(Long id) throws ServiceException {
		this.productDAO.delete(id);

	}

	@Override
	public Product get(Long id) {

		return this.productDAO.findOne(id);
	}

	@Override
	public void update(Product product) {
		this.productDAO.save(product);

	}

	@Override
	public List<Product> findAll(Page page) {
		org.springframework.data.domain.Page<Product> springProductPage = this.productDAO
				.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springProductPage.getTotalElements());
		return springProductPage.getContent();
	}

	@Override
	public List<Product> find(Page page, String keywords) {
		page.setOrderField("id");
		page.setOrderDirection(Page.ORDER_DIRECTION_DESC);
		org.springframework.data.domain.Page<Product> springDataPage = (org.springframework.data.domain.Page<Product>) productDAO
				.findByCodeContaining(keywords, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

}

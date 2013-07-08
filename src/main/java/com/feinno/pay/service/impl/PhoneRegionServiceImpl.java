package com.feinno.pay.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.feinno.pay.dao.PhoneRegionDAO;
import com.feinno.pay.entity.PhoneRegion;
import com.feinno.pay.service.PhoneRegionService;
import com.feinno.security.util.dwz.Page;
import com.feinno.security.util.dwz.PageUtils;

@Component
@Transactional
public class PhoneRegionServiceImpl implements PhoneRegionService {

	@Autowired
	private PhoneRegionDAO regionDAO;

	@Override
	public void save(PhoneRegion region) {
		this.regionDAO.save(region);

	}

	@Override
	public void delete(Long id) {
		this.regionDAO.delete(id);

	}

	@Override
	public PhoneRegion get(Long id) {

		return this.regionDAO.findOne(id);
	}

	@Override
	public void update(PhoneRegion region) {
		this.regionDAO.save(region);

	}

	@Override
	public List<PhoneRegion> findAll(Page page) {
		org.springframework.data.domain.Page<PhoneRegion> springPhoneRegionPage = this.regionDAO
				.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springPhoneRegionPage.getTotalElements());
		return springPhoneRegionPage.getContent();
	}

}

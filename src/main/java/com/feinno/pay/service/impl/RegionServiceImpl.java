package com.feinno.pay.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.feinno.pay.dao.RegionDAO;
import com.feinno.pay.entity.Region;
import com.feinno.pay.service.RegionService;
import com.feinno.security.util.dwz.Page;
import com.feinno.security.util.dwz.PageUtils;

@Component
@Transactional
public class RegionServiceImpl implements RegionService {

	@Autowired
	private RegionDAO regionDAO;

	@Override
	public void save(Region region) {
		this.regionDAO.save(region);

	}

	@Override
	public void delete(Long id) {
		this.regionDAO.delete(id);

	}

	@Override
	public Region get(Long id) {

		return this.regionDAO.findOne(id);
	}

	@Override
	public void update(Region region) {
		this.regionDAO.save(region);

	}

	@Override
	public List<Region> findAll(Page page) {
		org.springframework.data.domain.Page<Region> springRegionPage = this.regionDAO
				.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springRegionPage.getTotalElements());
		return springRegionPage.getContent();
	}

}

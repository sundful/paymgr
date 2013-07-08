package com.feinno.pay.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.feinno.pay.dao.RegionBlacklistDAO;
import com.feinno.pay.entity.RegionBlacklist;
import com.feinno.pay.service.RegionBlacklistService;
import com.feinno.security.util.dwz.Page;
import com.feinno.security.util.dwz.PageUtils;

@Component
@Transactional
public class RegionBlacklistServiceImpl implements RegionBlacklistService {

	@Autowired
	private RegionBlacklistDAO blacklistDAO;

	@Override
	public void save(RegionBlacklist blacklist) {
		this.blacklistDAO.save(blacklist);

	}

	@Override
	public void delete(Long id) {
		this.blacklistDAO.delete(id);

	}

	@Override
	public RegionBlacklist get(Long id) {

		return this.blacklistDAO.findOne(id);
	}

	@Override
	public void update(RegionBlacklist blacklist) {
		this.blacklistDAO.save(blacklist);

	}

	@Override
	public List<RegionBlacklist> findAll(Page page) {
		org.springframework.data.domain.Page<RegionBlacklist> springRegionPage = this.blacklistDAO
				.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springRegionPage.getTotalElements());
		return springRegionPage.getContent();
	}

}

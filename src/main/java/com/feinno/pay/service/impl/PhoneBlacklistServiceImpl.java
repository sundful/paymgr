package com.feinno.pay.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.feinno.pay.dao.PhoneBlacklistDAO;
import com.feinno.pay.entity.PhoneBlacklist;
import com.feinno.pay.service.PhoneBlacklistService;
import com.feinno.security.util.dwz.Page;
import com.feinno.security.util.dwz.PageUtils;

@Component
@Transactional
public class PhoneBlacklistServiceImpl implements PhoneBlacklistService {

	@Autowired
	private PhoneBlacklistDAO blacklistDAO;

	@Override
	public void save(PhoneBlacklist blacklist) {
		this.blacklistDAO.save(blacklist);

	}

	@Override
	public void delete(Long id) {
		this.blacklistDAO.delete(id);

	}

	@Override
	public PhoneBlacklist get(Long id) {

		return this.blacklistDAO.findOne(id);
	}

	@Override
	public void update(PhoneBlacklist blacklist) {
		this.blacklistDAO.save(blacklist);

	}

	@Override
	public List<PhoneBlacklist> findAll(Page page) {
		org.springframework.data.domain.Page<PhoneBlacklist> springPhoneBlacklistPage = this.blacklistDAO
				.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springPhoneBlacklistPage.getTotalElements());
		return springPhoneBlacklistPage.getContent();
	}

}

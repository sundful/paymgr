/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, feinno.com
 * Filename:		com.feinno.security.service.impl.OrganizationServiceImpl.java
 * Class:			OrganizationServiceImpl
 * Date:			2012-8-27
 * Author:			<a href="mailto:sundful@gmail.com">sundful</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/

package com.feinno.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feinno.security.dao.OrganizationDAO;
import com.feinno.security.dao.UserDAO;
import com.feinno.security.entity.main.Organization;
import com.feinno.security.exception.ServiceException;
import com.feinno.security.service.OrganizationService;
import com.feinno.security.util.dwz.Page;
import com.feinno.security.util.dwz.PageUtils;

/**
 * 
 * @author <a href="mailto:sundful@gmail.com">sundful</a>
 * @version 1.1.0
 * @since 2012-8-27 下午3:56:46
 */
@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private OrganizationDAO organizationDao;

	@Override
	public void save(Organization organization) {
		organizationDao.save(organization);
	}

	@Override
	public Organization get(Long id) {
		return organizationDao.findOne(id);
	}

	@Override
	public void update(Organization organization) {
		organizationDao.save(organization);
	}

	/**
	 * @param id
	 * @throws ServiceException
	 * @see com.feinno.security.service.OrganizationService#delete(java.lang.Long)
	 */
	public void delete(Long id) throws ServiceException {
		if (isRoot(id)) {
			throw new ServiceException("不允许删除根组织。");
		}

		Organization organization = this.get(id);

		// 先判断是否存在子模块，如果存在子模块，则不允许删除
		if (organization.getChildren().size() > 0) {
			throw new ServiceException(organization.getName()
					+ "组织下存在子组织，不允许删除。");
		}

		if (userDAO.findByOrganizationId(id).size() > 0) {
			throw new ServiceException(organization.getName()
					+ "组织下存在用户，不允许删除。");
		}

		organization.setParent(null);
		organizationDao.delete(organization);
	}

	@Override
	public void delete(Organization organization) throws ServiceException {
		if (isRoot(organization.getId())) {
			throw new ServiceException("不允许删除根组织。");
		}
		// 先判断是否存在子模块，如果存在子模块，则不允许删除
		if (organization.getChildren().size() > 0) {
			throw new ServiceException(organization.getName()
					+ "组织下存在子组织，不允许删除。");
		}

		if (userDAO.findByOrganizationId(organization.getId()).size() > 0) {
			throw new ServiceException(organization.getName()
					+ "组织下存在用户，不允许删除。");
		}

		organization.setParent(null);
		organizationDao.delete(organization);

	}

	/**
	 * @param parentId
	 * @param page
	 * @return
	 * @see com.feinno.security.service.OrganizationService#find(java.lang.Long,
	 *      com.feinno.security.util.dwz.Page)
	 */
	public List<Organization> find(Long parentId, Page page) {
		org.springframework.data.domain.Page<Organization> springDataPage = organizationDao
				.findByParentId(parentId, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	/**
	 * @param parentId
	 * @param name
	 * @param page
	 * @return
	 * @see com.feinno.security.service.OrganizationService#find(java.lang.Long,
	 *      java.lang.String, com.feinno.security.util.dwz.Page)
	 */
	public List<Organization> find(Long parentId, String name, Page page) {
		org.springframework.data.domain.Page<Organization> springDataPage = organizationDao
				.findByParentIdAndNameContaining(parentId, name,
						PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	/**
	 * 判断是否是根组织.
	 */
	private boolean isRoot(Long id) {
		return id == 1;
	}

	/**
	 * 
	 * @return
	 * @see com.feinno.security.service.OrganizationService#getTree()
	 */
	public Organization getTree() {
		List<Organization> list = organizationDao.findAllWithCache();

		List<Organization> rootList = makeTree(list);

		return rootList.get(0);
	}

	private List<Organization> makeTree(List<Organization> list) {
		List<Organization> parent = new ArrayList<Organization>();
		// get parentId = null;
		for (Organization e : list) {
			if (e.getParent() == null) {
				e.setChildren(new ArrayList<Organization>(0));
				parent.add(e);
			}
		}
		// 删除parentId = null;
		list.removeAll(parent);

		makeChildren(parent, list);

		return parent;
	}

	private void makeChildren(List<Organization> parent,
			List<Organization> children) {
		if (children.isEmpty()) {
			return;
		}

		List<Organization> tmp = new ArrayList<Organization>();
		for (Organization c1 : parent) {
			for (Organization c2 : children) {
				c2.setChildren(new ArrayList<Organization>(0));
				if (c1.getId().equals(c2.getParent().getId())) {
					c1.getChildren().add(c2);
					tmp.add(c2);
				}
			}
		}

		children.removeAll(tmp);

		makeChildren(tmp, children);
	}

}

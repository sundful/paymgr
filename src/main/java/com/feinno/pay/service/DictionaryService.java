package com.feinno.pay.service;

import java.util.List;

import com.feinno.pay.entity.Dictionary;
import com.feinno.security.exception.ServiceException;
import com.feinno.security.util.dwz.Page;

public interface DictionaryService {

	Dictionary find(String code);
	
	List<Dictionary> findByParentId(Long parentId);

	void save(Dictionary dictionary);

	Dictionary get(Long id);

	void update(Dictionary dictionary);

	void delete(Long id) throws ServiceException;
	
	void delete(Dictionary dictionary) throws ServiceException;

	List<Dictionary> find(Long parentId, Page page);

	List<Dictionary> find(Long parentId, String name, Page page);

	Dictionary getTree();
}

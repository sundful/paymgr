package com.feinno.pay.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.beanvalidator.BeanValidators;

import com.feinno.pay.entity.Dictionary;
import com.feinno.pay.service.DictionaryService;
import com.feinno.security.exception.ServiceException;
import com.feinno.security.log.Log;
import com.feinno.security.log.LogMessageObject;
import com.feinno.security.log.impl.LogUitl;
import com.feinno.security.util.dwz.AjaxObject;
import com.feinno.security.util.dwz.Page;

@Controller
@RequestMapping("/management/paymgr/dictionary")
public class DictionaryController {

	@Autowired
	private DictionaryService dictionaryService;

	@Autowired
	private Validator validator;

	private static final String CREATE = "management/paymgr/dictionary/create";
	private static final String UPDATE = "management/paymgr/dictionary/update";
	private static final String LIST = "management/paymgr/dictionary/list";
	private static final String TREE = "management/paymgr/dictionary/tree";

	@RequiresPermissions("Dictionary:view")
	@RequestMapping(value = "/tree", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String list(Page page, String keywords, Map<String, Object> map) {
		Dictionary dictionary = dictionaryService.getTree();

		map.put("dictionary", dictionary);
		return TREE;
	}

	@RequiresPermissions("Dictionary:view")
	@RequestMapping(value = "/list/{parentDictionaryId}", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String list(Page page, @PathVariable Long parentDictionaryId,
			String keywords, Map<String, Object> map) {
		List<Dictionary> dictionaries = null;
		if (StringUtils.isNotBlank(keywords)) {
			dictionaries = dictionaryService.find(parentDictionaryId, keywords,
					page);
		} else {
			dictionaries = dictionaryService.find(parentDictionaryId, page);
		}

		map.put("page", page);
		map.put("dictionaries", dictionaries);
		map.put("keywords", keywords);
		map.put("parentDictionaryId", parentDictionaryId);

		return LIST;
	}

	@RequiresPermissions("Dictionary:save")
	@RequestMapping(value = "/create/{parentDictionaryId}", method = RequestMethod.GET)
	public String preCreate(@PathVariable Long parentDictionaryId,
			Map<String, Object> map) {
		map.put("parentDictionaryId", parentDictionaryId);
		return CREATE;
	}

	@Log(message = "添加了{0}字典。")
	@RequiresPermissions("Dictionary:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	String create(Dictionary dictionary) {
		BeanValidators.validateWithException(validator, dictionary);

		Dictionary parentDictionary = dictionaryService.get(dictionary
				.getParent().getId());
		if (parentDictionary == null) {
			return AjaxObject.newError(
					"字典添加失败：id=" + dictionary.getParent().getId() + "的父字典不存在！")
					.toString();
		}
		Dictionary dic = this.dictionaryService.find(dictionary.getCode());
		if (dic == null) {
			dictionaryService.save(dictionary);
		} else {
			return AjaxObject.newError("添加的字典与已存在的字典冲突！").toString();
		}
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
				new Object[] { dictionary.getName() }));
		return AjaxObject.newOk("添加字典成功！").toString();
	}

	@RequiresPermissions("Dictionary:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		Dictionary dictionary = dictionaryService.get(id);

		map.put("dictionary", dictionary);
		return UPDATE;
	}

	@RequiresPermissions("Dictionary:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	String update(Dictionary dictionary) {
		BeanValidators.validateWithException(validator, dictionary);
		dictionaryService.update(dictionary);

		return AjaxObject.newOk("修改字典成功！").toString();
	}

	@Log(message = "删除了{0}字典。")
	@RequiresPermissions("Dictionary:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody
	String delete(@PathVariable Long id) {
		Dictionary dictionary = this.dictionaryService.get(id);
		if (dictionary != null) {
			try {
				dictionaryService.delete(dictionary);
			} catch (ServiceException e) {
				return AjaxObject.newError("删除字典失败：" + e.getMessage())
						.setCallbackType("").toString();
			}
			LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
					new Object[] { dictionary.getName() }));
			return AjaxObject.newOk("删除字典成功！").setCallbackType("").toString();
		} else {
			return AjaxObject.newError("删除字典失败：没有该字典").toString();
		}

	}

}

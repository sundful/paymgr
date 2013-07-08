package com.feinno.pay.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.beanvalidator.BeanValidators;

import com.feinno.pay.entity.Data;
import com.feinno.pay.service.DataService;
import com.feinno.security.exception.ExistedException;
import com.feinno.security.exception.ServiceException;
import com.feinno.security.log.Log;
import com.feinno.security.log.LogMessageObject;
import com.feinno.security.log.impl.LogUitl;
import com.feinno.security.util.dwz.AjaxObject;
import com.feinno.security.util.dwz.Page;

@Controller
@RequestMapping("/management/paymgr/data")
public class DataController {

	@Autowired
	private DataService dataService;

	@Autowired
	private Validator validator;

	private static final String CREATE = "management/paymgr/data/create";
	private static final String UPDATE = "management/paymgr/data/update";
	private static final String LIST = "management/paymgr/data/list";

	@RequiresPermissions("Data:save")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate() {
		return CREATE;
	}

	@Log(message = "添加了{0}游戏。")
	@RequiresPermissions("Data:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	String create(Data data) {
		BeanValidators.validateWithException(validator, data);

		data.setCreateTime(new Date());
		try {
			this.dataService.save(data);
		} catch (ExistedException e) {
			return AjaxObject.newError(e.getMessage()).setCallbackType("")
					.toString();
		}

		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
				new Object[] { data.getName() }));
		return AjaxObject.newOk("添加游戏成功！").toString();
	}

	@ModelAttribute("preloadData")
	public Data getOne(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			Data data = this.dataService.get(id);
			return data;
		}
		return null;
	}

	@RequiresPermissions("Data:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		Data data = this.dataService.get(id);

		map.put("data", data);
		return UPDATE;
	}

	@Log(message = "修改了{0}游戏的信息。")
	@RequiresPermissions("Data:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	String update(@ModelAttribute("preloadData") Data data) {
		BeanValidators.validateWithException(validator, data);
		this.dataService.update(data);

		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
				new Object[] { data.getName() }));
		return AjaxObject.newOk("修改游戏成功！").toString();
	}

	@Log(message = "删除了{0}游戏。")
	@RequiresPermissions("Data:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody
	String delete(@PathVariable Long id) {
		Data data = null;
		try {
			data = this.dataService.get(id);
			dataService.delete(data);
		} catch (ServiceException e) {
			return AjaxObject.newError(e.getMessage()).setCallbackType("")
					.toString();
		}

		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
				new Object[] { data.getName() }));
		return AjaxObject.newOk("删除游戏成功！").setCallbackType("").toString();
	}

	@Log(message = "删除了{0}游戏。")
	@RequiresPermissions("Data:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	String deleteMany(Long[] ids) {
		String[] dataNames = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				Data data = this.dataService.get(ids[i]);
				dataService.delete(data);

				dataNames[i] = data.getName();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError(e.getMessage()).setCallbackType("")
					.toString();
		}

		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
				new Object[] { Arrays.toString(dataNames) }));
		return AjaxObject.newOk("删除游戏成功！").setCallbackType("").toString();
	}

	@RequiresPermissions("Data:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String list(Page page, String keywords, Map<String, Object> map) {
		List<Data> datas = null;
		if (StringUtils.isNotBlank(keywords)) {
			datas = dataService.find(page, keywords);
		} else {
			datas = dataService.findAll(page);
		}

		map.put("page", page);
		map.put("datas", datas);
		map.put("keywords", keywords);
		return LIST;
	}

}

/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, feinno.com
 * Filename:		com.feinno.security.controller.ModuleController.java
 * Class:			ModuleController
 * Date:			2012-8-6
 * Author:			<a href="mailto:sundful@gmail.com">sundful</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.feinno.security.controller;

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

import com.feinno.security.entity.main.Module;
import com.feinno.security.entity.main.Permission;
import com.feinno.security.exception.ExistedException;
import com.feinno.security.exception.ServiceException;
import com.feinno.security.log.Log;
import com.feinno.security.log.LogMessageObject;
import com.feinno.security.log.impl.LogUitl;
import com.feinno.security.service.ModuleService;
import com.feinno.security.util.dwz.AjaxObject;
import com.feinno.security.util.dwz.Page;
import com.google.common.collect.Lists;

/** 
 * 	
 * @author 	<a href="mailto:sundful@gmail.com">sundful</a>
 * @version  1.1.0
 * @since   2012-8-6 上午10:08:48 
 */
@Controller
@RequestMapping("/management/security/module")
public class ModuleController {
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private Validator validator;
	
	private static final String CREATE = "management/security/module/create";
	private static final String UPDATE = "management/security/module/update";
	private static final String LIST = "management/security/module/list";
	private static final String TREE = "management/security/module/tree";
	private static final String VIEW = "management/security/module/view";
	private static final String TREE_LIST = "management/security/module/tree_list";
	
	@RequiresPermissions("Module:save")
	@RequestMapping(value="/create/{parentModuleId}", method=RequestMethod.GET)
	public String preCreate(Map<String, Object> map, @PathVariable Long parentModuleId) {
		map.put("parentModuleId", parentModuleId);
		return CREATE;
	}
	
	@Log(message="添加了{0}模块。")
	@RequiresPermissions("Module:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(Module module) {
		BeanValidators.validateWithException(validator, module);
		
		Module parentModule = moduleService.get(module.getParent().getId());
		if (parentModule == null) {
			return AjaxObject.newError("添加模块失败：id=" + module.getParent().getId() + "的父模块不存在！").toString();
		}
		
		List<Permission> permissions = Lists.newArrayList();
		for (Permission permission : module.getPermissions()) {
			if (StringUtils.isNotBlank(permission.getShortName())) {
				permissions.add(permission);
			}
		}
		
		for (Permission permission : permissions) {
			permission.setModule(module);
		}
		module.setPermissions(permissions);
		
		try {
			moduleService.save(module);
		} catch (ExistedException e) {
			return AjaxObject.newError("添加模块失败：" + e.getMessage()).toString();
		}
		
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{module.getName()}));
		return AjaxObject.newOk("添加模块成功！").toString();
	}
	
	@RequiresPermissions("Module:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		Module module = moduleService.get(id);
		
		map.put("module", module);
		return UPDATE;
	}
	
	@Log(message="修改了{0}模块的信息。")
	@RequiresPermissions("Module:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(Module module) {
		BeanValidators.validateWithException(validator, module);
		
		Module oldModule = moduleService.get(module.getId());
		oldModule.setName(module.getName());
		oldModule.setPriority(module.getPriority());
		oldModule.setDescription(module.getDescription());
		oldModule.setSn(module.getSn());
		oldModule.setUrl(module.getUrl());
		
		for (Permission permission : module.getPermissions()) {
			if (StringUtils.isNotBlank(permission.getShortName())) {
				if (permission.getId() == null) {
					permission.setModule(oldModule);
					oldModule.getPermissions().add(permission);
				}
			} else {
				if (permission.getId() != null) {
					for (Permission oldPermission : oldModule.getPermissions()) {
						if (oldPermission.getId().equals(permission.getId())) {
							oldPermission.setModule(null);
							permission = oldPermission;
							break;
						}
					}
					oldModule.getPermissions().remove(permission);
				}
			}
		}
		
		moduleService.update(oldModule);
		
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{oldModule.getName()}));
		return AjaxObject.newOk("修改模块成功！").toString();
	}
	
	@Log(message="删除了{0}模块。")
	@RequiresPermissions("Module:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		Module module = moduleService.get(id);
		try {
			moduleService.delete(id);
		} catch (ServiceException e) {
			return AjaxObject.newError("删除模块失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{module.getName()}));
		return AjaxObject.newOk("删除模块成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("Module:view")
	@RequestMapping(value="/tree_list", method={RequestMethod.GET, RequestMethod.POST})
	public String tree_list(Map<String, Object> map) {
		return TREE_LIST;
	}
	
	@RequiresPermissions("Module:view")
	@RequestMapping(value="/tree", method=RequestMethod.GET)
	public String tree(Map<String, Object> map) {
		Module module = moduleService.getTree();
		
		map.put("module", module);
		return TREE;
	}
	
	@RequiresPermissions("Module:view")
	@RequestMapping(value="/list/{parentModuleId}", method={RequestMethod.GET, RequestMethod.POST})
	public String list(Page page, @PathVariable Long parentModuleId, String keywords, 
			Map<String, Object> map) {
		List<Module> modules = null;
		if (StringUtils.isNotBlank(keywords)) {
			modules = moduleService.find(parentModuleId, keywords, page);
		} else {
			modules = moduleService.find(parentModuleId, page);
		}
		
		map.put("page", page);
		map.put("modules", modules);
		map.put("keywords", keywords);
		map.put("parentModuleId", parentModuleId);

		return LIST;
	}
	
	@RequiresPermissions("Module:view")
	@RequestMapping(value="/view/{id}", method={RequestMethod.GET})
	public String view(@PathVariable Long id, Map<String, Object> map) {
		Module module = moduleService.get(id);
		
		map.put("module", module);
		return VIEW;
	}
}

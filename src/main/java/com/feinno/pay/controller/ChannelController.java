package com.feinno.pay.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
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

import com.feinno.pay.entity.Channel;
import com.feinno.pay.entity.Data;
import com.feinno.pay.entity.Dictionary;
import com.feinno.pay.service.ChannelService;
import com.feinno.pay.service.DataService;
import com.feinno.pay.service.DictionaryService;
import com.feinno.security.exception.ExistedException;
import com.feinno.security.exception.ServiceException;
import com.feinno.security.log.Log;
import com.feinno.security.log.LogMessageObject;
import com.feinno.security.log.impl.LogUitl;
import com.feinno.security.util.dwz.AjaxObject;
import com.feinno.security.util.dwz.MD5Util;
import com.feinno.security.util.dwz.Page;

@Controller
@RequestMapping("/management/paymgr/channel")
public class ChannelController {

	@Autowired
	private ChannelService channelService;

	@Autowired
	private DataService dataService;

	@Autowired
	private DictionaryService dictionaryService;

	@Autowired
	private Validator validator;

	private static final String CREATE = "management/paymgr/channel/create";
	private static final String UPDATE = "management/paymgr/channel/update";
	private static final String LIST = "management/paymgr/channel/list";
	private static final String LOOK_DATA = "management/paymgr/channel/lookup_data";

	@RequiresPermissions("Channel:save")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate(Map<String, Object> map) {

		Dictionary dictionary = this.dictionaryService.find("JSBL");
		if (dictionary != null) {
			List<Dictionary> dictionaries = this.dictionaryService
					.findByParentId(dictionary.getId());
			map.put("dics", dictionaries);
		}
		return CREATE;
	}

	@Log(message = "添加了{0}渠道。")
	@RequiresPermissions("Channel:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	String create(Channel channel) {
		BeanValidators.validateWithException(validator, channel);
		
		channel.setRequestKey(MD5Util.md5Hex(channel.getName()));
		channel.setCreateTime(new Date());
		try {
			this.channelService.save(channel);
		} catch (ExistedException e) {
			return AjaxObject.newError(e.getMessage()).setCallbackType("")
					.toString();
		}

		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
				new Object[] { channel.getName() }));
		return AjaxObject.newOk("添加渠道成功！").toString();
	}

	@ModelAttribute("preloadChannel")
	public Channel getOne(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			Channel channel = this.channelService.get(id);
			channel.setData(null);
			return channel;
		}
		return null;
	}

	@RequiresPermissions("Channel:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		
		Channel channel = channelService.get(id);
		map.put("channel", channel);
		Dictionary dictionary = this.dictionaryService.find("JSBL");
		if (dictionary != null) {
			List<Dictionary> dictionaries = this.dictionaryService
					.findByParentId(dictionary.getId());
			map.put("dics", dictionaries);
		}
		
		return UPDATE;
	}

	@Log(message = "修改了{0}渠道的信息。")
	@RequiresPermissions("Channel:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	String update(@ModelAttribute("preloadChannel") Channel channel) {
		BeanValidators.validateWithException(validator, channel);
		channelService.update(channel);

		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
				new Object[] { channel.getName() }));
		return AjaxObject.newOk("修改渠道成功！").toString();
	}

	@Log(message = "删除了{0}渠道。")
	@RequiresPermissions("Channel:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody
	String delete(@PathVariable Long id) {
		Channel channel = null;
		try {
			channel = this.channelService.get(id);
			channelService.delete(channel.getId());
		} catch (ServiceException e) {
			return AjaxObject.newError(e.getMessage()).setCallbackType("")
					.toString();
		}

		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
				new Object[] { channel.getName() }));
		return AjaxObject.newOk("删除渠道成功！").setCallbackType("").toString();
	}

	@Log(message = "删除了{0}渠道。")
	@RequiresPermissions("Channel:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	String deleteMany(Long[] ids) {
		String[] channelNames = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				Channel channel = channelService.get(ids[i]);
				channelService.delete(channel.getId());

				channelNames[i] = channel.getName();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError(e.getMessage()).setCallbackType("")
					.toString();
		}

		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
				new Object[] { Arrays.toString(channelNames) }));
		return AjaxObject.newOk("删除渠道成功！").setCallbackType("").toString();
	}

	@RequiresPermissions("Channel:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String list(Page page, String keywords, Map<String, Object> map) {
		List<Channel> channels = null;
		if (StringUtils.isNotBlank(keywords)) {
			channels = channelService.find(page, keywords);
		} else {
			channels = channelService.findAll(page);
		}

		map.put("page", page);
		map.put("channels", channels);
		map.put("keywords", keywords);
		return LIST;
	}

	@Log(message = "{0}渠道{1}")
	@RequiresPermissions("Channel:reset")
	@RequestMapping(value = "/reset/{type}/{channelId}", method = RequestMethod.POST)
	public @ResponseBody
	String reset(@PathVariable String type, @PathVariable Long channelId) {
		Channel channel = this.channelService.get(channelId);
		AjaxObject ajaxObject = new AjaxObject();
		ajaxObject.setCallbackType("");

		if (type.equals("status")) {
			if (channel.getEnable()) {
				channel.setEnable(false);
			} else {
				channel.setEnable(true);
			}

			ajaxObject.setMessage("更新状态成功，当前为"
					+ (channel.getEnable() ? "激活" : "冻结"));
		}

		this.channelService.update(channel);

		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
				new Object[] { channel.getName(), ajaxObject.getMessage() }));
		return ajaxObject.toString();
	}

	@RequiresPermissions(value = { "Channel:edit", "Channel:save" }, logical = Logical.OR)
	@RequestMapping(value = "/lookup2data", method = { RequestMethod.GET })
	public String lookup(Map<String, Object> map) {
		List<Data> datas = this.dataService.findAll();

		map.put("datas", datas);
		return LOOK_DATA;
	}

}

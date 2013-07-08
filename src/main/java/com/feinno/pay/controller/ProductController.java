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

import com.feinno.pay.entity.Data;
import com.feinno.pay.entity.Product;
import com.feinno.pay.service.DataService;
import com.feinno.pay.service.ProductService;
import com.feinno.security.exception.ExistedException;
import com.feinno.security.exception.ServiceException;
import com.feinno.security.log.Log;
import com.feinno.security.log.LogMessageObject;
import com.feinno.security.log.impl.LogUitl;
import com.feinno.security.util.dwz.AjaxObject;
import com.feinno.security.util.dwz.Page;

@Controller
@RequestMapping("/management/paymgr/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private DataService dataService;

	@Autowired
	private Validator validator;

	private static final String CREATE = "management/paymgr/product/create";
	private static final String UPDATE = "management/paymgr/product/update";
	private static final String LIST = "management/paymgr/product/list";
	private static final String LOOK_DATA = "management/paymgr/product/lookup_data";

	@RequiresPermissions("Product:save")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate() {
		return CREATE;
	}

	@Log(message = "添加了{0}计费代码。")
	@RequiresPermissions("Product:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	String create(Product product) {
		BeanValidators.validateWithException(validator, product);

		product.setCreateTime(new Date());
		try {
			this.productService.save(product);
		} catch (ExistedException e) {
			return AjaxObject.newError(e.getMessage()).setCallbackType("")
					.toString();
		}

		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
				new Object[] { product.getCode() }));
		return AjaxObject.newOk("添加计费代码成功！").toString();
	}

	@ModelAttribute("preloadProduct")
	public Product getOne(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			Product product = this.productService.get(id);
			product.setData(null);
			return product;
		}
		return null;
	}

	@RequiresPermissions("Product:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		Product product = productService.get(id);

		map.put("product", product);
		return UPDATE;
	}

	@Log(message = "修改了{0}计费代码的信息。")
	@RequiresPermissions("Product:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	String update(@ModelAttribute("preloadProduct") Product product) {
		BeanValidators.validateWithException(validator, product);
		productService.update(product);

		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
				new Object[] { product.getCode() }));
		return AjaxObject.newOk("修改计费代码成功！").toString();
	}

	@Log(message = "删除了{0}计费代码。")
	@RequiresPermissions("Product:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody
	String delete(@PathVariable Long id) {
		Product product = null;
		try {
			product = this.productService.get(id);
			productService.delete(product.getId());
		} catch (ServiceException e) {
			return AjaxObject.newError(e.getMessage()).setCallbackType("")
					.toString();
		}

		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
				new Object[] { product.getCode() }));
		return AjaxObject.newOk("删除计费代码成功！").setCallbackType("").toString();
	}

	@Log(message = "删除了{0}计费代码。")
	@RequiresPermissions("Product:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	String deleteMany(Long[] ids) {
		String[] productCodes = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				Product product = productService.get(ids[i]);
				productService.delete(product.getId());

				productCodes[i] = product.getCode();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError(e.getMessage()).setCallbackType("")
					.toString();
		}

		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
				new Object[] { Arrays.toString(productCodes) }));
		return AjaxObject.newOk("删除计费代码成功！").setCallbackType("").toString();
	}

	@RequiresPermissions("Product:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String list(Page page, String keywords, Map<String, Object> map) {
		List<Product> products = null;
		if (StringUtils.isNotBlank(keywords)) {
			products = productService.find(page, keywords);
		} else {
			products = productService.findAll(page);
		}

		map.put("page", page);
		map.put("products", products);
		map.put("keywords", keywords);
		return LIST;
	}

	@RequiresPermissions(value = { "Product:edit", "Product:save" }, logical = Logical.OR)
	@RequestMapping(value = "/lookup2data", method = { RequestMethod.GET })
	public String lookup(Map<String, Object> map) {
		List<Data> datas = this.dataService.findAll();

		map.put("datas", datas);
		return LOOK_DATA;
	}

}

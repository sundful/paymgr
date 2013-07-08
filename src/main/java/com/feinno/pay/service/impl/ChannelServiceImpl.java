package com.feinno.pay.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.feinno.pay.dao.ChannelDAO;
import com.feinno.pay.entity.Channel;
import com.feinno.pay.entity.Data;
import com.feinno.pay.entity.Product;
import com.feinno.pay.service.ChannelService;
import com.feinno.security.exception.ExistedException;
import com.feinno.security.exception.ServiceException;
import com.feinno.security.util.dwz.EmagUtil;
import com.feinno.security.util.dwz.HttpClientUtil;
import com.feinno.security.util.dwz.JsonUtil;
import com.feinno.security.util.dwz.MD5Util;
import com.feinno.security.util.dwz.Page;
import com.feinno.security.util.dwz.PageUtils;
import com.feinno.security.util.dwz.StringUtil;

@Component
@Transactional
public class ChannelServiceImpl implements ChannelService {
	protected static final Logger logger = LoggerFactory
			.getLogger(ChannelServiceImpl.class);

	@Autowired
	private ChannelDAO channelDAO;

	@Override
	public void save(Channel channel) throws ExistedException {
		String code = channel.getCode();
		if (this.channelDAO.findByCode(code) != null) {
			throw new ExistedException("渠道添加失败，渠道代码：" + code + "已存在。");
		}
		this.channelDAO.save(channel);
	}

	@Override
	public void delete(Long id) throws ServiceException {
		this.channelDAO.delete(id);

	}

	@Override
	public Channel get(Long id) {

		return this.channelDAO.findOne(id);
	}

	@Override
	public void update(Channel channel) {
		this.channelDAO.save(channel);

	}

	@Override
	public List<Channel> findAll() {
		return this.channelDAO.findAll();
	}

	@Override
	public List<Channel> findAll(Page page) {
		org.springframework.data.domain.Page<Channel> springDataPage = this.channelDAO
				.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	/**
	 * 根据传入的条件查询
	 * 
	 * @param page
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public List<Channel> find(Page page, String keywords) {

		page.setOrderField("id");
		page.setOrderDirection(Page.ORDER_DIRECTION_DESC);
		org.springframework.data.domain.Page<Channel> springDataPage = (org.springframework.data.domain.Page<Channel>) channelDAO
				.findAll(buildChannelSpecification(keywords),
						PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	private Specification<Channel> buildChannelSpecification(
			final String keywords) {

		return new Specification<Channel>() {

			@Override
			public Predicate toPredicate(Root<Channel> root,
					CriteriaQuery<?> q, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (StringUtils.hasText(keywords)) {
					Path<String> np = root.get("name");
					predicates.add(cb.like(np, "%" + keywords + "%"));
					Path<String> np1 = root.get("code");
					predicates.add(cb.like(np1, "%" + keywords + "%"));
				}
				if (predicates.size() > 0) {
					return cb.or(predicates.toArray(new Predicate[predicates
							.size()]));
				}
				return cb.conjunction();
			}
		};
	}

	@Override
	public Channel findChannel(String code) {
		return this.channelDAO.findByCode(code);
	}

	@Override
	public boolean checkSignLegal(Channel channel, String sign) {
		String joinStr = StringUtil.joinString(SEPARATOR, channel.getCode(),
				channel.getRequestKey());
		String md5Sign = MD5Util.md5Hex(joinStr);
		if (md5Sign.equals(sign)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkMoneylLegal(Channel channel, String money) {
		if (StringUtil.isEmpty(money) || StringUtil.isEmpty(channel)) {
			return false;
		}
		List<Product> products = channel.getData().getProduct();
		Integer num = Integer.parseInt(money);
		for (Product product : products) {
			if (num == product.getMoney()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getSessionKey(Channel channel) {
		if (StringUtil.isEmpty(channel)) {
			return null;
		}

		StringBuilder url = new StringBuilder(EmagUtil.EMAG_URL).append("?");
		String method = EmagUtil.METHOD_GETSESSIONKEY;
		String key = EmagUtil.EMAG_KEY;
		String app = channel.getData().getCode();
		String time = String.valueOf(new Date().getTime() / 1000);
		// 构造签名
		StringBuilder hashStr = new StringBuilder("app=").append(app)
				.append("&method=").append(method).append("&time=")
				.append(time).append("&key=").append(key);
		String hash = MD5Util.md5Hex(hashStr.toString());

		url.append("app=").append(app).append("&method=").append(method)
				.append("&time=").append(time).append("&hash=").append(hash)
				.append("&format=json");

		if (logger.isDebugEnabled()) {
			logger.debug("[sessionkey url]=====> \n" + url.toString());
		}

		return JsonUtil.parseJsonBackMsg(
				HttpClientUtil.sendGetRequest(url.toString(), null),
				EmagUtil.ELEMENT_SESSIONKEY);
	}

	@Override
	public String getOrderId(Channel channel, String sessionKey, String phone,
			String money) {
		if (StringUtil.isEmpty(channel, sessionKey, phone, money)) {
			return null;
		}
		Data data = channel.getData();

		Product product = null;
		for (Product p : data.getProduct()) {
			if (p.getMoney().intValue() == Integer.parseInt(money)) {
				product = p;
			}
		}
		if (StringUtil.isEmpty(data, product)) {
			return null;
		}

		StringBuilder url = new StringBuilder(EmagUtil.EMAG_URL).append("?");
		String method = EmagUtil.METHOD_APPLYFORPURCHASE;
		String key = EmagUtil.EMAG_KEY;
		String app = data.getCode();
		String consumeCode = product.getCode();
		String saleChannelId = data.getChannelId();
		String time = String.valueOf(new Date().getTime() / 1000);
		// 构造签名
		StringBuilder hashStr = new StringBuilder("app=").append(app)
				.append("&method=").append(method).append("&tel=")
				.append(phone).append("&consumecode=").append(consumeCode)
				.append("&time=").append(time).append("&sessionkey=")
				.append(sessionKey).append("&key=").append(key);
		String hash = MD5Util.md5Hex(hashStr.toString());

		url.append("app=").append(app).append("&method=").append(method)
				.append("&tel=").append(phone).append("&consumecode=")
				.append(consumeCode).append("&salechannelid=")
				.append(saleChannelId).append("&time=").append(time)
				.append("&sessionkey=").append(sessionKey).append("&hash=")
				.append(hash).append("&format=json");

		if (logger.isDebugEnabled()) {
			logger.debug("[orderid url]=====> \n" + url.toString());
		}

		return JsonUtil.parseJsonBackMsg(
				HttpClientUtil.sendGetRequest(url.toString(), null),
				EmagUtil.ELEMENT_ORDERID);
	}

	@Override
	public String getResultCode(Channel channel, String sessionKey,
			String verifyCode, String orderId) {
		if (StringUtil.isEmpty(channel, sessionKey, verifyCode, orderId)) {
			return null;
		}

		Data data = channel.getData();
		if (StringUtil.isEmpty(data)) {
			return null;
		}

		StringBuilder url = new StringBuilder(EmagUtil.EMAG_URL).append("?");
		String method = EmagUtil.METHOD_CONFIRMPURCHASE;
		String key = EmagUtil.EMAG_KEY;
		String app = data.getCode();
		String time = String.valueOf(new Date().getTime() / 1000);
		// 构造签名
		StringBuilder hashStr = new StringBuilder("app=").append(app)
				.append("&method=").append(method).append("&verifycode=")
				.append(verifyCode).append("&orderid=").append(orderId)
				.append("&time=").append(time).append("&sessionkey=")
				.append(sessionKey).append("&key=").append(key);
		String hash = MD5Util.md5Hex(hashStr.toString());

		url.append("app=").append(app).append("&method=").append(method)
				.append("&verifycode=").append(verifyCode).append("&orderid=")
				.append(orderId).append("&time=").append(time)
				.append("&sessionkey=").append(sessionKey).append("&hash=")
				.append(hash).append("&format=json");

		if (logger.isDebugEnabled()) {
			logger.debug("[resultcode url]=====> \n" + url.toString());
		}

		return JsonUtil.parseJsonBackMsg(
				HttpClientUtil.sendGetRequest(url.toString(), null),
				EmagUtil.ELEMENT_RESULTCODE);
	}

}

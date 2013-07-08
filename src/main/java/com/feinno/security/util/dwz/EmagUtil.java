/**
 * create on 2011-12-12
 */
package com.feinno.security.util.dwz;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author 孙维维(sundful)
 * 
 */
public class EmagUtil {
	private static final Log logger = LogFactory.getLog(EmagUtil.class);

	// 幻方提供的数据
	public static final String INVALID_IP_MSG = "9000"; // ip不合法
	public static final String REGION_CLOSE_MSG = "9001"; // 该地区屏蔽
	public static final String PROVINCE_CLOSE_MSG = "9002"; // 该省份屏蔽
	public static final String BLACK_PHONE_MSG = "9003"; // 黑名单号码
	public static final String INVALID_OID_MSG = "9005"; // 无效订单号
	public static final String CLOSE_CHANNEL_MSG = "9008"; // 渠道冻结
	public static final String MONEY_ERROR_MSG = "9009"; // 支付金额错误
	public static final String CODE_TIMEOUT_MSG = "8010"; // 验证码超时
	public static final String CODE_ERROR_MSG = "8011"; // 验证码错误
	public static final String UPPER_LIMIT_MSG = "8012"; // 达到充值上限
	public static final String BANLANCE_LACK_MSG = "1182"; // 手机欠费
	public static final String ALREADY_DEAL_MSG = "1183"; // 已经处理
	public static final Integer MAX_MONEY = 100; // 充值上限100元

	public static final String METHOD_GETSESSIONKEY = "getsessionkey"; // 得到sessionkey方法
	public static final String METHOD_APPLYFORPURCHASE = "applyforpurchase"; // 计费请求
	public static final String METHOD_CONFIRMPURCHASE = "confirmpurchase"; // 确认扣费请求
	public static final String ELEMENT_SESSIONKEY = "sessionkey"; // sessionkey
	public static final String ELEMENT_RESULTCODE = "resultCode"; // resultCode
	public static final String ELEMENT_ORDERID = "orderid"; // orderid

	/** 配置文件 */
	private static final String CONFIG_FILE_NAME = "emag.properties";
	private static final PropertiesConfiguration config = new PropertiesConfiguration();
	static {
		/**
		 * 装载配置文件
		 */
		config.setEncoding("UTF-8");
		try {
			config.load(CONFIG_FILE_NAME);
		} catch (ConfigurationException e) {
			logger.error("init config error:", e);
		}
	}
	// 请求幻方的链接
	public static final String EMAG_URL = config.getString("emag.url");
	// 取得签名的密钥
	public static final String EMAG_KEY = config.getString("emag.key");

	public static enum StatusCode {
		PARAM_ERROR("9001", "参数异常"), CHANNEL_ERROR("9002", "商户信息不合法"), MONEY_ERROR(
				"9003", "支付金额不合法"), SIGN_ERROR("9004", "签名错误"), ORDER_ERROR(
				"9005", "无效订单号"), INTERNAL_ERROR("500", "内部错误"), SUCCESS_INFO(
				"200000", "成功"), PENDING_INFO("100000", "短信下发未确认");

		private final String code;
		private final String msg;

		private StatusCode(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public String getCode() {
			return code;
		}

		public String getMsg() {
			return msg;
		}
	}
}

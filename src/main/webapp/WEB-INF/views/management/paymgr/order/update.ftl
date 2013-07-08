<@dwz.layout_content>
<@dwz.form action="${request.contextPath}/management/paymgr/order/update" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${order.id}"/>
	<input type="hidden" name="createTime" value="${order.createTime}"/>
	<input type="hidden" name="notifyTime" value="${order.notifyTime}"/>
	<@dwz.layout_form_content layoutH="58">
		<@dwz.label_input content="订单号:">
			<input type="text" name="orderId" class="required" size="20" maxlength="32" readonly="readonly" value="${order.orderId}"/>
		</@dwz.label_input>
		<@dwz.label_input content="渠道代码:">
			<input type="text" name="channel" class="required" size="20" maxlength="32" readonly="readonly" value="${order.channel!''}"/>
		</@dwz.label_input>	
		<@dwz.label_input content="电话号码:">
			<input type="text" name="phone" class="required" size="20" maxlength="32" readonly="readonly" value="${order.phone!''}"/>
		</@dwz.label_input>	
		<@dwz.label_input content="订单金额:">
			<input type="text" name="money" class="required" size="20" maxlength="32" readonly="readonly" value="${order.money!''}"/>
		</@dwz.label_input>	
		<@dwz.label_input content="订单状态:">
			<input type="text" name="status" class="required" size="20" maxlength="32" value="${order.status!''}"/>
		</@dwz.label_input>	
		
	</@dwz.layout_form_content>
			
	<@dwz.form_bar submitTitle="确定" closeTitle="关闭"/>	
</@dwz.form>
</@dwz.layout_content>
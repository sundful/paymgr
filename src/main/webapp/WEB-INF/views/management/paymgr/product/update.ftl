<@dwz.layout_content>
<@dwz.form action="${request.contextPath}/management/paymgr/product/update" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${product.id}"/>
	<@dwz.layout_form_content layoutH="58">
		<@dwz.label_input content="计费代码:">
			<input type="text" name="code" class="required" size="20" maxlength="32" readonly="readonly" value="${product.code}"/>
		</@dwz.label_input>	
		<@dwz.label_input content="金额:">
			<input type="text" name="money" class="digits required" size="20" maxlength="32" value="${product.money}"/>
		</@dwz.label_input>
		<@dwz.label_input content="关联游戏:">
			<input name="data.id" value="${product.data.id }" type="hidden"/>
			<input class="required" name="data.name" type="text" readonly="readonly" value="${product.data.name }"/>
			<a class="btnLook" href="${request.contextPath}/management/paymgr/product/lookup2data" lookupGroup="data" title="关联游戏" width="400">查找带回</a>	
		</@dwz.label_input>					
		<@dwz.label_input content="描述：">
			<textarea name="description" cols="28" rows="3" maxlength="255" value="${product.description}"></textarea>
		</@dwz.label_input>						
	</@dwz.layout_form_content>
			
	<@dwz.form_bar submitTitle="确定" closeTitle="关闭"/>	
</@dwz.form>
</@dwz.layout_content>
<@dwz.layout_content>
<@dwz.form action="${request.contextPath}/management/paymgr/product/create" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<@dwz.layout_form_content layoutH="58">
		<@dwz.label_input content="计费代码:">
			<input type="text" name="code" class="required" size="20" maxlength="32" alt="幻方分配"/>
		</@dwz.label_input>	
		<@dwz.label_input content="金额:">
			<input type="text" name="money" class="digits required" size="20" maxlength="32" alt="请输入数字"/>
		</@dwz.label_input>
		<@dwz.label_input content="关联游戏:">
			<input name="data.id" value="" type="hidden"/>
			<input class="required" name="data.name" type="text" readonly="readonly"/>
			<a class="btnLook" href="${request.contextPath}/management/paymgr/product/lookup2data" lookupGroup="data" title="关联游戏" width="400">查找带回</a>
		</@dwz.label_input>			
		<@dwz.label_input content="描述：">
			<textarea name="description" cols="28" rows="3" maxlength="255"></textarea>
		</@dwz.label_input>	
	</@dwz.layout_form_content>
			
	<@dwz.form_bar submitTitle="确定" closeTitle="关闭"/>	
</@dwz.form>
</@dwz.layout_content>
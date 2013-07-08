<@dwz.layout_content>
<@dwz.form action="${request.contextPath}/management/paymgr/channel/create" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<@dwz.layout_form_content layoutH="58">
		<@dwz.label_input content="渠道代码:">
			<input type="text" name="code" class="required" size="20" maxlength="32" alt="4位数字组合"/>
		</@dwz.label_input>	
		<@dwz.label_input content="渠道名称:">
			<input type="text" name="name" class="required" size="20" maxlength="32"/>
		</@dwz.label_input>					
		<@dwz.label_input content="渠道状态:">
			<select name="enable">
				<option value="1">激活</option>
				<option value="0">冻结</option>
			</select>
		</@dwz.label_input>
		<@dwz.label_input content="结算比例:">
			<select name="percent">
			 <#list dics as item>
		 		<option value="${item.code}">${item.name}%</option>
			 </#list>
			</select>
		</@dwz.label_input>						
		<@dwz.label_input content="关联游戏:">
			<input name="data.id" value="" type="hidden"/>
			<input class="required" name="data.name" type="text" readonly="readonly"/>
			<a class="btnLook" href="${request.contextPath}/management/paymgr/channel/lookup2data" lookupGroup="data" title="关联游戏" width="400">查找带回</a>
		</@dwz.label_input>
	</@dwz.layout_form_content>
			
	<@dwz.form_bar submitTitle="确定" closeTitle="关闭"/>	
</@dwz.form>
</@dwz.layout_content>
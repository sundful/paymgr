<@dwz.layout_content>
<@dwz.form action="${request.contextPath}/management/paymgr/channel/update" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${channel.id}"/>
	<@dwz.layout_form_content layoutH="58">
		<@dwz.label_input content="渠道代码:">
			<input type="text" name="code" class="required" size="20" maxlength="32" readonly="readonly" value="${channel.code}"/>
		</@dwz.label_input>	
		<@dwz.label_input content="渠道名称:">
			<input type="text" name="name" class="required" size="20" maxlength="32" value="${channel.name}"/>
		</@dwz.label_input>							
		<@dwz.label_input content="签名KEY:">
			<input type="text" name="requestKey" class="required" size="20" maxlength="32" value="${channel.requestKey }"/>
		</@dwz.label_input>	
		<@dwz.label_input content="渠道状态:">
			<select name="enable">
				<option value="1" ${(channel.enable)?string('selected="selected"','')}>激活</option>
				<option value="0" ${(!channel.enable)?string('selected="selected"','')}>冻结</option>
			</select>
		</@dwz.label_input>	
		<@dwz.label_input content="结算比例:">
			<select name="percent">
			 <#list dics as item>
		 		<#if item.code==(channel.percent)?string>
		 		<option value="${item.code}" selected="selected">${item.name}%</option>
		 		<#else>
		 		<option value="${item.code}">${item.name}%</option>
		 		</#if>
			 </#list>
			</select>
		</@dwz.label_input>						
		<@dwz.label_input content="关联游戏:">
			<input name="data.id" value="${channel.data.id }" type="hidden"/>
			<input class="required" name="data.name" type="text" readonly="readonly" value="${channel.data.name }"/>
			<a class="btnLook" href="${request.contextPath}/management/paymgr/channel/lookup2data" lookupGroup="data" title="关联游戏" width="400">查找带回</a>	
		</@dwz.label_input>
	</@dwz.layout_form_content>
			
	<@dwz.form_bar submitTitle="确定" closeTitle="关闭"/>	
</@dwz.form>
</@dwz.layout_content>
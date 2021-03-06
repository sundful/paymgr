<@dwz.layout_content>
<@dwz.form action="${request.contextPath}/management/paymgr/data/update" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${data.id}"/>
	<@dwz.layout_form_content layoutH="58">
		<@dwz.label_input content="游戏代码:">
			<input type="text" name="code" class="required" size="20" maxlength="32" readonly="readonly" value="${data.code}"/>
		</@dwz.label_input>	
		<@dwz.label_input content="游戏名称:">
			<input type="text" name="name" class="required" size="20" maxlength="32" value="${data.name}"/>
		</@dwz.label_input>							
		<@dwz.label_input content="渠道代码:">
			<input type="text" name="cpId" class="required" size="20" maxlength="32" value="${data.cpId}"/>
		</@dwz.label_input>
		<@dwz.label_input content="业务代码:">
			<input type="text" name="cpServiceId" class="required" size="20" maxlength="32" value="${data.cpServiceId}"/>
		</@dwz.label_input>
		<@dwz.label_input content="内容代码:">
			<input type="text" name="contentId" class="required" size="20" maxlength="32" value="${data.contentId}"/>
		</@dwz.label_input>
		<@dwz.label_input content="计费代码:">
			<input type="text" name="consumeCode" class="required" size="20" maxlength="32"  value="${data.consumeCode}"/>
		</@dwz.label_input>
		<@dwz.label_input content="结算渠道:">
			<input type="text" name="channelId" class="required" size="20" maxlength="32" value="${data.channelId}"/>
		</@dwz.label_input>
	</@dwz.layout_form_content>
			
	<@dwz.form_bar submitTitle="确定" closeTitle="关闭"/>	
</@dwz.form>
</@dwz.layout_content>
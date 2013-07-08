<@dwz.pagerForm action="${request.contextPath}/management/paymgr/data/list" page=page>
	<input type="hidden" name="keywords" value="${keywords!''}"/>
</@dwz.pagerForm>

<@dwz.search_form action="${request.contextPath}/management/paymgr/data/list">
	<@dwz.label_input2 content="代码/名称：">
		<input type="text" name="keywords" value="${keywords!''}"/>
	</@dwz.label_input2>
</@dwz.search_form>

<@dwz.layout_content>
	<@dwz.tool_bar>
		<@shiro.hasPermission name="Data:save">
			<@dwz.tool_button content="添加游戏" class="user_add" rel="lookup2organization_add" width="530" height="330" href="${request.contextPath}/management/paymgr/data/create"/>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="Data:edit">
			<@dwz.tool_button content="编辑游戏" class="user_edit" rel="lookup2organization_edit" width="530" height="330" href="${request.contextPath}/management/paymgr/data/update/{slt_uid}"/>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="Data:delete">
			<@dwz.tool_button content="删除游戏" class="user_delete" target="selectedTodo" rel="ids" href="${request.contextPath}/management/paymgr/data/delete" title="确认要删除?"/>
		</@shiro.hasPermission>
	</@dwz.tool_bar>
	
	<@dwz.table_common layoutH="138">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th width="100">游戏代码</th>
				<th width="100">游戏名称</th>
				<th width="100">渠道代码</th>
				<th width="100">业务代码</th>
				<th width="100">内容代码</th>
				<th width="100">计费代码</th>
				<th width="100">结算渠道</th>
				<th width="100">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<#list datas as item>
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.code!''}</td>
				<td>${item.name!''}</td>
				<td>${item.cpId!''}</td>
				<td>${item.cpServiceId!''}</td>
				<td>${item.contentId!''}</td>
				<td>${item.consumeCode!''}</td>
				<td>${item.channelId!''}</td>
				<td>${item.createTime}</td>
			</tr>			
			</#list>
		</tbody>
	</@dwz.table_common>
	<!-- 分页 -->
	<@dwz.pagerBar page=page/>
</@dwz.layout_content>

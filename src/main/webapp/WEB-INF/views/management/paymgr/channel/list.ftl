<@dwz.pagerForm action="${request.contextPath}/management/paymgr/channel/list" page=page>
	<input type="hidden" name="keywords" value="${keywords!''}"/>
</@dwz.pagerForm>

<@dwz.search_form action="${request.contextPath}/management/paymgr/channel/list">
	<@dwz.label_input2 content="代码/名称：">
		<input type="text" name="keywords" value="${keywords!''}"/>
	</@dwz.label_input2>
</@dwz.search_form>

<@dwz.layout_content>
	<@dwz.tool_bar>
		<@shiro.hasPermission name="Channel:save">
			<@dwz.tool_button content="添加渠道" class="user_add" rel="lookup2organization_add" width="530" height="330" href="${request.contextPath}/management/paymgr/channel/create"/>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="Channel:edit">
			<@dwz.tool_button content="编辑渠道" class="user_edit" rel="lookup2organization_edit" width="530" height="330" href="${request.contextPath}/management/paymgr/channel/update/{slt_uid}"/>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="Channel:delete">
			<@dwz.tool_button content="删除渠道" class="user_delete" target="selectedTodo" rel="ids" href="${request.contextPath}/management/paymgr/channel/delete" title="确认要删除?"/>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="Channel:reset">
			<@dwz.tool_line/>
			<@dwz.tool_button content="更新状态" class="user_go" target="ajaxTodo" href="${request.contextPath}/management/paymgr/channel/reset/status/{slt_uid}" title="确认更新状态"/>
			<@dwz.tool_line/>
		</@shiro.hasPermission>
	</@dwz.tool_bar>
	
	<@dwz.table_common layoutH="138">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th width="100">渠道代码</th>
				<th width="100">渠道名称</th>
				<th width="200">签名KEY</th>
				<th width="150">关联游戏</th>
				<th width="100">渠道状态</th>
				<th width="100">结算比例</th>
				<th width="100">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<#list channels as item>
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.code}</td>
				<td>${item.name}</td>
				<td>${item.requestKey!''}</td>
				<td>
					<#if item.data??>
					  ${item.data.name!''}
					</#if>
				</td>
				<td>${item.enable?string("激活","冻结")}</td>
				<td>${item.percent!''}%</td>
				<td>${item.createTime}</td>
			</tr>			
			</#list>
		</tbody>
	</@dwz.table_common>
	<!-- 分页 -->
	<@dwz.pagerBar page=page/>
</@dwz.layout_content>

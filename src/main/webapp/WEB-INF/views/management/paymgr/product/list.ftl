<@dwz.pagerForm action="${request.contextPath}/management/paymgr/product/list" page=page>
	<input type="hidden" name="keywords" value="${keywords!''}"/>
</@dwz.pagerForm>

<@dwz.search_form action="${request.contextPath}/management/paymgr/product/list">
	<@dwz.label_input2 content="计费代码：">
		<input type="text" name="keywords" value="${keywords!''}"/>
	</@dwz.label_input2>
</@dwz.search_form>

<@dwz.layout_content>
	<@dwz.tool_bar>
		<@shiro.hasPermission name="Product:save">
			<@dwz.tool_button content="添加计费代码" class="user_add" rel="lookup2organization_add" width="530" height="330" href="${request.contextPath}/management/paymgr/product/create"/>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="Product:edit">
			<@dwz.tool_button content="编辑计费代码" class="user_edit" rel="lookup2organization_edit" width="530" height="330" href="${request.contextPath}/management/paymgr/product/update/{slt_uid}"/>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="Product:delete">
			<@dwz.tool_button content="删除计费代码" class="user_delete" target="selectedTodo" rel="ids" href="${request.contextPath}/management/paymgr/product/delete" title="确认要删除?"/>
		</@shiro.hasPermission>
	</@dwz.tool_bar>
	
	<@dwz.table_common layoutH="138">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th width="100">计费代码代码</th>
				<th width="100">金额(元)</th>
				<th width="100">关联游戏</th>
				<th width="200">描述</th>
				<th width="100">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<#list products as item>
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.code}</td>
				<td>${item.money}</td>
				<td>
					<#if item.data??>
					  ${item.data.name!''}
					</#if>
				</td>
				<td>${item.description!''}</td>
				<td>${item.createTime}</td>
			</tr>			
			</#list>
		</tbody>
	</@dwz.table_common>
	<!-- 分页 -->
	<@dwz.pagerBar page=page/>
</@dwz.layout_content>

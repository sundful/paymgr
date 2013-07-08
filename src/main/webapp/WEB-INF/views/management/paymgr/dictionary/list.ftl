<@dwz.pagerForm action="${request.contextPath}/management/paymgr/dictionary/list/${parentDictionaryId}" page=page onsubmit="return divSearch(this, 'jbsxBox2dictionary');">
	<input type="hidden" name="keywords" value="${keywords!''}"/>
</@dwz.pagerForm>

<@dwz.search_form action="${request.contextPath}/management/paymgr/dictionary/list/${parentDictionaryId}" onsubmit="return divSearch(this, 'jbsxBox2dictionary');">
	<@dwz.label_input2 content="字典名称：">
		<input type="text" name="keywords" value="${keywords!''}"/>
	</@dwz.label_input2>
</@dwz.search_form>

<@dwz.layout_content>
	<@dwz.tool_bar>
		<@shiro.hasPermission name="Dictionary:save">
			<@dwz.tool_button content="添加字典" class="group_add" width="530" height="250" href="${request.contextPath}/management/paymgr/dictionary/create/${parentDictionaryId}"/>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="Dictionary:edit">
			<@dwz.tool_button content="编辑字典" class="group_edit" width="530" height="250" href="${request.contextPath}/management/paymgr/dictionary/update/{slt_uid}"/>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="Dictionary:delete">
			<@dwz.tool_button content="删除字典" class="group_delete" target="ajaxTodo" callback="reloadRel" href="${request.contextPath}/management/paymgr/dictionary/delete/{slt_uid}" title="确认要删除该字典项?"/>
		</@shiro.hasPermission>
	</@dwz.tool_bar>
	
	<@dwz.table_common layoutH="142" rel="jbsxBox2dictionary">
		<thead>
			<tr>
				<th width="150">字典名称</th>
				<th width="150">字典代码</th>
				<th width="150">字典描述</th>
				<th width="150">父字典</th>
			</tr>
		</thead>
		<tbody>
			<#list dictionaries as item>
			<tr target="slt_uid" rel="${item.id}">
				<td><a href="${request.contextPath}/management/paymgr/dictionary/list/${item.id}" target="ajax" rel="jbsxBox2dictionary">${item.name}</a></td>
				<td>${item.code!''}</td>
				<td>${item.description!''}</td>
				<td>${item.parent.name}</td>
			</tr>			
			</#list>
		</tbody>
	</@dwz.table_common>
	<!-- 分页 -->
	<@dwz.pagerBar page=page rel="jbsxBox2dictionary" onchange="navTabPageBreak({numPerPage:this.value}, 'jbsxBox2dictionary')"/>
</@dwz.layout_content>
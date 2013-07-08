<@dwz.pagerForm action="${request.contextPath}/management/paymgr/order/list" page=page>
	<input type="hidden" name="channel" value="${channel!''}"/>
	<input type="hidden" name="status" value="${status!''}"/>
	<input type="hidden" name="orderId" value="${orderId!''}"/>
	<input type="hidden" name="fromDate" value="${fromDate!''}"/>
	<input type="hidden" name="toDate" value="${toDate!''}"/>
</@dwz.pagerForm>

<@dwz.search_form action="${request.contextPath}/management/paymgr/order/list">
	<@dwz.label_input2 content="订单号：">
		<input type="text" name="orderId" value="${orderId!''}"/>
	</@dwz.label_input2>
	<@dwz.label_input2 content="渠道名称：">
		<select name="channel">
		 <option value="">全部</option>
		 <#list channels as item>
		 	<#if item.code==channel!''>
		 		<option value="${item.code}" selected="selected">${item.name}(${item.code})</option>
		 		<#else>
		 		<option value="${item.code}">${item.name}(${item.code})</option>
		 	</#if>
		 </#list>
		</select>
	</@dwz.label_input2>
	<@dwz.label_input2 content="订单状态：">
		<select name="status">
	 		<option value="true" <#if (status?exists) && (status == 'true')> selected="selected"</#if>>成功</option>
	 		<option value="false" <#if (status?exists) && (status == 'false')> selected="selected"</#if>>失败</option>
		</select>
	</@dwz.label_input2>
	<@dwz.label_input2 content="开始时间：">
		<input type="text" id="fromDate" name="fromDate" class="Wdate" value="${fromDate!''}" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'toDate\')||\'2020-10-01\'}'})"/>
	</@dwz.label_input2>
	<@dwz.label_input2 content="结束时间：">
		<input type="text" id="toDate" name="toDate" class="Wdate" value="${toDate!''}" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'fromDate\')}',maxDate:'2020-10-01'})"/>
	</@dwz.label_input2>
</@dwz.search_form>

<@dwz.layout_content>
	<@dwz.tool_bar>
		<@shiro.hasPermission name="Order:edit">
			<@dwz.tool_button content="编辑订单" class="edit" width="530" height="250" href="${request.contextPath}/management/paymgr/order/update/{slt_uid}"/>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="Order:delete">
			<@dwz.tool_button content="删除订单" class="delete" target="selectedTodo" rel="ids" href="${request.contextPath}/management/paymgr/order/delete" title="确认要删除该订单?"/>
		</@shiro.hasPermission>
	</@dwz.tool_bar>
	
	<@dwz.table_common layoutH="138">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="100">订单号</th>
				<th width="100">渠道代码</th>
				<th width="100">电话号码</th>
				<th width="100">订单金额(单位：元)</th>
				<th width="100">创建时间</th>
				<th width="100">订单状态</th>
			</tr>
		</thead>
		<tbody>
			<#list orders as item>
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.orderId!''}</td>
				<td>${item.channel!''}</td>
				<td>${item.phone!''}</td>
				<td>${item.money!''}</td>
				<td>${item.createTime!''}</td>
				<td>${(item.status=='200000')?string("成功","失败")}</td>
			</tr>
			</#list>
		</tbody>
	</@dwz.table_common>
	<!-- 分页 -->
	<@dwz.pagerBar page=page/>
</@dwz.layout_content>
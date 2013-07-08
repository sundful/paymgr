<@dwz.pagerForm action="${request.contextPath}/management/paymgr/order/cplist" page=page>
	<input type="hidden" name="orderId" value="${orderId!''}"/>
	<input type="hidden" name="fromDate" value="${fromDate!''}"/>
	<input type="hidden" name="toDate" value="${toDate!''}"/>
</@dwz.pagerForm>

<@dwz.search_form action="${request.contextPath}/management/paymgr/order/cplist">
	<@dwz.label_input2 content="订单号：">
		<input type="text" name="orderId" value="${orderId!''}"/>
	</@dwz.label_input2>
	<@dwz.label_input2 content="开始时间：">
		<input type="text" id="fromDate" name="fromDate" class="Wdate" value="${fromDate!''}" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'toDate\')||\'2020-10-01\'}'})"/>
	</@dwz.label_input2>
	<@dwz.label_input2 content="结束时间：">
		<input type="text" id="toDate" name="toDate" class="Wdate" value="${toDate!''}" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'fromDate\')}',maxDate:'2020-10-01'})"/>
	</@dwz.label_input2>
</@dwz.search_form>

<@dwz.layout_content>
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
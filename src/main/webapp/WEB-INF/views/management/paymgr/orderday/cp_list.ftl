<@dwz.pagerForm action="${request.contextPath}/management/paymgr/orderday/cplist" page=page>
	<input type="hidden" name="fromDate" value="${fromDate!''}"/>
	<input type="hidden" name="toDate" value="${toDate!''}"/>
</@dwz.pagerForm>

<@dwz.search_form action="${request.contextPath}/management/paymgr/orderday/cplist">
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
				<th width="100">时间</th>
				<th width="100">渠道代码</th>
				<th width="100">订单金额(单位：元)</th>
				<th width="100">总订单量</th>
				<th width="100">成功订单量</th>
				<th width="100">成功总金额(单位：元)</th>
			</tr>
		</thead>
		<tbody>
			<#list orderDays as item>
			<tr target="slt_uid" rel="${item.id}">
				<td>${item.statDate!''}</td>
				<td>${item.channel!''}</td>
				<td>${item.money!''}</td>
				<td>${item.totalCount!''}</td>
				<td>${item.cpSuccessCount!''}</td>
				<td>${item.cpTotalMoney!''}</td>
			</tr>
			</#list>
		</tbody>
	</@dwz.table_common>
	<!-- 分页 -->
	<@dwz.pagerBar page=page/>
</@dwz.layout_content>
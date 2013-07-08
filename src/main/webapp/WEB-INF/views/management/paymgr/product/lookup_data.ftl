<#macro lookup_data children>
    <#if children?exists && children?size gt 0>
    	<ul>
        <#list children as data>
			<li>
				<a href="javascript:" onclick="$.bringBack({id:'${data.id}', name:'${data.name}'})">${data.name}</a>
            </li>
		</#list>
		</ul>
	</#if>	
</#macro>

<@dwz.layout_content>
	<@dwz.layout_form_content layoutH="58">
		<ul class="tree expand">
			<li><a href="javascript:">游戏列表</a>
				<#assign depth = 1 />
				<@lookup_data children=datas />
			</li>
		</ul>
	</@dwz.layout_form_content>
	
	<@dwz.form_bar closeTitle="关闭"/>	
</@dwz.layout_content>

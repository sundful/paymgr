<#-- 
class="minitree treeFolder treeCheck expand"
-->
<#macro minitree object class="tree expand" href="" rel="" target="">
	<ul class="${class}">
	<#assign depth = 1 />
	<li><a 
		   <#if href!=""> href="${href}/${object.id}"<#else>href="javascript:void(0);"</#if><#rt>
		   <#if target!=""> target="${target}"</#if><#rt>
		   <#if rel!=""> rel="${rel}"</#if><#rt>
		>${object.name}</a>
		<#if object.children?exists && object.children?size gt 0>
		<ul>
		<#list object.children as m>
			<li><a href="${href}/${m.id}" target="${target}"<#if rel!=""> rel="${rel}"</#if>>${m.name}</a></li>
		</#list>
		</ul>
		</#if>
	</li>
	</ul>
</#macro>
<%--
- form.jsp
-
- Copyright (C) 2012-2024 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="client.contract.list.label.code" path="code" placeholder="client.contract.form.placerholder.code"/>
	<acme:input-select code="client.contract.list.label.title" path="project" choices="${projects}" />
	<acme:input-moment code="client.contract.list.label.moment" path="instantiationMoment" readonly = "true"/>
	<acme:input-textbox code="client.contract.list.label.providerName" path="providerName" placeholder="client.contract.form.placerholder.providerName"/>
	<acme:input-textbox code="client.contract.list.label.customerName" path="customerName" placeholder="client.contract.form.placerholder.customerName"/>
	<acme:input-textbox code="client.contract.list.label.goals" path="goals" placeholder="client.contract.form.placerholder.goals"/>
	<acme:input-money code="client.contract.list.label.budget" path="budget"/>
	<acme:input-url code="client.contract.list.label.link" path="link"/>


	<jstl:choose> 
	<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="client.contract.list.button.progressLogs" action="/client/progress-log/list-mine?masterId=${id}"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')}">
			<acme:button code="client.contract.list.button.progressLogs" action="/client/progress-log/list-mine?masterId=${id}"/>
			<acme:submit code="client.contract.list.button.update" action="/client/contract/update"/>
			<acme:submit code="client.contract.list.button.delete" action="/client/contract/delete"/>
			<acme:submit code="client.contract.list.button.publish" action="/client/contract/publish"/>	
		</jstl:when>
		
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="client.contract.list.submit.create" action="/client/contract/create"/>
		</jstl:when>
	</jstl:choose> 

</acme:form>
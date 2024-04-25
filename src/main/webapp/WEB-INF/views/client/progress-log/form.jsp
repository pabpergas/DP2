<%--
- banner.jsp
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
	<acme:input-textbox code="client.progressLog.list.label.recordId" path="recordId" />
	<acme:input-double code="client.progressLog.form.label.completenessPercentage" path="completenessPercentage"/>
	<acme:input-textbox code="client.progressLog.list.label.progressComment" path="progressComment" />
	<acme:input-moment code="client.progressLog.list.label.registrationMoment" path="registrationMoment" readonly ="true"/>
	<acme:input-textbox code="client.progressLog.list.label.responsiblePerson" path="responsiblePerson" />

	
	<jstl:choose> 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:submit code="client.contract.list.submit.update" action="/client/progressLog/update"/>
			<acme:submit code="client.contract.list.submit.delete" action="/client/progressLog/delete"/>
			<acme:submit code="client.contract.list.submit.publish" action="/client/progress-log/publish"/>			
		</jstl:when>
		
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="client.contract.list.button.create" action="/client/progressLog/create?masterId=${masterId}"/>
		</jstl:when>
	</jstl:choose> 
	
</acme:form>
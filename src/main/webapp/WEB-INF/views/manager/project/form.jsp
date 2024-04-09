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
	<acme:input-textbox code="manager.project.form.label.code" path="code"/>
	<acme:input-textbox code="manager.project.form.label.title" path="title"/>
	<acme:input-textbox code="manager.project.form.label.summary" path="summary"/>
	<acme:input-textbox code="manager.project.form.label.hasFatalErrors" path="hasFatalErrors"/>
	<acme:input-textbox code="manager.project.form.label.cost" path="cost"/>
	<acme:input-textbox code="manager.project.form.label.link" path="link"/>


	<jstl:choose> 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')}">
			<acme:submit code="manager.project.list.button.update" action="/manager/project/update"/>
			<acme:submit code="manager.project.list.button.delete" action="/sponsor/project/delete"/>
			<acme:submit code="manager.project.list.button.publish" action="/sponsor/project/publish"/>	
		</jstl:when>
		
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="manager.project.list.button.create" action="/sponsor/project/create"/>
		</jstl:when>
	</jstl:choose> 

</acme:form>
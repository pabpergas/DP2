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
	<acme:input-textbox code="manager.project.form.label.cost" path="cost"/>
	<acme:input-url code="manager.project.form.label.link" path="link"/>
	<acme:input-checkbox code="manager.project.form.label.hasFatalErrors" path="hasFatalErrors"/>


	<jstl:choose>
		<jstl:when  test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}"> <!-- && draftMode == false -->
			<acme:button code="manager.project.form.button.add-userstories" action="/manager/project-user-stories/create?projectId=${id}"/>
			<acme:button code="manager.project.form.button.list-userstories" action="/manager/user-stories/list-by-proyect?projectId=${id}"/>
			<acme:submit code="manager.project.list.button.update" action="/manager/project/update"/>
			<acme:submit code="manager.project.list.button.delete" action="/manager/project/delete"/>
			<acme:submit code="manager.project.list.button.publish" action="/manager/project/publish"/>
		</jstl:when>
		
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="manager.project.list.button.create" action="/manager/project/create"/>
		</jstl:when>
		<jstl:otherwise>
			<acme:button code="manager.project.form.button.list-userstories" action="/manager/user-stories/list-by-proyect?projectId=${id}"/>
		</jstl:otherwise>	
	</jstl:choose> 

</acme:form>
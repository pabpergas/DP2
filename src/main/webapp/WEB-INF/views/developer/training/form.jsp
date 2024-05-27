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
	<acme:input-textbox code="developer.training.form.label.code" path="code"/>
	<acme:input-moment code="developer.training.form.label.moment" path="moment"/>
	<acme:input-textbox code="developer.training.form.label.details" path="details"/>	
	<acme:input-select code="developer.training.form.label.difficulty-level" path="difficulty" choices="${difficulty}"/>	
	<acme:input-moment code="developer.training.form.label.update-moment" path="updateMoment"/>
	<acme:input-url code="developer.training.form.label.link" path="link"/>	
	<acme:input-integer code="developer.training.form.label.estimated-total-time" path="estimatedTotalTime"/>
	<acme:input-select code="developer.training.form.label.project" path="project" choices="${projects}"/>	
	
	<jstl:choose>
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="developer.training.form.button.sessions" action="/developer/sessions/list?masterId=${id}"/>			
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:submit code="developer.training.form.button.update" action="/developer/training/update"/>
			<acme:submit code="developer.training.form.button.delete" action="/developer/training/delete"/>
			<acme:submit code="developer.training.form.button.publish" action="/developer/training/publish"/>
			<acme:button code="developer.training.form.button.sessions" action="/developer/sessions/list?masterId=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="developer.training.form.button.create-training" action="/developer/training/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>
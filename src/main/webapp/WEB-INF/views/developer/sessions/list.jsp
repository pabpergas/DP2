<%--
- list.jsp
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

<acme:list>
	<acme:list-column code="developer.training-session.list.label.code" path="code" width="10%"/>
	<acme:list-column code="developer.training-session.list.label.session-start" path="startDate" width="10%"/>
	<acme:list-column code="developer.training-session.list.label.session-end" path="endDate" width="10%"/>	
	<acme:list-column code="developer.training-session.list.label.location" path="location" width="10%"/>	
	<acme:list-column code="developer.training-session.list.label.instructor" path="instructor" width="10%"/>	
	<acme:list-column code="developer.training-session.list.label.contact-email" path="email" width="10%"/>
	<acme:list-column code="developer.training-session.list.label.link" path="link" width="10%"/>
	<acme:list-column code="developer.training-session.list.label.draft-mode" path="draftMode" width="10%"/>

</acme:list>

<jstl:if test="${_command == 'list'}">
	<acme:button code="developer.training-session.list.button.create-form" action="/developer/sessions/create?masterId=${masterId}"/>
</jstl:if>
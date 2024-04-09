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
	<acme:input-textbox code="sponsor.sponsorShip.list.label.code" path="code" placeholder="sponsor.sponsorShip.form.placerholder.code"/>
	<acme:input-select code="sponsor.sponsorShip.list.label.project.title" path="project" choices="${projects}"/>
	<acme:input-moment code="sponsor.sponsorShip.list.label.moment" path="moment" />
	<acme:input-moment code="sponsor.sponsorShip.list.label.startDate" path="startDate"/>
	<acme:input-moment code="sponsor.sponsorShip.list.label.endDate" path="endDate"/>
	<acme:input-money code="sponsor.sponsorShip.list.label.amount" path="amount"/>
	<acme:input-textbox code="sponsor.sponsorShip.list.label.type" path="type" placeholder="sponsor.sponsorShip.form.placerholder.type"/>
	<acme:input-email code="sponsor.sponsorShip.list.label.contactEmail" path="contactEmail"/>
	<acme:input-url code="sponsor.sponsorShip.list.label.link" path="link"/>
		
	<jstl:choose> 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')}">
			<acme:submit code="sponsor.sponsorShip.list.button.update" action="/sponsor/sponsor-ship/update"/>
			<acme:submit code="sponsor.sponsorShip.list.button.delete" action="/sponsor/sponsor-ship/delete"/>
			<acme:submit code="sponsor.sponsorShip.list.button.publish" action="/sponsor/sponsor-ship/publish"/>	
		</jstl:when>
		
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="sponsor.sponsorShip.list.button.create" action="/sponsor/sponsor-ship/create"/>
		</jstl:when>
	</jstl:choose> 

</acme:form>

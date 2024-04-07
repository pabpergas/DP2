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
	<acme:input-select code="sponsor.invoice.list.label.sponsorShip.code" path="sponsorShip" choices="${sponsorShips}"/>
	<acme:input-textbox code="sponsor.invoice.list.label.code" path="code" />
	<acme:input-textbox code="sponsor.invoice.list.label.registrationTime" path="registrationTime" />
	<acme:input-textbox code="sponsor.invoice.list.label.dueDate" path="dueDate"/>
	<acme:input-textbox code="sponsor.invoice.list.label.quantity" path="quantity"/>
	<acme:input-textbox code="sponsor.invoice.list.label.tax" path="tax"/>
	<acme:input-textbox code="sponsor.invoice.list.label.link" path="link"/>
	
	<jstl:choose> 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')}">
			<acme:submit code="sponsor.sponsorShip.list.button.update" action="/sponsor/invoice/update"/>
			<acme:submit code="sponsor.sponsorShip.list.button.delete" action="/sponsor/invoice/delete"/>
			<acme:submit code="sponsor.sponsorShip.list.button.publish" action="/sponsor/invoice/publish"/>	
		</jstl:when>
		
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="sponsor.sponsorShip.list.button.create" action="/sponsor/invoice/create"/>
		</jstl:when>
	</jstl:choose> 
	
</acme:form>
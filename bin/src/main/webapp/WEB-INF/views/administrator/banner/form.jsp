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
	<acme:input-moment code="administrator.banner.list.label.instantationMoment" path="instantationMoment" readonly="true" />
	<acme:input-moment code="administrator.banner.list.label.startDisplay" path="startDisplay" />
	<acme:input-moment code="administrator.banner.list.label.endDisplay" path="endDisplay" />
	<acme:input-url code="administrator.banner.list.label.pictureLink" path="pictureLink"/>
	<acme:input-textbox code="administrator.banner.list.label.slogan" path="slogan"/>
	<acme:input-url code="administrator.banner.list.label.documentLink" path="documentLink"/>
	
		<jstl:choose> 
		<jstl:when test="${acme:anyOf(_command, 'show|update|deletes')}">
			<acme:submit code="sponsor.sponsorShip.list.submit.update" action="/administrator/banner/update"/>
			<acme:submit code="sponsor.sponsorShip.list.submit.delete" action="/administrator/banner/delete"/>
		</jstl:when>
		
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="administrator.invoice.list.button.create" action="/administrator/banner/create"/>
		</jstl:when>
	</jstl:choose> 
	
</acme:form>



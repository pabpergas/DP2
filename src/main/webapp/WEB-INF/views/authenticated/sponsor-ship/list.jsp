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

<acme:list>
	<acme:list-column code="authenticated.sponsorShip.list.label.project.title" path="project.title" width="5%"/>
	<acme:list-column code="authenticated.sponsorShip.list.label.code" path="code" width="10%"/>
	<acme:list-column code="authenticated.sponsorShip.list.label.moment" path="moment" width="20%"/>
	<acme:list-column code="authenticated.sponsorShip.list.label.startDate" path="startDate" width="20%"/>
	<acme:list-column code="authenticated.sponsorShip.list.label.endDate" path="endDate" width="20%"/>
	<acme:list-column code="authenticated.sponsorShip.list.label.amount" path="amount" width="20%"/>
	<acme:list-column code="authenticated.sponsorShip.list.label.type" path="type" width="20%"/>
	<acme:list-column code="authenticated.sponsorShip.list.label.contactEmail" path="contactEmail" width="20%"/>
	<acme:list-column code="authenticated.sponsorShip.list.label.sponsor.name" path="sponsor.name" width="20%"/>
	
	<acme:list-column code="authenticated.sponsorShip.list.label.link" path="link" width="20%"/>
</acme:list>
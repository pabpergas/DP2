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
	<acme:input-textbox code="sponsor.invoice.list.label.sponsorShip.code" path="sponsorShip.code" />
	<acme:input-textbox code="sponsor.invoice.list.label.code" path="code" />
	<acme:input-textbox code="sponsor.invoice.list.label.registrationTime" path="registrationTime" />
	<acme:input-textbox code="sponsor.invoice.list.label.dueDate" path="dueDate"/>
	<acme:input-textbox code="sponsor.invoice.list.label.quantity" path="quantity"/>
	<acme:input-textbox code="sponsor.invoice.list.label.tax" path="tax"/>
	<acme:input-textbox code="sponsor.invoice.list.label.link" path="link"/>
</acme:form>
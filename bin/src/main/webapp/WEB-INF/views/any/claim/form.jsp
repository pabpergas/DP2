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

<acme:form readonly="${readonly}">
	<acme:input-textbox code="any.claim.list.label.code" path="code" />
	<acme:input-moment code="any.claim.list.label.instantiation" path="instantiation" readonly="true"/>
	<acme:input-textbox code="any.claim.list.label.heading" path="heading" />
	<acme:input-textbox code="any.claim.list.label.description" path="description"/>
	<acme:input-textbox code="any.claim.list.label.department" path="department"/>
	<acme:input-email code="any.claim.list.label.email" path="email"/>
	<acme:input-url code="any.claim.list.label.link" path="link"/>
	
		<jstl:if test="${!readonly}">
		<acme:input-checkbox code="any.claim.form.label.confirmation" path="confirmation"/>
		<acme:submit code="any.claim.form.button.create" action="/any/claim/create"/>
	</jstl:if>
	
</acme:form>

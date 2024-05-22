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

<h2>
	<acme:message code="manager.manager-dashboard.form.title"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="manager.project.form.label.totalMusts"/>
		</th>
		<td>
			<acme:print value="${totalMusts}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.project.form.label.totalCoulds"/>
		</th>
		<td>
			<acme:print value="${totalCoulds}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.project.form.label.totalShoulds"/>
		</th>
		<td>
			<acme:print value="${totalShoulds}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.project.form.label.totalWonts"/>
		</th>
		<td>
			<acme:print value="${totalWonts}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.project.form.label.averageEstimatedCostUserHistory"/>
		</th>
		<td>
			<acme:print value="${averageEstimatedCostUserHistory}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.project.form.label.deviationEstimatedCostUserHistory"/>
		</th>
		<td>
			<acme:print value="${deviationEstimatedCostUserHistory}"/>
		</td>
	</tr>
			<tr>
		<th scope="row">
			<acme:message code="manager.project.form.label.minimiunEstimatedCostUserHistory"/>
		</th>
		<td>
			<acme:print value="${minimiunEstimatedCostUserHistory}"/>
		</td>
	</tr>
			<tr>
		<th scope="row">
			<acme:message code="manager.project.form.label.maximunEstimatedCostUserHistory"/>
		</th>
		<td>
			<acme:print value="${maximunEstimatedCostUserHistory}"/>
		</td>
	</tr>
			<tr>
		<th scope="row">
			<acme:message code="manager.project.form.label.averageEstimatedCostProject"/>
		</th>
		<td>
			<acme:print value="${averageEstimatedCostProject}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.project.form.label.deviationEstimatedCostProject"/>
		</th>
		<td>
			<acme:print value="${deviationEstimatedCostProject}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.project.form.label.minimiunEstimatedCostProject"/>
		</th>
		<td>
			<acme:print value="${minimiunEstimatedCostProject}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code= "manager.project.form.label.maximunEstimatedCostProject"/>
		</th>
		<td>
			<acme:print value="${maximunEstimatedCostProject}"/>
		</td>
	</tr>
	
</table>

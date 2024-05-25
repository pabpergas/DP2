<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<div>
	<acme:form>
		<div>
			<acme:message code="auditor.dashboard.form.label.totalNumberStaticAudits"/>
			<acme:print value="${totalNumberStaticAudits}"/>
		</div>
		
		<div>
			<acme:message code="auditor.dashboard.form.label.totalNumberDinamicAudits"/>
			<acme:print value="${totalNumberDinamicAudits}"/>
		</div>
		
		<div>
			<acme:message code="auditor.dashboard.form.label.minNumberRecords"/>
			<acme:print value="${minNumberRecords}"/>
		</div>
		
		<div>
			<acme:message code="auditor.dashboard.form.label.maxNumberRecords"/>
			<acme:print value="${maxNumberRecords}"/>
		</div>
		
		<div>
			<acme:message code="auditor.dashboard.form.label.averageNumberRecords"/>
			<acme:print value="${averageNumberRecords}"/>
		</div>
		
		<div>
			<acme:message code="auditor.dashboard.form.label.deviationNumberRecord"/>
			<acme:print value="${deviationNumberRecords}"/>
		</div>
		<div>
			<acme:message code="auditor.dashboard.form.label.minPeriodRecord"/>
			<acme:print value="${minPeriodRecord}"/>
		</div>
		
		<div>
			<acme:message code="auditor.dashboard.form.label.maxPeriodRecord"/>
			<acme:print value="${maxPeriodRecord}"/>
		</div>
		
		<div>
			<acme:message code="auditor.dashboard.form.label.averagePeriodRecord"/>
			<acme:print value="${averagePeriodRecord}"/>
		</div>
		
		<div>
			<acme:message code="auditor.dashboard.form.label.deviationPeriodRecord"/>
			<acme:print value="${deviationPeriodRecord}"/>
		</div>
	</acme:form>
</div>
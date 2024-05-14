<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list navigable="true">
	<acme:list-column code="auditor.auditRecord.list.label.codeAudit" path="codeAudit" width="10%"/>
	<acme:list-column code="auditor.auditRecord.list.label.startAudition" path="startAudition" width="10%"/>
	<acme:list-column code="auditor.auditRecord.list.label.endAudition" path="endAudition" width="10%" />
	<acme:list-column code="auditor.auditRecord.list.label.mark" path="mark" width="10%"/>
	<acme:list-column code="auditor.auditRecord.list.label.informationLink" path="informationLink" width="10%"/>
</acme:list>

<jstl:if test="${_command == 'list'}">
	<acme:button code="auditor.auditRecord.list.button.create" action="/auditor/audit-record/create?codeAuditId=${codeAuditId}"/>
</jstl:if>
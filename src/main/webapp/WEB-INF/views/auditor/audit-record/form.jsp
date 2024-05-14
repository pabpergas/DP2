<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="auditor.auditRecord.list.label.codeAudit" path="codeAudit" />
	<acme:input-moment code="auditor.auditRecord.list.label.startAudition" path="startAudition" />
	<acme:input-moment code="auditor.auditRecord.list.label.endAudition" path="endAudition" />
	<acme:input-select code="auditor.auditRecord.list.label.mark" path="mark" choices="${marks}" />
	<acme:input-textbox code="auditor.auditRecord.list.label.informationLink" path="informationLink" />
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode}">
			<acme:submit code="auditor.auditRecord.form.button.update" action="/auditor/audit-record/update" />
			<acme:submit code="auditor.auditRecord.form.button.delete" action="/auditor/audit-record/delete" />
			<acme:submit code="auditor.auditRecord.form.button.publish" action="/auditor/audit-record/publish" />
		</jstl:when>
		
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.auditRecord.form.button.create" action="auditor/audit-record/create?codeAuditId=${codeAuditId}" />
		</jstl:when>
	</jstl:choose>
	
</acme:form>
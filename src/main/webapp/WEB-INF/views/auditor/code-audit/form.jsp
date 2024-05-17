<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-select code="auditor.codeAudit.list.label.project" path="project" choices="${projects}"/>
	<acme:input-textbox code="auditor.codeAudit.list.label.code" path="code"/>
	<acme:input-moment code="auditor.codeAudit.list.label.executionDate" path="executionDate"/>
	<acme:input-select code="auditor.codeAudit.list.label.type" path="type" choices="${types}"/>
	<acme:input-textbox code="auditor.codeAudit.list.label.correctiveActions" path="correctiveActions"/>
	<acme:input-textbox code="auditor.codeAudit.list.label.mark" readonly="true" path="mark"/>
	<acme:input-textbox code="auditor.codeAudit.list.label.link" path="link"/>
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode}">
			<acme:submit code="auditor.codeAudit.form.button.update" action="/auditor/code-audit/update"/>
			<acme:submit code="auditor.codeAudit.form.button.delete" action="/auditor/code-audit/delete"/>
			<acme:submit code="auditor.codeAudit.form.button.publish" action="/auditor/code-audit/publish"/>
			<acme:button code="auditor.codeAudit.form.button.list.auditRecord" action="/auditor/audit-record/list?codeAuditId=${id}"/>
		</jstl:when>
		
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && !draftMode}">
			<acme:button code="auditor.codeAudit.form.button.list.auditRecord" action="/auditor/audit-record/list?codeAuditId=${id}"/>
		</jstl:when>
		
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.codeAudit.form.button.create" action="/auditor/code-audit/create"/>
		</jstl:when>
			
	</jstl:choose>
	
</acme:form>
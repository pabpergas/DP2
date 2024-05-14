<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list navigable="true">
	<acme:list-column code="auditor.codeAudit.list.label.project" path ="project" width="10%"/>
	<acme:list-column code="auditor.codeAudit.list.label.code" path="code" width="10%"/>
	<acme:list-column code="auditor.codeAudit.list.label.executionDate" path = "executionDate" width ="10%"/>
	<acme:list-column code="auditor.codeAudit.list.label.type" path="type" width="10%"/>
	<acme:list-column code="auditor.codeAudit.list.label.correctiveActions" path="correctiveActions" width="10%"/>
	<acme:list-column code="auditor.codeAudit.list.label.mark" path="mark" width="10%"/>
</acme:list>

<jstl:if test="${_command == 'list-mine'}">
	<acme:button code="auditor.codeAudit.list.button.create" action="/auditor/code-audit/create"/>
</jstl:if>
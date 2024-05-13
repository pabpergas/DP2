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

<div>
    <acme:form>	
        <div>        
                <acme:message code="sponsor.sponsorShip.form.label.totalInvoicesWithTaxLessThanOrEqualTo21Percent"/>
                <acme:print value="${totalInvoicesWithTaxLessThanOrEqualTo21Percent}"/>
        </div>
        
        <div>
                <acme:message code="sponsor.sponsorShip.form.label.totalSponsorshipsWithLink"/>
                <acme:print value="${totalSponsorshipsWithLink}"/>
        </div>
        
        <div>
                <acme:message code="sponsor.sponsorShip.form.label.averageSponsorshipAmount"/>
                <acme:print value="${averageSponsorshipAmount}"/>
        </div>
        
        <div>
                <acme:message code="sponsor.sponsorShip.form.label.deviationSponsorshipAmount"/>
            
                <acme:print value="${deviationSponsorshipAmount}"/>
        </div>
        
        <div>
                <acme:message code="sponsor.sponsorShip.form.label.minimumSponsorshipAmount"/>
                <acme:print value="${minimumSponsorshipAmount}"/>
        </div>
        
        <div>
                <acme:message code="sponsor.sponsorShip.form.label.maximumSponsorshipAmount"/>
                <acme:print value="${maximumSponsorshipAmount}"/>
        </div>
        
        
        <div>
                <acme:message code="sponsor.sponsorShip.form.label.averageInvoiceQuantity"/>
                <acme:print value="${averageInvoiceQuantity}"/>
        </div>
        
        <div>
                <acme:message code="sponsor.sponsorShip.form.label.deviationInvoiceQuantity"/>
                <acme:print value="${deviationInvoiceQuantity}"/>
        </div>
        
        <div>
                <acme:message code="sponsor.sponsorShip.form.label.minimumInvoiceQuantity"/>
                <acme:print value="${minimumInvoiceQuantity}"/>
        </div>
        
        <div>
                <acme:message code="sponsor.sponsorShip.form.label.maximumInvoiceQuantity"/>
                <acme:print value="${maximumInvoiceQuantity}"/>
        </div>
        
    </acme:form>
</div>

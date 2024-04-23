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
	<acme:list-column code="sponsor.sponsorShip.list.label.totalInvoicesWithTaxLessThanOrEqualTo21Percent" path="totalInvoicesWithTaxLessThanOrEqualTo21Percent" width="10%"/>
	<acme:list-column code="sponsor.sponsorShip.list.label.totalSponsorshipsWithLink" path="totalSponsorshipsWithLink" width="10%"/>
	<acme:list-column code="sponsor.sponsorShip.list.label.averageSponsorshipAmount" path="averageSponsorshipAmount" width="10%"/>
	<acme:list-column code="sponsor.sponsorShip.list.label.deviationSponsorshipAmount" path="deviationSponsorshipAmount" width="10%"/>
	<acme:list-column code="sponsor.sponsorShip.list.label.minimumSponsorshipAmount" path="minimumSponsorshipAmount" width="10%"/>
	<acme:list-column code="sponsor.sponsorShip.list.label.maximumSponsorshipAmount" path="maximumSponsorshipAmount" width="10%"/>
	
	<acme:list-column code="sponsor.sponsorShip.list.label.averageSponsorshipQuantity" path="averageSponsorshipQuantity" width="10%"/>
	<acme:list-column code="sponsor.sponsorShip.list.label.deviationSponsorshipQuantity" path="deviationSponsorshipQuantity" width="10%"/>
	<acme:list-column code="sponsor.sponsorShip.list.label.minimumSponsorshipQuantity" path="minimumSponsorshipQuantity" width="10%"/>
	<acme:list-column code="sponsor.sponsorShip.list.label.maximumSponsorshipQuantity" path="maximumSponsorshipQuantity" width="10%"/>
</acme:list>

<jstl:if test="${_command == 'list-mine'}">
	<acme:button code="sponsor.sponsorShip.list.button.create" action="/sponsor/sponsor-dashboard/show"/>
</jstl:if>

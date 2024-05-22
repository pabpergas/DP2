<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="acme" uri="http://acme-framework.org/" %>

<table class="table table-sm">
    <tr>
        <th scope="row">
            <acme:message code="sponsor.sponsorShip.form.label.totalInvoicesWithTaxLessThanOrEqualTo21Percent"/>
        </th>
        <td>
            <c:choose>
                <c:when test="${not empty totalInvoicesWithTaxLessThanOrEqualTo21Percent}">
                    <acme:print value="${totalInvoicesWithTaxLessThanOrEqualTo21Percent}"/>
                </c:when>
                <c:otherwise>
                    <acme:print value="N/A"/>
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <tr>
        <th scope="row">
            <acme:message code="sponsor.sponsorShip.form.label.totalSponsorshipsWithLink"/>
        </th>
        <td>
            <c:choose>
                <c:when test="${not empty totalSponsorshipsWithLink}">
                    <acme:print value="${totalSponsorshipsWithLink}"/>
                </c:when>
                <c:otherwise>
                    <acme:print value="N/A"/>
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <tr>
        <th scope="row">
            <acme:message code="sponsor.sponsorShip.form.label.averageSponsorshipAmount"/>
        </th>
        <td>
            <c:choose>
                <c:when test="${not empty averageSponsorshipAmount}">
                    <acme:print value="${averageSponsorshipAmount}"/>
                </c:when>
                <c:otherwise>
                    <acme:print value="N/A"/>
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <tr>
        <th scope="row">
            <acme:message code="sponsor.sponsorShip.form.label.averageSponsorshipAmount"/>
        </th>
        <td>
            <c:choose>
                <c:when test="${not empty averageSponsorshipAmount}">
                    <acme:print value="${averageSponsorshipAmount}"/>
                </c:when>
                <c:otherwise>
                    <acme:print value="N/A"/>
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <tr>
        <th scope="row">
            <acme:message code="sponsor.sponsorShip.form.label.deviationSponsorshipAmount"/>
        </th>
        <td>
            <c:choose>
                <c:when test="${not empty deviationSponsorshipAmount}">
                    <acme:print value="${deviationSponsorshipAmount}"/>
                </c:when>
                <c:otherwise>
                    <acme:print value="N/A"/>
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <tr>
        <th scope="row">
            <acme:message code="sponsor.sponsorShip.form.label.minimumSponsorshipAmount"/>
        </th>
        <td>
            <c:choose>
                <c:when test="${not empty minimumSponsorshipAmount}">
                    <acme:print value="${minimumSponsorshipAmount}"/>
                </c:when>
                <c:otherwise>
                    <acme:print value="N/A"/>
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <tr>
        <th scope="row">
            <acme:message code="sponsor.sponsorShip.form.label.maximumSponsorshipAmount"/>
        </th>
        <td>
            <c:choose>
                <c:when test="${not empty maximumSponsorshipAmount}">
                    <acme:print value="${maximumSponsorshipAmount}"/>
                </c:when>
                <c:otherwise>
                    <acme:print value="N/A"/>
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <tr>
        <th scope="row">
            <acme:message code="sponsor.sponsorShip.form.label.averageInvoiceQuantity"/>
        </th>
        <td>
            <c:choose>
                <c:when test="${not empty averageInvoiceQuantity}">
                    <acme:print value="${averageInvoiceQuantity}"/>
                </c:when>
                <c:otherwise>
                    <acme:print value="N/A"/>
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <tr>
        <th scope="row">
            <acme:message code="sponsor.sponsorShip.form.label.deviationInvoiceQuantity"/>
        </th>
        <td>
            <c:choose>
                <c:when test="${not empty deviationInvoiceQuantity}">
                    <acme:print value="${deviationInvoiceQuantity}"/>
                </c:when>
                <c:otherwise>
                    <acme:print value="N/A"/>
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <tr>
        <th scope="row">
            <acme:message code="sponsor.sponsorShip.form.label.minimumInvoiceQuantity"/>
        </th>
        <td>
            <c:choose>
                <c:when test="${not empty minimumInvoiceQuantity}">
                    <acme:print value="${minimumInvoiceQuantity}"/>
                </c:when>
                <c:otherwise>
                    <acme:print value="N/A"/>
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <tr>
        <th scope="row">
            <acme:message code="sponsor.sponsorShip.form.label.maximumInvoiceQuantity"/>
        </th>
        <td>
            <c:choose>
                <c:when test="${not empty maximumInvoiceQuantity}">
                    <acme:print value="${maximumInvoiceQuantity}"/>
                </c:when>
                <c:otherwise>
                    <acme:print value="N/A"/>
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
</table>

<%--
- menu.jsp
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
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:menu-bar code="master.menu.home">
	<acme:menu-left>
		<acme:menu-option code="master.menu.anonymous" access="isAnonymous()">
			<acme:menu-suboption code="master.menu.anonymous.claim" action="/any/claim/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.anonymous.favourite-link-2" action="https://www.instagram.com/"/>
			<acme:menu-suboption code="master.menu.anonymous.favourite-link-1" action="https://www.youtube.com/"/>
			<acme:menu-suboption code="master.menu.anonymous.favourite-link-3" action="https://www.twitch.tv/"/>
			<acme:menu-suboption code="master.menu.anonymous.favourite-link-4" action="https://www.facebook.com/"/>
			<acme:menu-suboption code="master.menu.anonymous.favourite-link-5" action="https://www.tiktok.com"/>
			
		</acme:menu-option>
		<acme:menu-option code="master.menu.anonymous.claim" action="/any/claim/list" access="isAnonymous()"/>

		<acme:menu-option code="master.menu.administrator" access="hasRole('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.user-accounts" action="/administrator/user-account/list"/>
			<acme:menu-suboption code="master.menu.administrator.banner" action="/administrator/banner/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.populate-initial" action="/administrator/system/populate-initial"/>
			<acme:menu-suboption code="master.menu.administrator.populate-sample" action="/administrator/system/populate-sample"/>			
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.shut-down" action="/administrator/system/shut-down"/>
		</acme:menu-option>
		

		<acme:menu-option code="master.menu.client" access="hasRole('Client')">
			<acme:menu-suboption code="master.menu.client.dashboard" action="/client/client-dashboard/show"/>
			<acme:menu-suboption code="master.menu.client.list-contract" action="/client/contract/list-mine"/>
    </acme:menu-option>
		
		<acme:menu-option code="master.menu.manager" access="hasRole('Manager')">
			<acme:menu-suboption code="master.menu.manager.all-projects" action="/manager/project/list-all"/>
			<acme:menu-suboption code="master.menu.manager.my-projects" action="/manager/project/list-mine"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.manager.user-stories" action="/manager/user-story/list"/>
			<acme:menu-suboption code="master.menu.manager.mine-user-stories" action="/manager/user-story/list-mine"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.manager.dashboard" action="/manager/manager-dashboard/show"/>
		</acme:menu-option>


	
	
		
		<acme:menu-option code="master.menu.sponsor" access="hasRole('Sponsor')">
			<acme:menu-suboption code="master.menu.sponsor.sponsor-ships" action="/sponsor/sponsor-ship/list-mine" access="hasRole('Sponsor')"/>
			
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.sponsor.dashboard" action="/sponsor/sponsor-dashboard/show"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.auditor" access="hasRole('Auditor')">
			<acme:menu-suboption code="master.menu.auditor.codeAudit" action="/auditor/code-audit/list-mine" access="hasRole('Auditor')"/>
			
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.auditor.dashboard" action="/auditor/auditor-dashboard/show" access="hasRole('Auditor')"/>
		</acme:menu-option>
		
				<acme:menu-option code="master.menu.developer" access="hasRole('Developer')">
			<acme:menu-suboption code="master.menu.developer.training-modules" action="/developer/training/list-mine" access="hasRole('Developer')"/>
			
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.developer.dashboard" action="/developer/developer-dashboard/show"/>
		</acme:menu-option>
		<acme:menu-option code="master.menu.anonymous.claim" action="/any/claim/list" access="!isAnonymous()"/>
		
	</acme:menu-left>

	<acme:menu-right>
		<acme:menu-option code="master.menu.sign-up" action="/anonymous/user-account/create" access="isAnonymous()"/>
		<acme:menu-option code="master.menu.sign-in" action="/anonymous/system/sign-in" access="isAnonymous()"/>


		<acme:menu-option code="master.menu.user-account" access="isAuthenticated()">
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.user-account.general-data" action="/authenticated/user-account/update"/>
			<acme:menu-suboption code="master.menu.user-account.become-sponsor" action="/authenticated/sponsor/create" access="!hasRole('Sponsor')"/>
			<acme:menu-suboption code="master.menu.user-account.sponsor" action="/authenticated/sponsor/update" access="hasRole('Sponsor')"/>	
			<acme:menu-suboption code="master.menu.user-account.become-manager" action="/authenticated/manager/create" access="!hasRole('Manager')"/>
			<acme:menu-suboption code="master.menu.user-account.manager" action="/authenticated/manager/update" access="hasRole('Manager')"/>	
			<acme:menu-suboption code="master.menu.user-account.become-client" action="/authenticated/client/create" access="!hasRole('Client')"/>
			<acme:menu-suboption code="master.menu.user-account.client" action="/authenticated/client/update" access="hasRole('Client')"/>		
		</acme:menu-option>
		

		<acme:menu-option code="master.menu.sign-out" action="/authenticated/system/sign-out" access="isAuthenticated()"/>
	</acme:menu-right>
</acme:menu-bar>


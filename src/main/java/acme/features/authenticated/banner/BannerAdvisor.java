/*
 * AdvertisementAdvisor.java
 *
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.banner;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import acme.client.helpers.MomentHelper;
import acme.entities.groupal.Banner;

@ControllerAdvice
public class BannerAdvisor {

	@Autowired
	private BannerRepository repository;


	@ModelAttribute("banner")
	public Banner getBanner() {
		Banner result;
		Date currentMoment = MomentHelper.getCurrentMoment();
		try {
			result = this.repository.findRandomBanner(currentMoment);
		} catch (final Throwable oops) {
			result = null;
		}

		return result;
	}

}

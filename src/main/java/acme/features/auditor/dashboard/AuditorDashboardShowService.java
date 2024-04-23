package acme.features.auditor.dashboard;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S5.AuditRecord;
import acme.features.auditor.auditRecord.AuditorAuditRecordRepository;
import acme.features.auditor.codeAudit.AuditorCodeAuditRepository;
import acme.forms.AuditorDashboard;
import acme.roles.Auditor;

@Service
public class AuditorDashboardShowService extends AbstractService<Auditor, AuditorDashboard>{

	@Autowired
	AuditorDashboardRepository		repo;
	
	@Autowired
	AuditorCodeAuditRepository		codeAuditRepo;

	@Autowired
	AuditorAuditRecordRepository	auditRecordRepo;
	
	@Override
	public void authorise() {

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		AuditorDashboard dashboard;
		Principal principal;
		Collection<Long> auditRecordCount;
		Collection<AuditRecord> auditRecords;
		List<Long> listOfPeriodInAuditRecord;

		int totalStaticCodeAudits;
		int totalDinamicCodeAudits;

		Double averageAuditRecord;
		Double deviationAuditRecord;
		Integer minimumAuditRecord;
		Integer maximumAuditRecord;

		Double averageTimeOfPeriodInAuditRecord;
		Double deviationTimeOfPeriodInAuditRecord;
		Integer minimumTimeOfPeriodInAuditRecord;
		Integer maximumTimeOfPeriodInAuditRecord;

		principal = super.getRequest().getPrincipal();

		auditRecordCount = this.repo.findNumberOfAuditRecordByAuditorId(principal.getActiveRoleId());
		auditRecords = this.auditRecordRepo.findAllByAuditorId(principal.getActiveRoleId());
		listOfPeriodInAuditRecord = auditRecords.stream().map(auditRecord -> auditRecord.getAuditionPeriod()).toList();

		totalStaticCodeAudits = this.repo.totalStaticCodeAudits();
		totalDinamicCodeAudits = this.repo.totalDinamicCodeAudits();

		averageAuditRecord = this.calculateAverage(auditRecordCount);
		deviationAuditRecord = this.calculateStandardDeviation(auditRecordCount);
		minimumAuditRecord = this.calculateMinimum(auditRecordCount);
		maximumAuditRecord = this.calculateMaximum(auditRecordCount);

		averageTimeOfPeriodInAuditRecord = this.calculateAverage(listOfPeriodInAuditRecord);
		deviationTimeOfPeriodInAuditRecord = this.calculateStandardDeviation(listOfPeriodInAuditRecord);
		minimumTimeOfPeriodInAuditRecord = this.calculateMinimum(listOfPeriodInAuditRecord);
		maximumTimeOfPeriodInAuditRecord = this.calculateMaximum(listOfPeriodInAuditRecord);

		dashboard = new AuditorDashboard();

		dashboard.setTotalNumberDinamicAudits(totalDinamicCodeAudits);
		dashboard.setTotalNumberStaticAudits(totalStaticCodeAudits);
		
		dashboard.setMinNumberRecords(minimumAuditRecord);
		dashboard.setMaxNumberRecords(maximumAuditRecord);
		dashboard.setAverageNumberRecords(averageAuditRecord);
		dashboard.setDeviationNumberRecords(deviationAuditRecord);
				
		dashboard.setMinPeriodRecord(minimumTimeOfPeriodInAuditRecord);
		dashboard.setMaxPeriodRecord(maximumTimeOfPeriodInAuditRecord);
		dashboard.setAveragePeriodRecord(averageTimeOfPeriodInAuditRecord);
		dashboard.setDeviationPeriodRecord(deviationTimeOfPeriodInAuditRecord);
		
		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final AuditorDashboard object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object,	//
			"totalStaticCodeAudits", "totalDynamicCodeAudits", "averageAuditRecord", "deviationAuditRecord", //
			"minimumAuditRecord", "maximumAuditRecord", "averageTimeOfPeriodInAuditRecord", //
			"deviationTimeOfPeriodInAuditRecord", "minimumTimeOfPeriodInAuditRecord", "maximumTimeOfPeriodInAuditRecord");

		super.getResponse().addData(dataset);
	}
	
	
	// METODOS AUXILIARES

	private Double calculateStandardDeviation(final Collection<Long> counts) {
		if (counts.isEmpty())
			return 0.0;
		double mean = this.calculateAverage(counts);
		double temp = 0;
		for (Long count : counts)
			temp += (count - mean) * (count - mean);
		return Math.sqrt(temp / counts.size());
	}

	private Integer calculateMinimum(final Collection<Long> counts) {
		if (counts.isEmpty())
			return 0;
		return counts.stream().min(Long::compare).orElse(0L).intValue();
	}

	private Integer calculateMaximum(final Collection<Long> counts) {
		if (counts.isEmpty())
			return 0;
		return counts.stream().max(Long::compare).orElse(0L).intValue();
	}

	private Double calculateAverage(final Collection<Long> counts) {
		if (counts.isEmpty())
			return 0.0;
		double sum = 0.0;
		for (Long count : counts)
			sum += count;
		return sum / counts.size();
	}
}

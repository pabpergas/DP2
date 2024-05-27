
package acme.features.developer.trainingModules;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.S3.Training;
import acme.roles.Developer;

@Controller
public class DeveloperTrainingModuleController extends AbstractController<Developer, Training> {

	@Autowired
	private DeveloperTrainingModuleCreateService	createService;

	@Autowired
	private DeveloperTrainingModuleDeleteService	deleteService;

	@Autowired
	private DeveloperTrainingModuleListMineService	listMineService;

	@Autowired
	private DeveloperTrainingModulePublishService	publishService;

	@Autowired
	private DeveloperTrainingModuleShowService		showService;

	@Autowired
	private DeveloperTrainingModuleUpdateService	updateService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);
		super.addCustomCommand("list-mine", "list", this.listMineService);
		super.addCustomCommand("publish", "update", this.publishService);
		super.addBasicCommand("show", this.showService);
	}

}

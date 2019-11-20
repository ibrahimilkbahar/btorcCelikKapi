package tr.com.mindworks.controllers.proposal;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Getter;
import lombok.Setter;
import tr.com.mindworks.annotations.SpringFlowScoped;
import tr.com.mindworks.controllers.BaseController;
import tr.com.mindworks.model.TProposal;
import tr.com.mindworks.services.ProposalService;

@Component("proposalListController")
@SpringFlowScoped
@SessionScope
public class ProposalListController extends BaseController {
	@Autowired
	private Provider<ProposalService> proposalService;

	@Getter
	@Setter
	private List<TProposal> proposalList;

	@PostConstruct
	public void initialize() {
		proposalList = proposalService.get().findAll();

	}
}

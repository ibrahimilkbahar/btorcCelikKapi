package tr.com.mindworks.services;

import java.util.List;

import tr.com.mindworks.model.TProposal;

public interface ProposalService
{

	List<TProposal> findAll();
	TProposal findProposalById(int proposalId);
	void saveProposal(TProposal proposal);
	List<TProposal> findAllApproveList();

	
 
 
	 


}

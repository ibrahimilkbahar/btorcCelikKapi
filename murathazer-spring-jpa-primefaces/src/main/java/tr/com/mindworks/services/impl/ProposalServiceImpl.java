package tr.com.mindworks.services.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.com.mindworks.dao.ProposalRepository;
import tr.com.mindworks.model.TProposal;
import tr.com.mindworks.services.ProposalService;

@Service("proposalService")
public class ProposalServiceImpl implements ProposalService, Serializable {

	@Autowired
	private ProposalRepository proposalRepository;
   
    
	@Override
	public List<TProposal> findAll() {
		return proposalRepository.findAll();
	}
	@Override
	public TProposal findProposalById(int proposalId) {
		return proposalRepository.findOne(proposalId);
	}
	@Override
	public void saveProposal(TProposal proposal) {
		proposalRepository.save(proposal);
	}
	@Override
	public List<TProposal> findAllApproveList() {
		return proposalRepository.findAllApproveList();
	}
	

	
}

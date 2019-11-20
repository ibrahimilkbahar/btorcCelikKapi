package tr.com.mindworks.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tr.com.mindworks.model.TProposalSEQ;

@Repository
public interface ProposalSEQRepository extends JpaRepository<TProposalSEQ, Integer>
{
	
}

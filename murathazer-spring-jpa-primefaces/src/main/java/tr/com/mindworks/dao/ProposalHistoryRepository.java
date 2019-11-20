package tr.com.mindworks.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tr.com.mindworks.model.TProposalHistory;

@Repository
public interface ProposalHistoryRepository extends JpaRepository<TProposalHistory, Integer>
{
	
}

package tr.com.mindworks.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tr.com.mindworks.model.TProposal;
import tr.com.mindworks.model.TProposalHistory;

@Repository
public interface ProposalRepository extends JpaRepository<TProposal, Integer>
{
	@Query("SELECT p FROM TProposal p")
    public List<TProposal> findAllProposal();
	
	@Query("SELECT p FROM TProposal p WHERE p.order.id =:orderId")
	public TProposal findByOrderId(@Param("orderId") Integer orderId);

	@Query("SELECT p FROM TProposalHistory p WHERE p.proposal.id =:proposalId order by p.updateDate desc")
	public  List<TProposalHistory>  findAllProposalHistory(@Param("proposalId") Integer proposalId);

	@Query("SELECT p FROM TProposal p where p.order.orderStatus.code = 'SUBMITTED'")
    public List<TProposal> findAllApproveList();
}

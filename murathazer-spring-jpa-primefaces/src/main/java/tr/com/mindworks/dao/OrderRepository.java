package tr.com.mindworks.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tr.com.mindworks.model.TOrder;
import tr.com.mindworks.model.TOrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<TOrder, Integer>
{
	@Query("SELECT p FROM TOrderStatus p")
    public List<TOrderStatus> findAllOrderStatus();
	
	@Query("SELECT p FROM TOrder p WHERE p.id =:orderId")
    public TOrder findOrderById(@Param("orderId") Integer orderId);


	
	@Query("SELECT p FROM TOrder p WHERE p.orderStatus.id =:orderStatusId")
    public  List<TOrder> findAllOrdersByStatusId(@Param("orderStatusId") Integer orderStatusId);


}

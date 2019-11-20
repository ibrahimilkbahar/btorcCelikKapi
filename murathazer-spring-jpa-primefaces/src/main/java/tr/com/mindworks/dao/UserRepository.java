package tr.com.mindworks.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tr.com.mindworks.model.TScreenInfo;
import tr.com.mindworks.model.TUser;

@Repository
public interface UserRepository extends JpaRepository<TUser, String>
{
	@Query("SELECT p FROM TUser p WHERE p.loginName = :loginName")
    public TUser findByLoginName(@Param("loginName") String loginName);

	@Query("SELECT p FROM TUser p WHERE p.id = :id")
    public TUser findById(@Param("id") Integer id);
	
	@Query("SELECT p FROM TScreenInfo p")
    public List<TScreenInfo> findAllScreenInfo();
}

package tr.com.mindworks.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tr.com.mindworks.model.TPropertyGroup;

@Repository
public interface PropertyGroupRepository extends JpaRepository<TPropertyGroup, Integer>
{

}

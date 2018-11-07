package th.co.rocinante.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import th.co.rocinante.entity.Param;

@Repository
public interface ParamRepository extends JpaRepository<Param, Long> {
	
	List<Param> findByGroup(@org.springframework.data.repository.query.Param(value = "group") String group);
	
	Param findByGroupAndCode(@org.springframework.data.repository.query.Param(value = "group") String group, @org.springframework.data.repository.query.Param(value = "code") String code);

}

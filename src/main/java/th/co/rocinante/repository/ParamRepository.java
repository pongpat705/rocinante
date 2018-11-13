//package th.co.rocinante.repository;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import th.co.rocinante.entity.ParamApp;
//
//@Repository
//public interface ParamRepository extends JpaRepository<ParamApp, Long> {
//	
//	List<ParamApp> findByGroupCode(@org.springframework.data.repository.query.Param(value = "groupCode") String groupCode);
//	
//	ParamApp findByGroupCodeAndCode(@org.springframework.data.repository.query.Param(value = "groupCode") String groupCode, @org.springframework.data.repository.query.Param(value = "code") String code);
//
//}

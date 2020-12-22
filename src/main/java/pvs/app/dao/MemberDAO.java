package pvs.app.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pvs.app.entity.Member;

import java.util.List;

@Repository
public interface MemberDAO extends CrudRepository<Member, Long> {
    Member findByAccount(String account);
    Member findById(long id);
}

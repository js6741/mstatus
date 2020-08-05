package playground;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatusRepository extends CrudRepository<Status, Long> {

    List<Status> findByBookId(Long bookId);
    List<Status> findByGroundId(Long bookId);
    void deleteByGroundId(Long groundId);
}
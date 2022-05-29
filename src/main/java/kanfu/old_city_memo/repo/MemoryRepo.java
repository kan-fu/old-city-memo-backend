package kanfu.old_city_memo.repo;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import kanfu.old_city_memo.model.Memory;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public interface MemoryRepo extends MongoRepository<Memory, String> {
    List<Memory> findByUsername(@Param("username") String username);

    // @PreAuthorize("hasRole('ROLE_USER')")
    // @Override
    // <S extends Memory> S save(S memory);

    // @PreAuthorize("hasRole('ROLE_USER') and findById(#id).username==authentication.name")
    // @Override
    // void deleteById(String id);

    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    // @Override
    // void delete(Memory memory);
}


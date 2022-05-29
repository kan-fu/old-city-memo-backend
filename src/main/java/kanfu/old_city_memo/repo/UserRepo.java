package kanfu.old_city_memo.repo;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import kanfu.old_city_memo.model.User;

public interface UserRepo extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
} 


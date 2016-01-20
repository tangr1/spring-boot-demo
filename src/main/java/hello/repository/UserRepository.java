package hello.repository;

import hello.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface UserRepository extends CrudRepository<User, Long> {
    User findByLogin(String login);
}

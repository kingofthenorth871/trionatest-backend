package trionabackend.trionatestbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trionabackend.trionatestbackend.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
}
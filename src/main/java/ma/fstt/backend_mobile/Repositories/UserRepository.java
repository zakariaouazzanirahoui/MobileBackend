package ma.fstt.backend_mobile.Repositories;

import ma.fstt.backend_mobile.Models.User_Infos;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User_Infos, String> {

    @Query("SELECT COUNT(u) > 0 FROM User_Infos u WHERE u.email = ?1")
    boolean existsByEmail(String email);
}

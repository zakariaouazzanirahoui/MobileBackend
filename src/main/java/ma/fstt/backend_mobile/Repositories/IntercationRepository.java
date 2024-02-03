package ma.fstt.backend_mobile.Repositories;


import ma.fstt.backend_mobile.Models.Interaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntercationRepository extends CrudRepository<Interaction, Long> {

    @Query("SELECT i FROM Interaction  i WHERE i.device_udid = ?1 AND i.interaction_device_udid = ?2")
    Interaction interactionExists(String udid1, String udid2);


    @Query("SELECT u FROM Interaction u WHERE u.device_udid = ?1")
    List<Interaction> getListOfInteractions(String udid );
    @Query("SELECT u FROM Interaction u WHERE u.device_udid = ?1")
    Interaction findByDevice_udid(String udid1);


}

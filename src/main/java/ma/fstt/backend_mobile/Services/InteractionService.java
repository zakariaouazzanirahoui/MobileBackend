package ma.fstt.backend_mobile.Services;

import ma.fstt.backend_mobile.Models.Interaction;
import ma.fstt.backend_mobile.Repositories.IntercationRepository;
import org.springframework.stereotype.Service;

@Service
public class InteractionService {

    private final  IntercationRepository intercationRepository ;

    public InteractionService(IntercationRepository intercationRepository) {
        this.intercationRepository = intercationRepository;
    }
    public Interaction getInteraction (String udid, String udid2){
            return this.intercationRepository.interactionExists(udid , udid2);
    }

    public Interaction saveInteraction(Interaction interaction){
        return this.intercationRepository.save(interaction);
    }


}

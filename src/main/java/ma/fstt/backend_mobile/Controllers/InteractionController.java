package ma.fstt.backend_mobile.Controllers;

import ma.fstt.backend_mobile.Models.Interaction;
import ma.fstt.backend_mobile.Models.User_Infos;
import ma.fstt.backend_mobile.Repositories.IntercationRepository;
import ma.fstt.backend_mobile.Services.InteractionService;
import ma.fstt.backend_mobile.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/interaction")

public class InteractionController {

    private final InteractionService interactionService;
    private final UserService userService;
    private final IntercationRepository intercationRepository ;
    public InteractionController(InteractionService interactionService, UserService userService, IntercationRepository intercationRepository) {
        this.interactionService = interactionService;
        this.userService = userService;

        this.intercationRepository = intercationRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveInteraction(@RequestParam Map<String, String> body) {
        try {
            String device_udid = body.get("device_udid");
            String interaction_device_udid = body.get("interaction_device_udid");
            Interaction existingInteraction = interactionService.getInteraction(device_udid, interaction_device_udid);

            if (existingInteraction!= null) {
                Optional<User_Infos> user1 = this.userService.getUser(device_udid);
                Optional<User_Infos> user2 = this.userService.getUser(interaction_device_udid);

                if (!user1.get().isFlag() && !user2.get().isFlag()){
                    existingInteraction.setSafe(true);

                }else {
                    existingInteraction.setSafe(false);

                }
                existingInteraction.setInteraction_date(new Date()); // Update the date as needed

                interactionService.saveInteraction(existingInteraction);
                return new ResponseEntity<>("Interaction already exists , and date changed ", HttpStatus.INTERNAL_SERVER_ERROR);

            }



            Optional<User_Infos> user1 = this.userService.getUser(device_udid);
            Optional<User_Infos> user2 = this.userService.getUser(interaction_device_udid);


            // Save user information
            Interaction new_Interaction = new Interaction();  // Create a new instance
            new_Interaction.setDevice_udid(device_udid);  // Set the udid
            new_Interaction.setInteraction_device_udid(interaction_device_udid);
            new_Interaction.setInteraction_date(new Date());
            if (!user1.get().isFlag() && !user2.get().isFlag()){
                new_Interaction.setSafe(true);

            }else {
                new_Interaction.setSafe(false);

            }
            interactionService.saveInteraction(new_Interaction);



            return new ResponseEntity<>("Interaction information saved successfully", HttpStatus.OK);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list/{udid}")
    public ResponseEntity<?> getInteracted (@PathVariable  String udid){

        try {
            Optional<User_Infos> userOptional = this.userService.getUser(udid);

            if (userOptional.isPresent()) {
               List<Interaction> list=  intercationRepository.getListOfInteractions(udid);
                for (Interaction interaction : list) {
                    String interactionDeviceUdid = interaction.getInteraction_device_udid();

                    // Call getUser method to get the user based on interaction_device_udid
                    Optional<User_Infos> user2 = userService.getUser(interactionDeviceUdid);
                    System.out.println(user2);

                    // Check the flag of the user
                    if (!user2.get().isFlag() && !userOptional.get().isFlag()) {
                        // Do something if the flag is true
                        interaction.setSafe(true);
                        interactionService.saveInteraction(interaction) ;

                        System.out.println("Interaction is safe for user with interaction_device_udid: " + interactionDeviceUdid);
                    } else  {
                        interaction.setSafe(false);
                        interactionService.saveInteraction(interaction) ;

                        System.out.println("Interaction is not safe for user with interaction_device_udid: " + interactionDeviceUdid);
                    }
                }

                List<Interaction> new_list=  intercationRepository.getListOfInteractions(udid);

                // Save the updated user information

                return new ResponseEntity<>(new_list, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No Interactions", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}

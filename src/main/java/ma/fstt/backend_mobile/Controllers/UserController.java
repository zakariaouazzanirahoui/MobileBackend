package ma.fstt.backend_mobile.Controllers;


import ma.fstt.backend_mobile.Models.User_Infos;
import ma.fstt.backend_mobile.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveUserInfos(@RequestParam Map<String, String> body) {
        try {
            String udid = body.get("udid");
            String firstName = body.get("first_name");
            String lastName = body.get("last_name");
            String email = body.get("email");

            // Check if email already exists
            if (userService.emailExists(email)) {
                return new ResponseEntity<>("Email already exists", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            // Save user information
            User_Infos new_user = new User_Infos();  // Create a new instance
            new_user.setUDID(udid);  // Set the udid
            new_user.setFirst_name(firstName);
            new_user.setLast_name(lastName);
            new_user.setEmail(email);

            userService.saveUserInfos(new_user);  // Persist the user

            return new ResponseEntity<>("User information saved successfully", HttpStatus.OK);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/changeFlag/{udid}")
    public ResponseEntity<String> changeFlag(@PathVariable String udid) {
        try {
            Optional<User_Infos> userOptional = this.userService.getUser(udid);

            if (userOptional.isPresent()) {
                User_Infos user = userOptional.get();
                user.setFlag(!user.isFlag());

                // Save the updated user information
                this.userService.saveUserInfos(user);

                return new ResponseEntity<>("Flag changed successfully to "+ user.isFlag(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getUser/{udid}")
    public ResponseEntity<?> getUser(@PathVariable String udid){
        try {
            Optional<User_Infos> userOptional = this.userService.getUser(udid);

            if (userOptional.isPresent()) {


                return new ResponseEntity<>(userOptional, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/check-udid/{udid}")
    public ResponseEntity<String> checkUdidExistence(@PathVariable String udid) {
        boolean udidExists = userService.checkUdidExistence(udid);

        if (udidExists) {
            return ResponseEntity.ok("UDID exists");
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}

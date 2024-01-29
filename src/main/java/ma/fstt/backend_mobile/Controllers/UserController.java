package ma.fstt.backend_mobile.Controllers;


import ma.fstt.backend_mobile.Models.User_Infos;
import ma.fstt.backend_mobile.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveUserInfos(@RequestBody User_Infos user) {
        try {
            // Check if email already exists
            if (userService.emailExists(user.getEmail())) {
                return new ResponseEntity<>("Email already exists", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            // Save user information
            User_Infos new_user = userService.saveUserInfos(new User_Infos(
                    user.getUDID(), user.getFirst_name(),
                    user.getLast_name(), user.getEmail(),
                    user.isFlag()
            ));

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


}

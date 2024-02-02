package ma.fstt.backend_mobile.Services;

import ma.fstt.backend_mobile.Models.User_Infos;
import ma.fstt.backend_mobile.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User_Infos saveUserInfos(User_Infos user){
        return this.userRepository.save(user);
    }
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
    public Optional<User_Infos> getUser (String udid){
        return this.userRepository.findById(udid);
    }

    public boolean checkUdidExistence(String UDID) {
        // Implement your logic to check if the UDID exists in the database
        // This could involve querying your UserRepository or your database directly
        // Return true if the UDID exists, false otherwise
        // For example, assuming UserRepository has a method findByUdid
        return userRepository.findByUDID(UDID).isPresent();
    }



}

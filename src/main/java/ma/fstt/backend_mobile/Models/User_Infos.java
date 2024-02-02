package ma.fstt.backend_mobile.Models;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User_Infos {
    @Id
    private String UDID;
    private String first_name;
    private String last_name;
    @Column(unique = true)
    private String email;

    private boolean flag =true;
    public User_Infos(String udid, String first_name, String last_name, String email) {
        this.UDID = udid;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.flag = false;
    }

}

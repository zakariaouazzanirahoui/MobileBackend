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

    private boolean flag=true;


}

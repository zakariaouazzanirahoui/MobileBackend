package ma.fstt.backend_mobile.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@ToString
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Interaction {

    @Id @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    private String device_udid;
    private String interaction_device_udid;
    private Date interaction_date ;
    private boolean safe = true ;
}

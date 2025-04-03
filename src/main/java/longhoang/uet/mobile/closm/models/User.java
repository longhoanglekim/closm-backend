package longhoang.uet.mobile.closm.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    @Column(unique = true)
    private String email;
    private String password;
    @Column(unique = true)
    private String phone;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;
}

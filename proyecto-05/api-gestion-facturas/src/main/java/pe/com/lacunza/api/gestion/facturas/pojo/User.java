package pe.com.lacunza.api.gestion.facturas.pojo;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email=:email")
@NamedQuery(name = "User.getAllUsers",
        query = "SELECT new pe.com.lacunza.api.gestion.facturas.wrapper.UserWrapper(u.id, u.nombre, u.email, u.numeroDeContacto, u.status) FROM User u WHERE u.role='user'")
@NamedQuery(name = "User.updateStatus", query = "UPDATE User u set u.status=:status WHERE u.id=:id")
@NamedQuery(name = "User.getAllAdmins", query = "SELECT u.email FROM User u WHERE u.role='admin'")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "numeroDeContacto")
    private String numeroDeContacto;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

    @Column(name = "role")
    private String role;
}

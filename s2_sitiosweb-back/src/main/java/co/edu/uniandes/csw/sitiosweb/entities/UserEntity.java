package co.edu.uniandes.csw.sitiosweb.entities;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author Nicolás Abondano nf.abondano 201812467
 */
@Entity
public class UserEntity extends BaseEntity implements Serializable {

    /**
     * Login del usuario
     */
    private String login;
    
    /**
     * Email del usuario
     */
    private String email;

    /**
     * Phone del usuario
     */
    private String phone;

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

}

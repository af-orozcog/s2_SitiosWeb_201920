package co.edu.uniandes.csw.sitiosweb.dtos;

import co.edu.uniandes.csw.sitiosweb.entities.UserEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 
 * @author Nicolás Abondano nf.abondano 201812467
 */
public class UserDTO implements Serializable {

    private Long id;
    private String name;
    private String login;
    private String email;
    private String phone;
    private String image;
    
    /**
     * Constructor por defecto
     */
    public UserDTO() {
        
    }

    /**
     * Constructor a partir de la entidad
     *
     * @param userEntity La entidad del libro
     */
    public UserDTO(UserEntity userEntity) {
        if (userEntity != null) {
            this.id = userEntity.getId();
            this.name = userEntity.getName();
            this.login = userEntity.getLogin();
            this.email = userEntity.getEmail();
            this.phone = userEntity.getPhone();
            this.image = userEntity.getImage();
        }
    }

    /**
     * Método para transformar el DTO a una entidad.
     *
     * @return La entidad del libro asociado.
     */
    public UserEntity toEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(this.getName());
        userEntity.setLogin(this.getLogin());
        userEntity.setEmail(this.getEmail());
        userEntity.setPhone(this.getPhone());
        userEntity.setImage(this.getImage());
        return userEntity;
    }
    
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }
    
    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }
    
            /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}

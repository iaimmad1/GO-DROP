package np.edu.nast.Godrop.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", length = 20, nullable = false)
    private String firstName;

    @Column(name = "middle_name", length = 15, nullable = true)
    private String middleName;

    @Column(name = "last_name", length = 15, nullable = false)
    private String lastName;

    @Column(name = "address", length = 50, nullable = false)
    private String address;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, length = 10, nullable = false)
    private String contact;

    @Column(length = 15, nullable = false)
    private String password;
    
    @Column(length = 15, nullable = true)
    private String Role;



    public Profile() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

  
}

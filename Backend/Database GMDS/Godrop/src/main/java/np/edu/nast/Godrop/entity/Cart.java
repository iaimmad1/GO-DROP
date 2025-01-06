package np.edu.nast.Godrop.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tbl_carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    

    @Column(nullable=false)
    private float total_price;
 
    
    @Column(nullable=true)
    private String item;
    
    @Column(nullable=true)
    private Long quantity;
    
    

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private List<Product> products = new ArrayList<>();

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "customer_id")
//    private Profile customer;
    @ManyToMany
    @JoinTable(
        name = "cart_profile", // Name of the join table
        joinColumns = @JoinColumn(name = "cart_id", referencedColumnName = "id"), // FK from this entity
        inverseJoinColumns = @JoinColumn(name = "profile_id", referencedColumnName = "id") // FK from the other entity
    )
    private List<Profile> profile;


    public Cart() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        
    }

	public List<Profile> getProfile() {
		return profile;
	}

	public void setProfile(List<Profile> profile) {
		this.profile = profile;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	
	 

  }

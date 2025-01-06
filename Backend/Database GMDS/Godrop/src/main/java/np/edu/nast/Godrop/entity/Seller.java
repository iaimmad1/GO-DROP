package np.edu.nast.Godrop.entity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tbl_sellers")

public class Seller {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String ShopName; 
	@Column(nullable=false, unique=true)
	private String ShopContact; 
	
	@Column(nullable=false)
	private String ShopAddress;
	
	@Column(nullable=false)
	private String ShopEmail; 
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Profile profile;
	

	public Seller() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getShopName() {
		return ShopName;
	}

	public void setShopName(String shopName) {
		ShopName = shopName;
	}

	public String getShopAddress() {
		return ShopAddress;
	}

	public void setShopAddress(String shopAddress) {
		ShopAddress = shopAddress;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public String getShopContact() {
		return ShopContact;
	}

	public void setShopContact(String shopContact) {
		ShopContact = shopContact;
	}

	public String getShopEmail() {
		return ShopEmail;
	}

	public void setShopEmail(String shopEmail) {
		ShopEmail = shopEmail;
	}

	

	
	
}
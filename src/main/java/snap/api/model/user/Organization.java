package snap.api.model.user;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snap.api.enums.TypeUserEnum;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Organization {
	
	private String id;
	
	private Date updatedAt;
	
	private Date createdAt;
	
	private String name;
	
	private String addressLine1;
	
	private String locality;
	
	private String administrativeDistrictLevel1;
	
	private String country;
	
	private String postalCode;
	
	private TypeUserEnum type;
    
}// Organization

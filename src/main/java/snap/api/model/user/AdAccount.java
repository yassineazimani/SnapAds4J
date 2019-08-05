package snap.api.model.user;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snap.api.enums.CurrencyEnum;
import snap.api.enums.StatusEnum;
import snap.api.enums.TypeUserEnum;

@Getter
@Setter
@ToString
public class AdAccount {
	
	private String id;
	
    private Date updatedAt;
    
    private Date createdAt;
    
    private String name;
	
    private TypeUserEnum type;
    
    private StatusEnum status;
    
    private CurrencyEnum currency;
    
    private String timezone;
    
    private List<String> roles;
    
}// AdAccount

package model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Singleton
@Startup
public class DataContainer {

	Logger log = Logger.getLogger("DataContainer");
	
	List<UserModel> userList;
	
    @PersistenceContext
    EntityManager em;


	@PostConstruct
	public void init() {		
		userList = new ArrayList<>();
		TypedQuery<UserModel> query = em.createNamedQuery("Users.list", UserModel.class);
		  List<UserModel> results = query.getResultList();
		  if(results != null)
			  userList = results;
	}

	public UserModel checkUser(UserModel user){
		for(UserModel u: userList){
			if(u.getLogin().equals(user.getLogin()) && u.getPassword().equals(user.getPassword())){	
				log.info("User found: " + u);
				return u;
			}			
		}
		return null;
	}
}

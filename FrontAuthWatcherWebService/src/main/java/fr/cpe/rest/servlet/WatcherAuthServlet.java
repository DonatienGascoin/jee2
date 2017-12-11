package fr.cpe.rest.servlet;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dto.UserLoginDto;
import dto.UserResponseDto;
import ejb.MessageReceiverSyncLocal;
import ejb.MessageSenderLocal;
import model.UserModel;


@Path("/WatcherAuth")
public class WatcherAuthServlet {

	Logger log = Logger.getLogger("WatcherAuthServlet");
	
	@Inject
	MessageSenderLocal sender;
	
	@Inject
	MessageReceiverSyncLocal receiver;
	
	
	@GET
	@Path("/hello")
	public String helloWorld(){
		return "Hello!";
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/authUser")
	public UserResponseDto doPost(UserLoginDto userLogin){
		if(userLogin == null){
			return null;
		}
		UserResponseDto ret = null;
		
		if(userLogin.getLogin() != null && userLogin.getPassword() != null){
			UserModel user = new UserModel(userLogin.getLogin(), userLogin.getPassword());
			sender.sendMessage(user);
			
			UserModel userR = receiver.receiveMessage();
			if (userR != null){
				ret = new UserResponseDto(userR.getLogin(), userR.getPassword(), userR.getRole(), userR.isValidAuth());
			}
		}else{
			log.warning("Login and password au mauvais format");
		}
		
		return ret;
	}
}
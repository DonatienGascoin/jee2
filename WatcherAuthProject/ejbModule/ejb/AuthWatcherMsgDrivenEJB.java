package ejb;
import java.util.Date;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.jboss.logging.Logger;

import model.DataContainer;
//import model.Role;
import model.UserModel;

@MessageDriven(
		activationConfig = {
				@ActivationConfigProperty(
						propertyName = "destinationType",
						propertyValue = "javax.jms.Topic"),
				@ActivationConfigProperty(
						propertyName = "destination",
						propertyValue = "java:/jms/watcherAuthJMS")
		})
public class AuthWatcherMsgDrivenEJB implements MessageListener {

	Logger log = Logger.getLogger("AuthWatcherMsgDrivenEJB");
	
	@Inject
	private DataContainer dataContainer;
	
	@EJB 
	MessageSenderQueueLocal sender;
	
	public void onMessage(Message message) {
		try {
			if (message instanceof TextMessage) {
				log.info("Topic: I received a TextMessage at "+ new Date());
				TextMessage msg = (TextMessage) message;
				System.out.println("Message is : " + msg.getText());
			} else if (message instanceof ObjectMessage) {
				
				log.info("Topic: I received an ObjectMessage at " + new Date());
				
				ObjectMessage msg = (ObjectMessage) message;
				
				if( msg.getObject() instanceof UserModel){
					
					UserModel user=(UserModel)msg.getObject();
					log.info("User Details: ");
					log.info("login:"+user.getLogin());
					UserModel tmp = dataContainer.checkUser(user);
					if(tmp != null){
						user = tmp;
					}
					log.info("Second user: " + user);
					sender.sendMessage(user);
				}
			} else {
				System.out.println("Not valid message for this Queue MDB");
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
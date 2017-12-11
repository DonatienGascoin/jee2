package ejb;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;

import org.jboss.logging.Logger;

import model.UserModel;

@Stateless
public class MessageReceiverSync implements MessageReceiverSyncLocal {
	
	Logger log = Logger.getLogger("MessageReceiverSync");
	
	@Inject
	JMSContext context;

	@Resource(mappedName = "java:/jms/queue/watcherqueue")
	Queue queue;
	
	@Override
	public UserModel receiveMessage() {
		UserModel receivedUser = new UserModel();
		boolean isResult = false;
		JMSConsumer consumer = context.createConsumer(queue);
		while (!isResult) {
		    Message m = consumer.receive(1000);
		    if (m != null) { 
		        if (m instanceof TextMessage) { 
		            try {
						System.out.println(
						        "Reading message: " + m.getBody(String.class));
					} catch (JMSException e) {
						log.error("[MR] Impossible to read message",e);
					}
		        } else if(m instanceof ObjectMessage){
		        	ObjectMessage msg = (ObjectMessage) m;
					try {
						if( msg.getObject() instanceof UserModel){
							receivedUser = (UserModel) msg.getObject();
							if(receivedUser.getLogin() != null && receivedUser.getPassword() != null){		
								log.info("result: " + receivedUser);
								isResult = true;
							}
						}
					} catch (JMSException e) {
						log.error("Error trying reading ObjectMessage");
						e.printStackTrace();
					}
		        }else { 
		            break; 
		        } 
		    }
		}
		isResult = false;
		log.info("ReceivedUser: " + receivedUser);

		return receivedUser;
	}
}
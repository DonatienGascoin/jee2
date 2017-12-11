package ejb;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;

import org.jboss.logging.Logger;

import model.UserModel;

/**
 * Session Bean implementation class MessageSenderQueue
 */
@Stateless
public class MessageSenderQueue implements MessageSenderQueueLocal {
	
	Logger log = Logger.getLogger("MessageSenderQueue");	
	
	@Inject
	JMSContext context;
	
	@Resource(mappedName = "java:/jms/queue/watcherqueue")
	Queue queue;
	
	public void sendMessage(String message) {
		context.createProducer().send(queue, message);
	}
	
	public void sendMessage(UserModel user) {
		try {
			log.info("[MSQL] " + user);
			ObjectMessage message = context.createObjectMessage();
			message.setObject(user);
			context.createProducer().send(queue, user);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}	
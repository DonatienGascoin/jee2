package ejb;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSRuntimeException;
import javax.jms.Topic;

import org.jboss.logging.Logger;

import model.UserModel;

@Stateless
public class MessageSender implements MessageSenderLocal {

	Logger log = Logger.getLogger("MessageSender");
	
	@Inject
	JMSContext context;

	@Resource(mappedName = "java:/jms/watcherAuthJMS")
	Topic topic;
	
	@Override
	public void sendMessage(String message) {
		try{
			context.createProducer().send(topic, message);
		} catch (JMSRuntimeException ex) {
			log.error("[MS] Eror during sending message", ex);
		}
	}

	@Override
	public void sendMessage(UserModel user) {
		try{
			context.createProducer().send(topic, user);
		} catch (JMSRuntimeException ex) {
			log.error("[MS] Eror during sending user", ex);
		}
	}
}
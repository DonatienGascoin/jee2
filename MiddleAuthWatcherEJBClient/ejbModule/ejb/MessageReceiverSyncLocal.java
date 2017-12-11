package ejb;

import javax.ejb.Local;

import model.UserModel;

@Local
public interface MessageReceiverSyncLocal {
	public UserModel receiveMessage();
}

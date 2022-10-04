import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClientBox extends Remote {
	void stream(byte[] chunck) throws RemoteException;
}
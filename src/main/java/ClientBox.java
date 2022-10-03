import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

public class ClientBox extends UnicastRemoteObject implements Serializable,IClientBox {
    protected ClientBox() throws RemoteException {
    }

    @Override
    public void stream(byte[] chunck) throws RemoteException {
        System.out.println(Arrays.toString(chunck));
    }
}

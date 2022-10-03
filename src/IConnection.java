import java.rmi.Remote;
import java.rmi.RemoteException;

import exceptions.InvalidCredentialsException;
import exceptions.SignInFailed;

public interface IConnection extends Remote {
    boolean signIn(String mail,String pwd) throws SignInFailed, RemoteException;

    IVODService login(String mail,String pwd) throws InvalidCredentialsException, RemoteException;
}

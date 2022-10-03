import exceptions.InvalidCredentialsException;
import exceptions.SignInFailed;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IConnection extends Remote {
    boolean signIn(String mail,String pwd) throws SignInFailed, RemoteException;

    IVODService login(String mail,String pwd) throws InvalidCredentialsException, RemoteException;
}

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IVODService extends Remote {
    List<MovieDesc> viewCatalog() throws RemoteException;

    Bill playmovie(String isbn, IClientBox box) throws RemoteException;
}

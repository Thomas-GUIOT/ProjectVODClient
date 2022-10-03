
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

import exceptions.InvalidCredentialsException;

public class Main {

    public static void main(String[] args) {

    	try {
			Registry registry = LocateRegistry.getRegistry(2001);
	    	IConnection iConnection = (IConnection) registry.lookup("connectionVOD");
	    	
	    	Scanner scanner = new Scanner(System.in);

	    	
	    	
	    	System.out.println("Hello, please identify yourself.");
	    	System.out.println("If you want to register first type -> 1");
	    	System.out.println("If you want to login type anything");
		    	
	    	if(scanner.nextInt() == 1) {
	    		register(scanner);
	    	}
	    	
	    	boolean notFinished = true;
	    	String username;
	    	String password;
	    	IVODService ivodService = null;
	    	while(notFinished) {
	    		try {
	    			System.out.println("Enter your username :");
		    		username = scanner.next();
		    		
		    		System.out.println("Enter your password :");
		    		password = scanner.next();
		    		
		    		ivodService = iConnection.login(username, password);
		    		
		    		notFinished = false;
	    		} catch(InvalidCredentialsException e) {
					e.printStackTrace();
	    		}
	    	}
	    	
	    	List<MovieDesc> movies = ivodService.viewCatalog();
			System.out.println(movies.toString());

			Bill bill =	ivodService.playmovie("9781234567897",new ClientBox());
			System.out.println(bill.toString());
	    	scanner.close();
    	} catch (RemoteException | NotBoundException | MalformedURLException e) {
			e.printStackTrace();
		}
    }
    
    private static void register(Scanner scanner) {
    	String username;
    	String password;
    	boolean notFinished = true;
    	while(notFinished) {

    	}
    }
}

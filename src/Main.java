

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import exceptions.InvalidCredentialsException;

public class Main implements IClientBox {

    public static void main(String[] args) {

    	try {
			Registry registry = LocateRegistry.getRegistry(2001);
	    	IConnection iConnection = (IConnection) registry.lookup("JSP");
	    	
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
	    			
	    		}
	    	}
	    	
	    	ivodService.viewCatalog();
	    	
	    	scanner.close();
    	} catch (RemoteException | NotBoundException e) {
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

    
    
	@Override
	public void stream(byte[] chunck) {
		// TODO Auto-generated method stub
		
	}
}

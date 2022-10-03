import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import exceptions.InvalidCredentialsException;
import exceptions.SignInFailed;

public class Client {

    public static void main(String[] args) {
    	try {
			Registry registry = LocateRegistry.getRegistry(2001);
	    	IConnection iConnection = (IConnection) registry.lookup("JSP");
	    	
	    	Scanner scanner = new Scanner(System.in);

	    	loginRegisterMenu(scanner, iConnection);
	    	
	    	scanner.close();
    	} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
    }
    
    private static void loginRegisterMenu(Scanner scanner, IConnection iConnection) {
    	System.out.println("Hello, welcome on the VODProject.");
    	while(true) {
    		System.out.println("Choose what you want to do :");
    		System.out.println("- If you want to register first type -> register");
    		System.out.println("- If you want to leave type -> leave");
	    	System.out.println("- If you want to login type anything");
	    	
	    	String input = scanner.next();
	    	if(input.equals("leave")) {
	    		System.out.println("Goodbye thanks you for visiting our project.");
	    		break;
	    	}
	    	else if(input.equals("register")) {
	    		register(scanner, iConnection);
    		}
	    	else {
	    		login(scanner, iConnection);
	    	}
    	}
    	
    	
    }

	private static boolean register(Scanner scanner, IConnection iConnection) {
    	String email;
    	String password;
    	while(true) {
    		try {
        		System.out.println("Enter an email :");
        		email = scanner.next();
        		
        		System.out.println("Enter a password :");
        		password = scanner.next();
        		
				iConnection.signIn(email, password);
				
				System.out.println("Registration successful !");
				System.out.println("You can now login.");
				return true;
			} catch (RemoteException | SignInFailed e) {
				System.err.println("An error occured during the registration : " + e.getMessage());
				System.out.println("- If you want to try again type -> again");
				System.out.println("- If you want to go back to the main menu type anything.");
				if(!scanner.next().equals("again")) {
					return false;
				}
			}
    	}
    }

    private static boolean login(Scanner scanner, IConnection iConnection) {
    	String email;
    	String password;
    	while(true) {
    		try {
	    		System.out.println("Enter your email :");
	    		email = scanner.next();
	    		
	    		System.out.println("Enter your password :");
	    		password = scanner.next();
	    		
				IVODService ivodService = iConnection.login(email, password);
				
				System.out.println("Login successful !");
				System.out.println("You have gained access to VODProject.");
				
				mainMenu(scanner, ivodService);
				System.out.println("Disconnection successful.");
				return true;
			} catch (RemoteException | InvalidCredentialsException e) {
				System.err.println("An error occured during the login : " + e.getMessage());
				System.out.println("- If you want to try again type -> again");
				System.out.println("- If you want to go back to the main menu type anything.");
				if(!scanner.next().equals("again")) {
					return false;
				}
				e.printStackTrace();
			}
    	}
	}
    
    private static void mainMenu(Scanner scanner, IVODService ivodService) throws RemoteException {
    	String input;
    	while(true) {
    		System.out.println("Choose what you want to do :");
    		System.out.println("- If you want to leave type -> leave");
    		System.out.println("- If you want to see the movie catalog type -> catalog");
    		System.out.println("- If you want to play a movie type -> play <ISBN>");
    		
    		input = scanner.next();
    		
    		if(input.equals("leave")) {
    			return;
    		}
    		else if(input.equals("catalog")) {
    			showCatalog(ivodService.viewCatalog());
    		}
    		else if(input.matches("^play[ \t]\\d+$")) {
    			try {
        			String[] inputs = input.split("[ \t]");
					ivodService.playmovie(inputs[1], new ClientBox());
				} catch (NotBoundException e) {
					System.err.println("");
					//TODO
				} catch (MalformedURLException e) {
					e.printStackTrace();
					//TODO
				}
    		}
    		else {
    			System.err.println("Invalid command, try again.");
    		}
    	}
    }
    
    private static void showCatalog(List<MovieDesc> movies) {
    	Arrays.toString(movies.toArray());//TODO to improve
    }
}

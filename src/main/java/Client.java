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
	    	IConnection iConnection = (IConnection) registry.lookup("connectionVOD");
	    	
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
    		System.out.println("- If you want to leave type -> leave");
	    	System.out.println("- If you want to login type -> login");
    		System.out.println("- If you want to register first type -> register");
	    	
	    	String input = scanner.nextLine();
	    	if(input.equals("leave")) {
	    		System.out.println("Goodbye thanks you for visiting our project.");
	    		break;
	    	}
	    	else if(input.equals("register")) {
	    		register(scanner, iConnection);
    		}
	    	else if(input.equals("login")) {
	    		login(scanner, iConnection);
	    	}
	    	else {
    			System.err.println("Invalid command, try again.\n\n");
	    	}
    	}
    	
    	
    }

	private static boolean register(Scanner scanner, IConnection iConnection) {
    	String email;
    	String password;
    	while(true) {
    		try {
        		System.out.println("Enter an email :");
        		email = scanner.nextLine();
        		
        		System.out.println("Enter a password :");
        		password = scanner.nextLine();
        		
				iConnection.signIn(email, password);
				
				System.out.println("Registration successful !");
				System.out.println("You can now login.");
				return true;
			} catch (RemoteException | SignInFailed e) {
				System.err.println("An error occured during the registration : " + e.getMessage());
				System.out.println("- If you want to try again type -> again");
				System.out.println("- If you want to go back to the main menu type anything.");
				if(!scanner.nextLine().equals("again")) {
					return false;
				}
				System.out.println("\n\n");
			}
    	}
    }

    private static boolean login(Scanner scanner, IConnection iConnection) {
    	String email;
    	String password;
    	while(true) {
    		try {
	    		System.out.println("Enter your email :");
	    		email = scanner.nextLine();
	    		
	    		System.out.println("Enter your password :");
	    		password = scanner.nextLine();
	    		
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
				if(!scanner.nextLine().equals("again")) {
					return false;
				}
				System.out.println("\n\n");
			}
    	}
	}
    
    private static void mainMenu(Scanner scanner, IVODService ivodService) throws RemoteException {
    	String input;
    	while(true) {
    		System.out.println("Choose what you want to do :");
    		System.out.println("- If you want to see the movie catalog type -> catalog");
    		System.out.println("- If you want to play a movie type -> play <ISBN>");
    		System.out.println("- If you want to leave type -> leave");

    		input = scanner.nextLine();
    		if(input.equals("leave")) {
    			return;
    		}
    		else if(input.equals("catalog")) {
    			showCatalog(ivodService.viewCatalog());
    		}
    		else if(input.matches("^play[ \t]\\d+\n?$")) {
    			try {
    				String[] inputs = input.split(" ");
					Bill bill = ivodService.playmovie(inputs[1], new ClientBox());
					System.out.println("Factured bill :\n" + bill.toString());
				} catch (NotBoundException e) {
					e.printStackTrace();
					//TODO
				} catch (MalformedURLException e) {
					e.printStackTrace();
					//TODO
				}
    		}
    		else {
    			System.err.println("Invalid command, try again.\n\n");
    		}
    	}
    }
    
    private static void showCatalog(List<MovieDesc> movies) {
    	System.out.println(Arrays.toString(movies.toArray()));//TODO to improve
    }
}

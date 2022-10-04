import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
				System.out.println("\n\n");
				return false;
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
				
				System.out.println("\n\nLogin successful !");
				System.out.println("You have gained access to VODProject.\n");
				
				mainMenu(scanner, ivodService);
				System.out.println("Disconnection successful.");
				return true;
			} catch (RemoteException | InvalidCredentialsException e) {
				System.err.println("An error occured during the login : " + e.getMessage());
				System.out.println("\n\n");
				return false;
			}
    	}
	}
    
    private static void mainMenu(Scanner scanner, IVODService ivodService) throws RemoteException {
    	String input;
    	Map<Integer, MovieDesc> moviesMap = convertToMap(ivodService.viewCatalog());
    	showCatalog(moviesMap);
    	while(true) {
    		System.out.println("Choose what you want to do :");
    		System.out.println("- If you want to play a movie type -> play [ID]");
    		System.out.println("- If you want to see the movie catalog again type -> catalog");
    		System.out.println("- If you want to leave type -> leave");

    		input = scanner.nextLine();
    		if(input.equals("leave")) {
    			return;
    		}
    		else if(input.equals("catalog")) {
    			moviesMap = convertToMap(ivodService.viewCatalog());
    			showCatalog(moviesMap);
    		}
    		else if(input.matches("^play[ \t]\\d+\n?$")) {
    			try {
    				String[] inputs = input.split(" ");
    				MovieDesc choosenFilm = moviesMap.get(Integer.valueOf(inputs[1]));
    				if(choosenFilm != null) {
    					Bill bill = ivodService.playmovie(choosenFilm.getIsbn(), new ClientBox());
    					System.out.println("Factured price : " + bill.getOutrageousPrice());
    				}
    				else {
    					System.err.println("This ID does not exist, please use the number provided in the catalog.\n\n");
    				}
					
				} catch (NotBoundException | MalformedURLException e) {
					System.err.println("An error occured during the retrieving of the movie : " + e.getMessage());
					System.out.println("\n\n");
				} 
    		}
    		else {
    			System.err.println("Invalid command, try again.\n\n");
    		}
    	}
    }
    
    private static void showCatalog(Map<Integer, MovieDesc> movies) {
    	System.out.println("List of all available movies :");
		movies.forEach((id, movieDesc) -> System.out.println("- [" + id + "] " + movieDesc.getMovieName()
				+ " :\n\tPrice : " + movieDesc.getPrice() + "\n\tSynopsis : " + movieDesc.getSynopsis()));
		System.out.println("\n");
    }
    
    private static Map<Integer, MovieDesc> convertToMap(List<MovieDesc> movies){
    	Map<Integer, MovieDesc> result = new HashMap<>();
    	int i = 1;
		for (MovieDesc movieDesc : movies) {
			result.put(i++, movieDesc);
		}
		return result;
    }
}

package logic;

import java.util.HashMap;

import com.sun.media.jfxmedia.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Account extends Base implements Page {
	
	public static final int ID_PAGE = 4;
	private VBox root;
	private Menu menu;
	private Scene scene;
	private VBox subPage;
	private int loggedin;
	private HashMap<String, HashMap<String, String>> accounts;
	private final String noAccount = "No Account? Create New Account";
	public Account() throws IOException {
		renderPage();
	}
	
	public void renderPage() throws IOException{
        loggedin = 0;
		
        accounts = new HashMap<String, HashMap<String, String>>();
        parseAccounts();    
		
		root = new VBox();		
		subPage = createSub(root);      
		Background b = new Background();
		menu = new Menu();
		b.add(menu.getRoot());
		b.add(subPage);
		root.getChildren().addAll(b.getRoot());
		scene = new Scene(root, 1200, 800);
		
		final GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		
		final GridPane grid2 = new GridPane();
		grid2.setAlignment(Pos.CENTER);
		grid2.setHgap(10);
		grid2.setVgap(10);
		
		final Label success = new Label("Successful Login");
		grid2.add(success, 0, 24);
		
		final Label userName = new Label("User Name:");
		grid.add(userName, 0, 24);

		final TextField userTextField = new TextField();
		grid.add(userTextField, 1, 24);
		
		final Label badInput = new Label("");
		badInput.setTextFill(Color.RED);
		grid.add(badInput, 1, 29);
		
		final Label firstName = new Label("First Name:");
		final TextField firstNameTextField = new TextField();
		final Label lastName = new Label("Last Name:");
		final TextField lastNameTextField = new TextField();
		final Label email = new Label("Email:");
		final TextField emailTextField = new TextField();

		final Label pw = new Label("Password:");
		grid.add(pw, 0, 25);

		final PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 25);
		
		final Label pw2 = new Label("Retype Password:");

		final PasswordField pwBox2 = new PasswordField();

		final Button btn = new Button("Sign in");
		final HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		btn.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent e) {
		        String username = userTextField.getText();
		        String password = pwBox.getText();
		        badInput.setText("");
		        
		        HashMap<String, String> mapPassword = accounts.get(username);
		        if(mapPassword == null) {
		        	badInput.setText("       Incorrect Username or Password");
		        }
		        else if(mapPassword.get("pwd").equals(password)) {
		        	subPage.getChildren().remove(0);
		        	subPage.getChildren().add(grid2);
		        }
		        else {
		        	badInput.setText("       Incorrect Username or Password");
		        }
		    }
		});
		
		final Button newA = new Button(noAccount);
		final HBox hbBtn2 = new HBox(10);
		hbBtn2.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn2.getChildren().add(newA);
		newA.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent e) {
		    	badInput.setText("");
		        	        
		        if (newA.getText().equals(noAccount)) {
		           userTextField.setText("");
		           pwBox.setText("");
		        
		           grid.getChildren().remove(hbBtn);
		           grid.add(pwBox2, 1, 26);
		           grid.add(pw2, 0, 26);
		           newA.setText("Create Account");
		           grid.add(firstName, 0, 22);
		           grid.add(firstNameTextField, 1, 22);
		           grid.add(lastName, 0, 23);
		           grid.add(lastNameTextField, 1, 23);
		           grid.add(email, 0, 27);
		           grid.add(emailTextField, 1, 27);
		           
		        }
		        else {
		        	//here we will check if the user name is already in the hash map
		        	//if it is just say a simple message else
		        	//reset the login page so they can login
		        	String username = userTextField.getText();
			        String password = pwBox.getText();
			        String passwordVerify = pwBox2.getText();
			        String firstN = firstNameTextField.getText();
			        String lastN = lastNameTextField.getText();
			        String emails = emailTextField.getText();
			        
			        HashMap<String, String> inIt = accounts.get(username);
			        if(inIt != null) {
			        	badInput.setText("    Username Already In Use");
			        }
			        else if(!password.equals(passwordVerify)) {
			        	badInput.setText("    Passwords Do Not Match");
			        }
			        else if(username.equals("") || password.equals("") || firstN.equals("") || lastN.equals("") || emails.equals("")) {
			        	badInput.setText("Please Complete All Fields");
			        }
			        else {
			        	HashMap<String, String> temp = new HashMap<String, String>();
			        	temp.put("pwd", password);
			        	temp.put("firstN", firstN);
			        	temp.put("lastN", lastN);
			        	temp.put("email", emails);
			        	accounts.put(username, temp);
			        	userTextField.setText("");
			        	pwBox.setText("");
			        	newA.setText(noAccount);
			        	grid.add(hbBtn, 1, 28);
			        	grid.getChildren().remove(pwBox2);
			        	grid.getChildren().remove(pw2);
			        	grid.getChildren().remove(firstName);
			        	grid.getChildren().remove(firstNameTextField);
			        	grid.getChildren().remove(lastName);
			        	grid.getChildren().remove(lastNameTextField);
			        	grid.getChildren().remove(email);
			        	grid.getChildren().remove(emailTextField);
			        }
		        }
		        	
		    }
		});
		grid.add(hbBtn, 1, 28);
		grid.add(hbBtn2, 1, 35);
		
		subPage.getChildren().addAll(grid);
	}
	
	@Override
	public VBox getRoot() {
		return root;
	}
	
	@Override
	public Menu getMenu() {
		return menu;
	}
	
	@Override
	public Scene getScene() {
		return scene;
	}
	
	public void parseAccounts() throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("./src/main/java/logic/accounts.txt"));
		    StringBuilder sb = new StringBuilder();
		    String line1 = br.readLine();
		    String line2;
		    String[] splitty;
		    HashMap<String, String> temp;

		    while (line1 != null) {
		    	temp = new HashMap<String, String>();
		    	line2 = br.readLine();
		    	splitty = line2.split(",");
		    	for(int i = 0; i < splitty.length; i += 2) {
		    		temp.put(splitty[i], splitty[i+1]);
		    	}
		    	accounts.put(line1, temp);
		    	line1 = br.readLine();
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		finally {
			if (br != null) br.close();
		}
	}
}
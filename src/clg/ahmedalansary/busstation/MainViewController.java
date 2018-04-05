package clg.ahmedalansary.busstation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MainViewController implements Initializable {

	@FXML
	private JFXTextField userIdText;
	@FXML
	private JFXTextField newUserFullNameTF;
	@FXML
	private JFXTextField newUserNameTF;
	@FXML
	private JFXTextField newUserEmailTF;
	@FXML
	private JFXTextField newUserPhoneTF;
	@FXML
	private JFXPasswordField userPasswordText;
	@FXML
	private JFXPasswordField newUserPassTF;
	@FXML
	private Label typeLabel;
	@FXML
	private Label dontLabel;
	@FXML
	private Label signupLabel;
	@FXML
	private JFXToggleButton employeeTBtn;
	@FXML
	private JFXToggleButton passengerTBtn;
	@FXML 
	private AnchorPane loginFrame;
	@FXML 
	private AnchorPane signupFrame;
	
	
	public static Employee authenticatedEmployee;
	public static Passenger authenticatedPassenger;
	@FXML
	protected void cancelSignUp() {
		newUserEmailTF.setText("");
		newUserFullNameTF.setText("");
		newUserNameTF.setText("");
		newUserPassTF.setText("");
		newUserPhoneTF.setText("");
		signupFrame.setVisible(false);
		loginFrame.setVisible(true);
		employeeTBtn.setVisible(true);
		passengerTBtn.setVisible(true);
	}
	@FXML
	protected void login(ActionEvent event) throws IOException {
		String check = typeLabel.getText();
		if(check == "Employee") {
		String idCheck = userIdText.getText().trim();
		String passCheck = userPasswordText.getText().trim();
		Employee currentEmployee = MainView.db.authenticateUser(idCheck, passCheck);
		if (currentEmployee != null) {
			MainViewController.authenticatedEmployee = currentEmployee;
			switch (currentEmployee.getType()) {
			case 1:
				viewNewScene("/views/manager_view.fxml", event);
				break;
			case 2:
				break;
			}
		}
		}else {
			String idCheck = userIdText.getText();
			String passCheck = userPasswordText.getText();
			Passenger currentPassenger = MainView.db.authenticatePassenger(idCheck, passCheck);
			if (currentPassenger != null) {
				MainViewController.authenticatedPassenger = currentPassenger;
				viewNewScene("/views/passenger_view.fxml", event);
			}
		}
	}
	private void viewNewScene(String name,ActionEvent event) {
		try {
		Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent Root = FXMLLoader.load(getClass().getResource(name));
		Scene Scene = new Scene(Root);
		primaryStage.hide();
		primaryStage.setScene(Scene);
		primaryStage.show();
	}catch(Exception e) {
		e.printStackTrace();
	}
	}
	@FXML
	protected void goToSignUpForm(MouseEvent event) {
		loginFrame.setVisible(false);
		employeeTBtn.setVisible(false);
		passengerTBtn.setVisible(false);
		signupFrame.setVisible(true);
	}

	@FXML
	protected void closePorgram(MouseEvent event) {
		System.exit(0);
	}

	@FXML
	protected void checkAccountType(ActionEvent event) {
		JFXToggleButton type = (JFXToggleButton) event.getSource();
		String text = type.getText();
		if (text.equals("Employee")) {
			if (!employeeTBtn.isSelected()) {
				passengerTBtn.arm();
				passengerTBtn.setSelected(true);
				employeeTBtn.setSelected(false);
			} else {
				employeeTBtn.arm();
				passengerTBtn.setSelected(false);
			}
		} else {
			if (!passengerTBtn.isSelected()) {
				employeeTBtn.arm();
				employeeTBtn.setSelected(true);
				passengerTBtn.setSelected(false);
			} else {
				passengerTBtn.arm();
				employeeTBtn.setSelected(false);
			}
		}
		if(employeeTBtn.isSelected()) {
			typeLabel.setText("Employee");
			signupLabel.setVisible(false);
			dontLabel.setVisible(false);
		}else {
			typeLabel.setText("Passenger");
			signupLabel.setVisible(true);
			dontLabel.setVisible(true);
		
		}
	}
	@FXML
	protected void signup() {
		String name = newUserFullNameTF.getText().trim();
		String phoneNumber =  newUserPhoneTF.getText().trim();
		String userName =  newUserNameTF.getText().trim();
		String email =  newUserEmailTF.getText().trim();
		String pass =  newUserPassTF.getText().trim();
		if(name == null || phoneNumber == null || userName == null || email == null||pass == null) {
			MainView.showErrorDialogue("Missing Data!");
			return;
		}
		if(name.isEmpty()|| phoneNumber.isEmpty() || userName.isEmpty() || email.isEmpty() ||pass.isEmpty()) {
			MainView.showErrorDialogue("Missing Data!");
			return;
		}
		if(!MainView.db.isUniqueEmailAndUserName(userName) || !MainView.db.isUniqueEmailAndUserName(email)) {
			MainView.showErrorDialogue("Account Already Exists!");
		}
		Passenger passenger =  new Passenger(name, phoneNumber, email, pass, userName);
		MainView.db.addPassenger(passenger);
		MainView.showSuccessDialogue("successfull sigining up!");
		cancelSignUp();
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		passengerTBtn.arm();
		employeeTBtn.setSelected(true);
		passengerTBtn.setSelected(false);	
		passengerTBtn.setSelected(true);
		employeeTBtn.setSelected(false);
		signupFrame.setVisible(false);
		loginFrame.setVisible(true);
		
		newUserPhoneTF.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	newUserPhoneTF.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
	}

}

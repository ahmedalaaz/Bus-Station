package clg.ahmedalansary.busstation;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.simple.parser.ParseException;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainView extends Application {
	public static final String ACCOUNTS_FILE = "resources/DB/employees.json";
	public static final String TRIPS_FILE = "resources/DB/trips.json";
	public static final String VEHICLES_FILE = "resources/DB/vehicles.json";
	public static final String SEATS_FILE = "resources/DB/seats.json";
	public static final String PASSENGERS_FILE = "resources/DB/passengers.json";
	
	//public static String canonicalPath = "";
	//public final static String PATH = "/src/clg/ahmedalansary/busstation/DB";
	private final static String API_KEY = "AIzaSyDJPdLHrmwHevrM_WSApVF3Z4Acl-HZ_B0";
	public static Database db = new Database();

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub

		db.readAccounts(ACCOUNTS_FILE);
		db.readTrips(TRIPS_FILE);
		db.readVehicles(VEHICLES_FILE);
		db.readPassengers(PASSENGERS_FILE);
		db.readSeats(SEATS_FILE);
		launch(args);
		
		// utilize this to check for Internet connection !!
				
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		Parent mainViewRoot = FXMLLoader.load(getClass().getResource("/views/main_view.fxml"));
		Scene scene = new Scene(mainViewRoot);
		primaryStage.setScene(scene);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setTitle("Bus Station");
		primaryStage.setResizable(false);
		try {
			Object[] response = MainView.getDriveDistDura("cairo", "alexandria");
			System.out.println("Distance : " + (String) response[0] + "meters\nDuration : " + (String) response[1]);
		} catch (ApiException | InterruptedException | IOException e1) {
			// TODO Auto-generated catch block
			showErrorDialogue("Please connect to the internet!");
			e1.printStackTrace();
		}	

		primaryStage.show();
	}

	public static Object[] getDriveDistDura(String addrOne, String addrTwo)
			throws ApiException, InterruptedException, IOException {

		// set up key
		GeoApiContext distCalcer = new GeoApiContext.Builder().apiKey(API_KEY).build();
		DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(distCalcer);
		DistanceMatrix result = req.origins(addrOne).destinations(addrTwo)
				.language("en-US").mode(TravelMode.DRIVING).await();
		Object[] ret = new Object[2];
		System.out.println(result.originAddresses.toString()+ " " + 
		result.destinationAddresses.toString() + " "+result.rows[0].elements[0].status);
		if (result.rows[0].elements[0].distance == null)
			return ret = null;
		String distApart = result.rows[0].elements[0].distance.toString();
		String time = result.rows[0].elements[0].duration.toString();
		ret[0] = distApart;
		ret[1] = time;
		return ret;
	}

	public static void showErrorDialogue(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error Message");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();

	}

	public static void showSuccessDialogue(String message) {
		// TODO Auto-generated method stub
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Info Message");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public static Date getCurrentDate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dateobj = new Date();
		System.out.println(df.format(dateobj));
		return dateobj;
	}
	public static Date addDayToDate(Date date) {
		 Date newDate = date;
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DATE, 1); 
			newDate = c.getTime();
			return newDate;
	}
	private static String removeComma(String distString) {
		if (distString.contains(",")) {
			String temp = "";
			for (int i = 0; i < distString.length(); i++) {
				if (distString.charAt(i) != ',')
					temp += distString.charAt(i);
			}
			distString = temp;
		}

		return distString;
	}
	public static double getPrice(String distance) {
		// TODO Auto-generated method stub
		distance = removeComma(distance);
		String arr[] = distance.split(" ");
		double dist = Double.parseDouble(arr[0]);
		if(arr[1].equals("kmmeters") || arr[1].equals("km") ||  arr[1].contains("k")) {
			dist*=1000;
		}
		
		return dist*0.001;
	}
}

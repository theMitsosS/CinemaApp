package staff;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import com.tcg.json.JSONUtils;

import application.VariableTracker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ScreeningControlCtrl implements Initializable {
	
	

	String title = VariableTracker.movieTitle;
	String desc = VariableTracker.movieDescription;
	Image image = VariableTracker.movieImage;

	@FXML
	ImageView poster;
	@FXML
	Label titleLbl;
	@FXML
	ListView<String> screeningList;

	public void goBack(ActionEvent Event) throws IOException {
		Parent main = FXMLLoader.load(getClass().getResource("/staff/StaffMovieControl.fxml"));
		Scene loginscene = new Scene(main);
		Stage window = (Stage) ((Node) Event.getSource()).getScene().getWindow();
		window.setScene(loginscene);
		window.show();
		loginscene.getWindow().centerOnScreen();
	}
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		JSONObject obj =new JSONObject();
		try{
			obj = JSONUtils.getJSONObjectFromFile("database.json");
		}
		catch(Exception e){
			System.out.println(e);
		}
		titleLbl.setText(title);
		poster.setImage(image);
		
		JSONArray screenings = obj.getJSONArray("Screenings");
		ObservableList<String> times = FXCollections.observableArrayList();
		
		for (int i = 0; i < screenings.length(); i++) {
			
			if (screenings.getJSONObject(i).getString("title").equals(title)) {
				
				
					times.add(screenings.getJSONObject(i).getString("date")+" at "+
							screenings.getJSONObject(i).getString("time"));	
			}
		}
		screeningList.setItems(times);
	}
	public void goToScreeningAdder(ActionEvent Event) throws IOException {
			Parent main = FXMLLoader.load(getClass().getResource("/staff/ScreeningAdder.fxml"));
			Scene loginscene = new Scene(main);
			Stage window = (Stage) ((Node) Event.getSource()).getScene().getWindow();
			window.setScene(loginscene);
			window.show();
			loginscene.getWindow().centerOnScreen();

	}
	
	public void goToScreeningView(ActionEvent Event) throws IOException {
		
		if(screeningList.getSelectionModel().getSelectedItem()!=null)
		{
			String[] dayTime=new String[2];
			dayTime=convertDayTime();
			application.VariableTracker.selectedDateStaff=dayTime[0];
			application.VariableTracker.selectedTimeStaff=dayTime[1];
			Parent availableTimes = FXMLLoader.load(getClass().getResource("/staff/ScreeningOverview.fxml"));
			Scene availableTimesScene = new Scene(availableTimes);
			Stage window = (Stage) ((Node) Event.getSource()).getScene().getWindow();
			window.setScene(availableTimesScene);
			window.show();
			availableTimesScene.getWindow().centerOnScreen();
			
			
		}


}



	private String[] convertDayTime()
	{
		String[] dayTime=new String[2];
		dayTime=screeningList.getSelectionModel().getSelectedItem().split(" at ");
		return dayTime;
	}
	

}

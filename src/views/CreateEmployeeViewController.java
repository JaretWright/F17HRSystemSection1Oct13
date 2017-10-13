package views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Employee;
import models.HourlyEmployee;

/**
 * FXML Controller class
 *
 * @author JWright
 */
public class CreateEmployeeViewController implements Initializable {

    private ObservableList<Employee> employees;
    
    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField sinTextField;
    @FXML private DatePicker dob;
    @FXML private Label errorMsg;
    
    
    public void initialData(ObservableList<Employee> listOfEmp)
    {
        employees = listOfEmp;
    }
    
    public void createNewEmployeeButtonPushed(ActionEvent event) throws IOException
    {
        try
        {
            HourlyEmployee newEmp = new HourlyEmployee(firstNameTextField.getText(), 
                                                        lastNameTextField.getText(), 
                                                        sinTextField.getText(), 
                                                        dob.getValue());
            employees.add(newEmp);
            changeScene(event, "AllEmployeeView.fxml");
        }
        catch (IllegalArgumentException e)
        {
            errorMsg.setText(e.getMessage());
        }
    }
    
    public void cancelButtonPushed(ActionEvent event) throws IOException
    {
        changeScene(event, "AllEmployeeView.fxml");
    }
    
    
    public void changeScene(ActionEvent event, String fxmlFileName) throws IOException
    {
        //load a new scene
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFileName));
        Parent parent = loader.load();
        Scene newScene = new Scene(parent);
        
        //access the controller of the new Scene and send over
        //the current list of employees
        AllEmployeeViewController controller = loader.getController();
        controller.loadEmployees(employees);
        
        //Get the current "stage" (aka window) 
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        //change the scene to the new scene
        stage.setTitle("All Employees");
        stage.setScene(newScene);
        stage.show();
    }
            
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        errorMsg.setText("");
    }    
    
}

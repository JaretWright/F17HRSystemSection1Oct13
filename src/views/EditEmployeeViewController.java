package views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import models.Employee;

/**
 * FXML Controller class
 *
 * @author JWright
 */
public class EditEmployeeViewController implements Initializable {

    //private ObservableList<Employee> employees;
    private Employee employee;
    
    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private ImageView imageView;
    
    public void loadEmployee(Employee employeeToEdit)    
    {
        employee = employeeToEdit;
        firstNameTextField.setText(employee.getFirstName());
        lastNameTextField.setText(employee.getLastName());
        imageView.setImage(employee.getImage());
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

package views;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
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
    
    @FXML private ImageView employeeImage;
    
    //Used for the file chooser
    private FileChooser fileChooser;
    private File imageFile;
    
    
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
                                                        dob.getValue(),
                                                        employeeImage.getImage());
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
        
        try
        {
            BufferedImage bufferedImage = ImageIO.read(new File("./src/images/defaultPerson.png"));
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            employeeImage.setImage(image);
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
        }
    } 
    
    
    /**
     * This method will launch a FileChooser and load the image if accessible
     */
    public void chooseImageButtonPushed(ActionEvent event)
    {
        //get the stage to open a new window
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        
        //filter for only .jpg and .png files
        FileChooser.ExtensionFilter jpgFilter 
                = new FileChooser.ExtensionFilter("Image File (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter pngFilter 
                = new FileChooser.ExtensionFilter("Image File (*.png)", "*.png");
        
        fileChooser.getExtensionFilters().addAll(jpgFilter, pngFilter);
        
        
        //Set to the user's picture directory or C drive if not available
        String userDirectoryString = System.getProperty("user.home")+"\\Pictures";
        File userDirectory = new File(userDirectoryString);
        
        if (!userDirectory.canRead())
            userDirectory = new File("c:/");
        
        fileChooser.setInitialDirectory(userDirectory);
        
        //open the file dialog window
        imageFile = fileChooser.showOpenDialog(stage);
        
        //ensure the user selected a file
        if (imageFile.isFile())
        {
            try
            {
                BufferedImage bufferedImage = ImageIO.read(imageFile);
                Image image = SwingFXUtils.toFXImage(bufferedImage,null);
                employeeImage.setImage(image);
            }
            catch (IOException e)
            {
                System.err.println(e.getMessage());
            }
        }
    }
    
}

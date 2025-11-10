package com.example.restaurantordermanagement.controllers;

import com.example.restaurantordermanagement.models.Employee;
import com.example.restaurantordermanagement.models.Job;
import com.example.restaurantordermanagement.service.EmployeeService;
import com.example.restaurantordermanagement.service.JobService;
import com.example.restaurantordermanagement.utils.*;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;


public class EmployeesController {

    @FXML
    private Label labelEmployes;

    @FXML
    private AnchorPane createForm;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField salaryField;
    @FXML
    private ComboBox<OptionComboBox> jobField;
    @FXML
    private Label errorProblem;
    @FXML
    private VBox employeeListContainer;

    @FXML
    private Button createEmployeeButton;
    @FXML
    private Button updateEmployeeButton;
   private EmployeeService employeeService;
   private JobService jobService;

   @FXML
   public void initialize(){
       employeeService = AppContext.getEmployeeService();
       jobService = AppContext.getJobService();
       generateEmployeesButtons();
       populateComboBox();


   }
    @FXML
    protected void onShowFormButtonClick(){
        if(labelEmployes.getText().isBlank()){
            labelEmployes.setText("Create an Employee");
        }else{
            labelEmployes.setText("");
        }

        populateFormValues("","","",-1);
        createEmployeeButton.setVisible(true);
        updateEmployeeButton.setVisible(false);
        createForm.setVisible(!createForm.isVisible());
    }
    @FXML
    protected void onCreateEmployeeButtonClick(){

       String firstName = firstNameField.getText().strip();
       String lastName = lastNameField.getText().strip();
       String salaryString = salaryField.getText().strip();
       try{
           int salary = Integer.parseInt(salaryString);

           OptionComboBox selected = jobField.getSelectionModel().getSelectedItem();
           Employee e = new Employee(-1,firstName,lastName,selected.getValue(),salary);
           employeeService.create(e);
           generateEmployeesButtons();
       } catch (NumberFormatException e) {
           errorProblem.setText("Salary need to be a number");
       } catch (EmptyStringException e) {
           errorProblem.setText(e.getMessage());
       } catch (AlreadyExistsException e) {
           errorProblem.setText(e.getMessage());
       } catch (Exception e) {
           e.printStackTrace();
       }




    }


    private void populateComboBox(){


       for(Job j:jobService.getAllJobs()){
           jobField.getItems().add(new OptionComboBox(j.getId(),j.getJobName()));
       }
    }



    private void generateEmployeesButtons(){
        employeeListContainer.getChildren().clear();
        List<String> employeesList = employeeService.showEmployee();
        for(String employee:employeesList){
            HBox employeeRow = new HBox(20); // spatiu intre ele
            employeeRow.setAlignment(Pos.CENTER_LEFT);
            Label employeeLabel = new Label(employee);

            employeeLabel.setPrefWidth(220);
            Button updateEmplyee = new Button("Update");
            Button deleteEmployee = new Button("Delete");

            updateEmplyee.setPrefWidth(80);
            deleteEmployee.setPrefWidth(80);



           try{
                String[] employeeSplit = employee.split("\\|");
                int id = Integer.parseInt(employeeSplit[0]);
                deleteEmployee.setOnAction(e->handleDelete(id));
               updateEmplyee.setOnAction(e->handleUpdate(id));
           } catch (NumberFormatException e) {
               e.printStackTrace();
               deleteEmployee.setText("Id not found!");
               updateEmplyee.setText("Id not found!");
           }

            employeeRow.setMinWidth(employeeLabel.getPrefWidth()
                    +updateEmplyee.getPrefWidth()
                    +deleteEmployee.getPrefWidth());
            employeeRow.getChildren().addAll(employeeLabel,updateEmplyee,deleteEmployee);
            employeeListContainer.getChildren().add(employeeRow);
        }
    }


    private void handleUpdate(int id){

        if(labelEmployes.getText().isBlank()){
            labelEmployes.setText("Update an Employee");
        }else{
            labelEmployes.setText("");
        }
        Employee e =employeeService.findEmployeeById(id);
        populateFormValues(e.getFirstName(),e.getLastName()
                , String.valueOf(e.getSalary()),e.getJobId());

        createForm.setVisible(!createForm.isVisible());
        createEmployeeButton.setVisible(false);
        updateEmployeeButton.setVisible(true);
        updateEmployeeButton.setOnAction(func->onUpdateEmployeeButtonClick(id));
    }

    private void onUpdateEmployeeButtonClick(int id){
        String firstName = firstNameField.getText().strip();
        String lastName = lastNameField.getText().strip();
        String salaryString = salaryField.getText().strip();
        try{
            int salary = Integer.parseInt(salaryString);


            Employee e = new Employee(id,firstName,lastName,jobField.getValue().getValue(),salary);
            employeeService.update(e);
            generateEmployeesButtons();
        } catch (NumberFormatException e) {
            errorProblem.setText("Salary need to be a number");
        } catch (EmptyStringException e) {
            errorProblem.setText(e.getMessage());
        } catch (AlreadyExistsException e) {
            errorProblem.setText(e.getMessage());
        }catch (SameElementException e){
            errorProblem.setText(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
  private void handleDelete(int id){

       employeeService.deleteEmployee(id);
       generateEmployeesButtons();
  }

    private void populateFormValues(String firstName,String lastName,String salary, int employeeJobId){
        firstNameField.setText(firstName);
        lastNameField.setText(lastName);
        salaryField.setText(salary);
        if(employeeJobId !=-1){
            for(OptionComboBox option:jobField.getItems()){
                if(option.getValue() == employeeJobId){
                    jobField.getSelectionModel().select(option);
                }
            }
        }

    }
}

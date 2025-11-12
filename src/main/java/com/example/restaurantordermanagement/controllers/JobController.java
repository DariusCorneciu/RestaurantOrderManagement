package com.example.restaurantordermanagement.controllers;

import com.example.restaurantordermanagement.models.Job;
import com.example.restaurantordermanagement.repository.EmployeeRepository;
import com.example.restaurantordermanagement.service.EmployeeService;
import com.example.restaurantordermanagement.service.JobService;
import com.example.restaurantordermanagement.utils.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;



public class JobController {

    private enum JobFormMode{ADD,EDIT,DELETE}
    private JobFormMode currentMode;
    @FXML
    private TextField searchField;

    @FXML
    private TextField jobNameField;
    @FXML
    private Label errorMessage;
    @FXML
    private Label labelFormName;

    @FXML
    private TableView<JobRow> jobTable;
    private ObservableList<JobRow> jobList = FXCollections.observableArrayList();

    @FXML
    private ComboBox<OptionComboBox> jobComboBox;

    @FXML
    private Button formJobButton;

    @FXML
    private VBox jobForm;
    private final EmployeeService employeeService = AppContext.getEmployeeService();
    private final JobService jobService = AppContext.getJobService();
    @FXML
    public void initialize(){

        jobList.addAll(employeeService.countEmployeesForAllJobs());
        jobTable.setItems(jobList);
        populateComboBox();
    }
    @FXML
    protected void searchJob(){

        String search = searchField.getText().strip();

        try {
            Job j = jobService.findJob(search);
            long counter = employeeService.countEmployeeAtJobById(j.getId());
            jobList.clear();
            jobList.add(new JobRow(j.getJobName(),counter));

        }catch (ElementNotFoundException e){
            errorMessage.setText(e.getMessage());
        }catch (EmptyStringException e){
            jobList.clear();
            jobList.addAll(employeeService.countEmployeesForAllJobs());

        } catch (Exception e) {
            errorMessage.setText("Look in console!");
            e.printStackTrace();
        }
    }
    @FXML
    public void showAddForm(){
        switchForm(JobFormMode.ADD);


    }
    @FXML
    public void showDeleteForm(){
        switchForm(JobFormMode.DELETE);


    }
    @FXML
    public void showEditForm(){
        switchForm(JobFormMode.EDIT);


    }
    private void switchForm(JobFormMode mode) {
        if(currentMode == mode && jobForm.isVisible()){

            jobForm.setVisible(false);
            return;
        }


        currentMode = mode;
        jobForm.setVisible(true);
        switch (mode){
            case ADD ->{
                jobComboBox.setVisible(false);
                jobNameField.setVisible(true);
                labelFormName.setText("Add Job");
                formJobButton.setText("Add");
                formJobButton.setOnAction(e->handleAdd());
            }
            case EDIT ->{
                jobComboBox.setVisible(true);
                jobNameField.setVisible(true);
                labelFormName.setText("Edit Job");
                formJobButton.setText("Edit");
                formJobButton.setOnAction(e->handleEdit());
            }
            case DELETE -> {
                jobNameField.setVisible(false);
                jobComboBox.setVisible(true);
                labelFormName.setText("Delete Job");
                formJobButton.setText("Delete");
                formJobButton.setOnAction(e->handleDelete());
            }
        }
    }
    private void handleAdd(){
        String jobName = jobNameField.getText().strip();
        Job newJob = new Job(-1,jobName);
        try {
            jobService.create(newJob);
            long counter = employeeService.countEmployeeAtJobById(newJob.getId());
            jobList.add(new JobRow(jobName,counter));
        }catch (EmptyStringException e){
            errorMessage.setText(e.getMessage());
        }catch (SameElementException e){
            errorMessage.setText(e.getMessage());
        }
    }
    private void handleEdit(){
        try{
            String search = jobNameField.getText().strip();
            OptionComboBox selected = jobComboBox.getSelectionModel().getSelectedItem();
            if(selected == null){
                errorMessage.setText("Please, select a job to edit");
                return;
            }
            Job job = new Job(jobService.findJob(selected.getLabel()));

            job.setJobName(search);
            jobService.update(job);
            populateComboBox();
            jobList.clear();
            jobList.addAll(employeeService.countEmployeesForAllJobs());

        }catch (EmptyStringException e){
            errorMessage.setText(e.getMessage());
        }catch (SameElementException e){
            errorMessage.setText(e.getMessage());
        }

    }

    private void handleDelete(){
        OptionComboBox selected = jobComboBox.getSelectionModel().getSelectedItem();
        if(selected == null){
            errorMessage.setText("Please, select a job to edit");
            return;
        }
        try{
            jobService.delete(selected.getValue());
            populateComboBox();
            jobList.clear();
            jobList.addAll(employeeService.countEmployeesForAllJobs());
        }catch (ElementNotFoundException e){
            errorMessage.setText(e.getMessage());
        }


    }

    private void populateComboBox(){
        jobComboBox.getItems().clear();
        for(Job j:jobService.getAllJobs()){
            jobComboBox.getItems().add(new OptionComboBox(j.getId(),j.getJobName()));
        }
    }


}

package com.example.restaurantordermanagement.DAO;

import com.example.restaurantordermanagement.models.Employee;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileEmployeeDAO implements EmployeeDAO {


    private static List<Employee> employees = new ArrayList<>();
    private final String filePath = "data/employees.txt";
    /**
     * Citeste toti angajatii din fisierul employees.txt
     * si returneaza o lista de obiecte Employee.
     *
     * @return lista de angajati
     */
    @Override
    public List<Employee> loadList(){

        File employeeFile = new File(filePath); // iau fisierul cu datele din path
         try(Scanner scanner = new Scanner(employeeFile)){
             // creez un scanner care scaneaza employeeFile
             while(scanner.hasNextLine()){
                 // parcurg fisierul cat timp exista o urmatoare linie
                 String data = scanner.nextLine();

                 // Sparg linia pe mai multe "coloane"
                 String[] columns = data.split(";");

                 //si le adaug in functie de ordine in variabile
                 int id = Integer.parseInt(columns[0]);
                 String firstName = columns[1];
                 String lastName = columns[2];
                 int jobId = Integer.parseInt(columns[3]);

                 /// Creeez obiectele de tipul Employee si le adaug in lista
                 Employee e = new Employee(id,firstName,lastName,jobId);
                 employees.add(e);

             }

         } catch (Exception e) {
             e.printStackTrace();
             //afiseaza eroarea
         }


        return employees;
    }
    @Override
    public void save(Employee e){
/// o sa scriu eu mai incolo
    }


}

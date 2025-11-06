package com.example.restaurantordermanagement.DAO;

import com.example.restaurantordermanagement.models.Employee;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clasa care implementeaza inerfata EmployeeDAO pentru fisiere de tip txt
 * Contine metode de read,save,update si delete pentru fisiere txt
 * @author Darius Corneciu
 * @since 2025-11-06
 * @version 1.0
 */

public class FileEmployeeDAO implements EmployeeDAO {


    private final String filePath = "data/employees.txt";
    /**
     * Citeste toti angajatii din fisierul employees.txt
     * si returneaza o lista de obiecte Employee.
     *
     * @return lista de angajati
     */
    @Override
    public ArrayList<Employee> loadList(){
        ArrayList<Employee> employees = new ArrayList<>();
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
                 int salary = Integer.parseInt(columns[4]);

                 /// Creeez obiectele de tipul Employee si le adaug in lista
                 Employee e = new Employee(id,firstName,lastName,jobId,salary);
                 Employee.counterId++;
                 employees.add(e);

             }

         } catch (Exception e) {
             e.printStackTrace();
             //afiseaza eroarea
         }


        return employees;
    }

    /**
     * Salveaza un angajat in fisierul txt aflat la filePath
     * @param emp obiectul din clasa Employee care trebuie salvat
     */
    @Override
    public int save(Employee emp){

        emp.setId(++Employee.counterId);
    String text = emp.GenerateTxtString();
    try(BufferedWriter writer
                = new BufferedWriter(new FileWriter(filePath,true))){
        writer.write(text);

        return emp.getId();
    }catch (Exception e){
        e.printStackTrace();
        return -1;
    }

}

    /**
     * Metoda foloseste updatedEmp pentru a cauta in lista de angajati.
     * Metoda equals este in clasa Employee, care foloseste ID pentru cautare.
     * Ulterior seteaza la acel index updatedEmp
     * @param updatedEmp obiectul primit updatat, dar care pastreaza acelasi ID
     * @author Darius Corneciu
     * @since 2025-11-06
     * @version 1.0
     * @see com.example.restaurantordermanagement.models.Employee
     * @see #saveAll(ArrayList<Employee>) metoda folosita intern pentru a rescrie fisierul text.
     */
    @Override
    public void update(Employee updatedEmp){

        ArrayList<Employee> employeeList = loadList();
        // folosind equals din clasa o sa caute strict dupa Id
        int index = employeeList.indexOf(updatedEmp);
        if(index != -1){
           employeeList.set(index,updatedEmp);
           //actualizeaza elementul din lista
            saveAll(employeeList);
        }
    }
    /**
     * Metoda foloseste toRemoveEmp pentru a cauta in lista de angajati.
     * Metoda equals este in clasa Employee, care foloseste ID pentru cautare.
     * Ulterior daca index-ul este valid, continua si sterge elementul.
     * @param toRemoveEmp obiectul primit care trebuie sters din lista
     * @author Darius Corneciu
     * @since 2025-11-06
     * @version 1.0
     * @see com.example.restaurantordermanagement.models.Employee
     * @see #saveAll(ArrayList<Employee>) metoda folosita intern pentru a rescrie fisierul text.
     */
    @Override
    public void delete(Employee toRemoveEmp){
        ArrayList<Employee> employees = loadList();
        int index = employees.indexOf(toRemoveEmp);
        if(index != -1){
            employees.remove(index);
            saveAll(employees);
        }




    }

    /**
     * Metoda folosita intern pentru a avea un cod mai curat, aceasta primeste o lista si suprascrie
     * fisierul text aflat la FilePath
     * @param employees obiectul primit care trebuie sters din lista
     * @author Darius Corneciu
     * @since 2025-11-06
     * @version 1.0
     * @see com.example.restaurantordermanagement.models.Employee
     */
    private void saveAll(ArrayList<Employee> employees){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,false))){
            for(Employee e: employees){
                writer.write(e.GenerateTxtString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

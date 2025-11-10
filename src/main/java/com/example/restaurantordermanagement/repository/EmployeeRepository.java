package com.example.restaurantordermanagement.repository;

import com.example.restaurantordermanagement.DAO.EmployeeDAO;
import com.example.restaurantordermanagement.models.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository-ul pentru Employee, aici creem o lista const pe care lucram mai departe
 * in program, nu folosim direct fisierul, pentru asta avem DAO.
 * Contine metodele CRUD, care apeleaza si modificarea fisierului prin intermediul DAO
 * Plus alte metode pentru filtare simple
 * @author Darius Corneciu
 * @since 2025-11-06
 * @see com.example.restaurantordermanagement.DAO.EmployeeDAO
 * @see com.example.restaurantordermanagement.models.Employee
 */
public class EmployeeRepository {
    public final EmployeeDAO employeeDAO;
    public final List<Employee> employees;

    /**
     * Incarcam variabilele noastre din interiorul clasei, care sunt finale.
     * lista este generata prin intermediul employeeDAO.
     * @implNote se putea folosii si employeeDAO.loadList() direct, dar am optat pentru separarea straturilor
     * @implNote  Daca as folosii metoda din DAO, si ulterior as modifica DAO-ul
     *  pentru un alt tip de fisier optand sa folosesc
     *  si in acesta o lista interna, metoda ar putea trimite o referinta catre lista, si folosind lista din repository as folosii lista din DAO.
     *
     *  @param employeeDAO DAO-ul folosit pentru accesul la date
     * @author Darius Corneciu
     * @since 2025-11-06
     * @see com.example.restaurantordermanagement.DAO.EmployeeDAO
     */
    public EmployeeRepository(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
        this.employees = new ArrayList<Employee>(employeeDAO.loadList());


    }

    ///  Metode de interactiune cu DAO
    /**
     * Metoda create, adauga un element in lista repository-ului si ulterior si in fisier.
     * @param employee parametrul primit pentru a fii adaugat in lista
     * @author Darius Corneciu
     * @since 2025-11-06
     * @see com.example.restaurantordermanagement.DAO.EmployeeDAO
     */
    public void create(Employee employee){
        employeeDAO.save(employee);
        employees.add(employee);
    }
    /**
     * Metoda cauta elementul folosind metoda equals din obiectul Employee.
     * Daca elementul este gasit atunci se face modificarea in lista, dar si in fisier.
     * @param e parametrul primit pentru a fii modificat in lista
     * @author Darius Corneciu
     * @since 2025-11-06
     * @see #update(Employee)
     * @see com.example.restaurantordermanagement.DAO.EmployeeDAO
     * @see com.example.restaurantordermanagement.models.Employee
     */
    public void update(Employee e){
        int index = employees.indexOf(e);
        if(index != -1){
            employees.set(index,e);
            employeeDAO.update(e);
        }
    }
    /**
     * Metoda cauta elementul folosind metoda equals din obiectul Employee.
     * Daca elementul este gasit atunci se face stergerea folosind indexul.
     * Ulterior apeland si metoda din DAO pentru stergerea din fisier
     * @param e parametrul primit pentru a fii modificat in lista
     * @author Darius Corneciu
     * @since 2025-11-06
     * @see #delete(Employee) (Employee)
     * @see com.example.restaurantordermanagement.DAO.EmployeeDAO
     * @see com.example.restaurantordermanagement.models.Employee
     */
    public void delete(Employee e){
        int index = employees.indexOf(e);
        if(index != -1){
            employees.remove(index);
            employeeDAO.delete(e);
        }
    }


    ///  Metode folosind strict lista
    /**
     * Metoda parcurge lista si cauta elementul dupa id.
     * @return null sau elementul gasit
     * @param id id-ul elementului cautat
     * @author Darius Corneciu
     * @since 2025-11-06
     *
     * @see com.example.restaurantordermanagement.models.Employee
     */

    public Employee findElementById(int id){
        Employee returned = null;
        for(Employee e: employees){
            if(e.getId() == id){
                returned = e;
                break;
            }
        }
        return returned; // trimite referinta nu o copie la obiect
    }

    /**
     * Folosid stra
     * Lista rezultata este o copie a structurii listei, dar referintele catre obiectele Employee sunt aceleasi ca in lista interna.
     * @implNote Modificarile asupra obiectelor din aceasta lista se vor reflecta si in lista interna, dar adaugarea sau stergerea elementelor nu afecteaza lista interna.
     * @return lista cu angajatii care au jobul X
     * @param jobId id-ul jobului dupa care se va face filtrarea
     * @author Darius Corneciu
     * @since 2025-11-06
     *
     * @see com.example.restaurantordermanagement.models.Employee
     */
    public List<Employee> filterEmployeesByJob(int jobId){
        // trimite o copie la lista, dar cu referintele elementelor
        return employees.stream()
                .filter(e-> e.getJobId() == jobId).toList();
    }
    /**
     * Lista rezultata este o copie a structurii listei, dar referintele catre obiectele Employee sunt aceleasi ca in lista interna.
     * @implNote Modificarile asupra obiectelor din aceasta lista se vor reflecta si in lista interna, dar adaugarea sau stergerea elementelor nu afecteaza lista interna.
     * @return null sau elementul gasit
     * @param keyword secventa de litere care este cautata in interiorul fullName-ului
     * @author Darius Corneciu
     * @since 2025-11-06
     *
     * @see com.example.restaurantordermanagement.models.Employee
     */
    public List<Employee> searchByName(String keyword){
        // trimite o copie la lista, dar cu referintele elementelor
       return employees.stream()
                .filter(e-> (e.getFirstName()+" "+e.getLastName())
                        .toLowerCase().contains(keyword.toLowerCase())).toList();
    }


    public int lastId(){
        return employees.getLast().getId();
    }

}

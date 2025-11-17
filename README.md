# TableMate 🍝
### PPOO Project

Cerinta proiectului:
>Tema 5
> 
```
Sa se implementeze o aplicație Java pentru gestiunea comenzilor din cadrul unui
restaurant, care presupune lucrul cu fișiere, vectori și colecții în Java. Se va asigura persistența
datelor, utilizând fișiere (fără lucrul cu baze de date). Aplicația permite realizarea unor situații
(rapoarte) privind valoarea comenzilor zilnice, cele mai vândute produse, etc.
```
## 📌 Descriere
Aplicația permite administrarea unui restaurant prin:

- gestionarea angajaților și joburilor  
- sistem de clock-in / clock-out  
- managementul meselor și comenzilor  
- adăugarea de produse la comenzi  
- generarea comenzilor și facturilor  
- statistici vizuale (PieChart / BarChart)

Persistența datelor se face **exclusiv prin fișiere**, fără baze de date, folosind un sistem propriu DAO → Repository → Service.

## 🏛️ Arhitectură
Structura urmează pattern-ul:
DAO → Repository → Service → Controller → FXML


### 🗂️ Layer-e definite:

- **DAO Layer** – citește/scrie în fișiere `.txt`
- **Repository Layer** – gestionează liste în memorie și sincronizare cu fișierele
- **Service Layer** – conține logica de business + validări
- **Controller Layer** – gestionează UI-ul JavaFX
- **Models** – clase pentru Employee, Job, Table, Order, Product, ProductOrder, Clocking etc.

---

## 🛠️ Tehnologii folosite
- **Java 17+**
- **JavaFX 17+**
- **SceneBuilder (FXML)**
- **Colecții Java (List, Map, Set, Stream API)**
- **Fișiere text pentru persistență**
- **Matrice / vectori pentru statistici**

---

## ✨ Funcționalități principale

### 👤 Management Angajați & Joburi
- CRUD complet pentru Job
- CRUD complet pentru Employee
- Atribuire job → angajat
- Statistici despre job-uri (PieChart)

### ⏱️ Clock-in / Clock-out
- Angajatul se autentifică prin cod
- Clock-in și Clock-out înregistrate cu `LocalDateTime`
- Istoric vizibil într-un ScrollPane

### 🍽️ Management Mese
- Poziționare mese pe ecran ca procente din dimensiunea ferestrei
- Recalculare poziții la resize
- Mese verzi = libere  
- Mese roșii = ocupate  
- Popup de autentificare angajat

### 🧾 Comenzi
- Creare comenzi noi  
- Adăugare produse cu butoane **+ / −**  
- Eliminare produs când cantitatea ajunge la 0  
- Salvare automată în ProductOrderRepository

### 📦 Produse
- Listă completă de produse
- Cantități totale vândute
- **Matrice `int[][]`** generată pentru raportări:

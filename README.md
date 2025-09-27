# 🏢 Department & Employee Management System

This is a **Spring Boot** project that provides a REST API to manage **departments** and **employees**, along with a salary adjustment feature based on performance and tenure.

---

## 🚀 Features
- **Department Management**
  - Create, update, delete, and list departments.
- **Employee Management**
  - Create, update, delete, and list employees.
  - Assign employees to departments.
- **Salary Adjustment**
  - Performance-based salary increase (10–15%).
  - Extra tenure bonus for employees with >5 years of service.
  - Salary cap enforcement (max: 200,000).
  - **Idempotency:** prevents duplicate adjustments within 30 minutes.
- **Validation & Exception Handling**
  - Graceful error messages for missing entities and invalid data.
- **Database Integration**
  - Uses **Spring Data JPA** with Microsoft SQL Server.

---

## 🛠️ Tech Stack
- **Backend:** Spring Boot 3, Spring Web, Spring Data JPA  
- **Database:** Microsoft SQL Server (via `mssql-jdbc`)  
- **Build Tool:** Maven  
- **Language:** Java 21  
- **Other Tools:** Lombok, Jakarta Validation  

---

## 📡 API Endpoints

| Method | Endpoint | Description |
|-------|-----------|-------------|
| `POST` | `/departments` | Create a new department |
| `GET` | `/departments` | Get all departments |
| `POST` | `/employees` | Create a new employee |
| `GET` | `/employees` | Get all employees |
| `PUT` | `/employees/{id}` | Update employee details |
| `DELETE` | `/employees/{id}` | Delete an employee |
| `POST` | `/employees/adjust-salary` | Adjust salary for employees in a department |

---

## 📦 Setup & Run

```bash
# Clone the repository
git clone https://github.com/danishdk46/assignment.git
cd assignment

# Build the project
mvn clean install -DskipTests

# Run the application
mvn spring-boot:run

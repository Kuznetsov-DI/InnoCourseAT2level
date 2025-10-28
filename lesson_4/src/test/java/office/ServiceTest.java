package office;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceTest {

    @BeforeEach
    void init() {
        Service.createDB();
    }

    @Test
    @DisplayName("Проверка создания департамента")
    void addDepartment() {
        var dep = new Department(10, "test_name");
        Department createdDep = null;
        Service.addDepartment(dep);

        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            PreparedStatement stm = con.prepareStatement("SELECT ID, NAME FROM Department WHERE ID = ?");
            stm.setInt(1, 10);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                createdDep = new Department(rs.getInt("ID"), rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        Assertions.assertNotNull(createdDep);
        Assertions.assertEquals(dep, createdDep);
    }

    @Test
    @DisplayName("Проверка удаления департамента")
    void removeDepartment() {
        Department deletedDep = null;
        Service.removeDepartment(1);

        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            PreparedStatement stm = con.prepareStatement("SELECT ID, NAME FROM Department WHERE ID = ?");
            stm.setInt(1, 1);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                deletedDep = new Department(rs.getInt("ID"), rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        Assertions.assertNull(deletedDep);
    }

    @Test
    @DisplayName("Проверка создания сотрудника")
    void addEmployee() {
        var emp = new Employee(10, "test_name", 1);
        Employee createdEmp = null;
        Service.addEmployee(emp);

        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            PreparedStatement stm = con.prepareStatement("SELECT ID, Name, DepartmentID FROM Employee WHERE ID = ?");
            stm.setInt(1, 10);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                createdEmp = new Employee(rs.getInt("ID"), rs.getString("name"), rs.getInt("DepartmentID"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        Assertions.assertNotNull(createdEmp);
        Assertions.assertEquals(emp, createdEmp);
    }

    @Test
    @DisplayName("Проверка удаления сотрудника")
    void removeEmployee() {
        Employee deletedEmp = null;
        Service.removeEmployee(1);

        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            PreparedStatement stm = con.prepareStatement("SELECT ID, Name, DepartmentID FROM Employee WHERE ID = ?");
            stm.setInt(1, 1);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                deletedEmp = new Employee(rs.getInt("ID"), rs.getString("name"), rs.getInt("DepartmentID"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        Assertions.assertNull(deletedEmp);
    }

    @Test
    @DisplayName("Проверка получения информации о департаменте")
    void getDepartment() {
        Department expectedDep = null;
        Department actualDep = Service.getDepartment(1);

        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            PreparedStatement stm = con.prepareStatement("SELECT ID, NAME FROM Department WHERE ID = ?");
            stm.setInt(1, 1);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                expectedDep = new Department(rs.getInt("ID"), rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        Assertions.assertNotNull(actualDep);
        Assertions.assertEquals(expectedDep, actualDep);
    }

    @Test
    @DisplayName("Проверка получения информации о сотруднике")
    void getEmployee() {
        Employee expectedEmp = null;
        Employee actualEmp = Service.getEmployee(1);

        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            PreparedStatement stm = con.prepareStatement("SELECT ID, Name, DepartmentID FROM Employee WHERE ID = ?");
            stm.setInt(1, 1);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                expectedEmp = new Employee(rs.getInt("ID"), rs.getString("name"), rs.getInt("DepartmentID"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        Assertions.assertNotNull(actualEmp);
        Assertions.assertEquals(expectedEmp, actualEmp);
    }

    @Test
    @DisplayName("Проверка получения информации о всех департаментах")
    void getDepartments() {
        List<Department> expectedDeps = new ArrayList<>();
        List<Department> actualDeps = Service.getDepartments();

        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            Statement stm = con.createStatement();

            ResultSet rs = stm.executeQuery("SELECT ID, NAME FROM Department");

            while (rs.next()) {
                var dep = new Department(rs.getInt("ID"), rs.getString("name"));
                expectedDeps.add(dep);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        Assertions.assertFalse(actualDeps.isEmpty());
        Assertions.assertEquals(expectedDeps, actualDeps);
    }

    @Test
    @DisplayName("Проверка получения информации о всех сотрудниках")
    void getEmployees() {
        List<Employee> expectedEmps = new ArrayList<>();
        List<Employee> actualEmps = Service.getEmployees();

        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            Statement stm = con.createStatement();

            ResultSet rs = stm.executeQuery("SELECT ID, Name, DepartmentID FROM Employee");

            while (rs.next()) {
                var emp = new Employee(rs.getInt("ID"), rs.getString("name"), rs.getInt("DepartmentID"));
                expectedEmps.add(emp);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        Assertions.assertFalse(actualEmps.isEmpty());
        Assertions.assertEquals(expectedEmps, actualEmps);
    }
}

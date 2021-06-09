package lab3.repository;

import lab3.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StudentJdbcRepository implements ICrudRepository<Student> {
    //List<Student> studentList = new ArrayList<Student>();

    /*
    public StudentJdbcRepository() throws ClassNotFoundException, SQLException {
        try {
            String url = "jdbc:sqlserver://DESKTOP-EINH3N1\\SQLEXPRESS04;integratedSecurity=true;";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            String sql;
            sql = "use Lab6_map select * from Student";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                long studentId = resultSet.getLong("StudentId");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                int totalCredits = resultSet.getInt("TotalCredits");
                String strEnrolledCourses = resultSet.getString("EnrolledCourses");
                String[] auxEnrolledCourses = strEnrolledCourses.split(",");
                List<Long> enrolledCourses = new ArrayList<Long>();
                Arrays.stream(auxEnrolledCourses)
                        .forEach(e -> enrolledCourses.add(Long.parseLong(e)));
                studentList.add(new Student(studentId, firstName, lastName, totalCredits, enrolledCourses));
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
     */

    public void deleteFromStudentDB(long id) throws ClassNotFoundException, SQLException {
        try {
            String url = "jdbc:sqlserver://DESKTOP-EINH3N1\\SQLEXPRESS04;databaseName=Lab6_map;integratedSecurity=true;";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            String sql;
            sql = "delete from Student where StudentId = " + String.valueOf(id);
            statement.executeQuery(sql);
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertIntoStudentDB(Student student) throws ClassNotFoundException, SQLException {
        try {
            String url = "jdbc:sqlserver://DESKTOP-EINH3N1\\SQLEXPRESS04;databaseName=Lab6_map;integratedSecurity=true;";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            String sql;
            String auxEnrolledCourses = "";
            for (int i = 0; i < student.getEnrolledCourses().size(); i++)
                auxEnrolledCourses += String.valueOf(student.getEnrolledCourses().get(i)) + ",";
            auxEnrolledCourses = auxEnrolledCourses.substring(0, auxEnrolledCourses.length() - 1);
            sql = "insert into Student values (" + String.valueOf(student.getStudentId()) +
                    ",'" + student.getFirstName() + "','" + student.getLastName() + "'," +
                    String.valueOf(student.getTotalCredits()) + ",'" + auxEnrolledCourses + "')";
            statement.executeQuery(sql);
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Student findOne(Long id) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        String url = "jdbc:sqlserver://DESKTOP-EINH3N1\\SQLEXPRESS04;integratedSecurity=true;";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
        Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        String sql;
        sql = "use Lab6_map select * from Student where StudentId = " + String.valueOf(id);
        ResultSet resultSet = statement.executeQuery(sql);
        if (!resultSet.next())
            return null;
        long studentId = resultSet.getLong("StudentId");
        String firstName = resultSet.getString("FirstName");
        String lastName = resultSet.getString("LastName");
        int totalCredits = resultSet.getInt("TotalCredits");
        resultSet.close();
        String sql2;
        sql2 = "use Lab6_map select CourseId from Enrolled where StudentId = " + String.valueOf(id);
        ResultSet rs = statement.executeQuery(sql2);
        List<Long> enrolledCourses = new ArrayList<Long>();
        while (rs.next())
            enrolledCourses.add(rs.getLong("CourseId"));
        rs.close();
        statement.close();
        connection.close();
        Student student = new Student(studentId, firstName, lastName, totalCredits, enrolledCourses);
        return student;
    }

    @Override
    public Iterator findAll() throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {
        List<Student> studentList = new ArrayList<Student>();
        String url = "jdbc:sqlserver://DESKTOP-EINH3N1\\SQLEXPRESS04;integratedSecurity=true;";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
        Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        String sql;
        sql = "use Lab6_map select * from Student";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            long studentId = resultSet.getLong("StudentId");
            String firstName = resultSet.getString("FirstName");
            String lastName = resultSet.getString("LastName");
            int totalCredits = resultSet.getInt("TotalCredits");
            List<Long> enrolledCourses = new ArrayList<Long>();
            Statement statement2 = connection.createStatement();
            String sql2;
            sql2 = "use Lab6_map select CourseId from Enrolled where StudentId = " + String.valueOf(studentId);
            ResultSet rs = statement2.executeQuery(sql2);
            while (rs.next())
                enrolledCourses.add(rs.getLong("CourseId"));
            rs.close();
            statement2.close();
            studentList.add(new Student(studentId, firstName, lastName, totalCredits, enrolledCourses));
        }
        resultSet.close();
        statement.close();
        connection.close();
        return studentList.iterator();
    }

    @Override
    public Student save(Student entity) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (findOne(entity.getStudentId()) != null)
            return entity;
        String url = "jdbc:sqlserver://DESKTOP-EINH3N1\\SQLEXPRESS04;integratedSecurity=true;";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
        Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        String sql;
        sql = "use Lab6_map insert into Student values (" + String.valueOf(entity.getStudentId()) + ",'" + entity.getFirstName()
                + "','" + entity.getLastName() + "'," + String.valueOf(entity.getTotalCredits()) + ")";
        statement.execute(sql);
        //resultSet.close();
        for (int i = 0; i < entity.getEnrolledCourses().size(); i++) {
            Statement statement2 = connection.createStatement();
            String sql2;
            sql2 = "use Lab6_map insert into Enrolled values (" + String.valueOf(entity.getStudentId()) + "," +
                    String.valueOf(entity.getEnrolledCourses().get(i)) + ")";
            statement2.execute(sql2);
            //rs.close();
            statement2.close();
        }
        statement.close();
        connection.close();
        return null;
    }

    @Override
    public Student delete(Long id) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (findOne(id) == null)
            return null;
        Student student = findOne(id);
        String url = "jdbc:sqlserver://DESKTOP-EINH3N1\\SQLEXPRESS04;integratedSecurity=true;";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
        Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        String sql;
        sql = "use Lab6_map delete from Student where StudentId = " + String.valueOf(id);
        statement.execute(sql);
        //resultSet.close();
        statement.close();
        connection.close();
        return student;
    }

    @Override
    public Student update(Student entity) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (findOne(entity.getStudentId()) == null)
            return entity;
        delete(entity.getStudentId());
        save(entity);
        return null;
    }
}

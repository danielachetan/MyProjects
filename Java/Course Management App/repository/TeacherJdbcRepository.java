package lab3.repository;

import lab3.model.Student;
import lab3.model.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TeacherJdbcRepository implements ICrudRepository<Teacher> {
    //List<Teacher> teacherList = new ArrayList<Teacher>();

    /*
    public TeacherJdbcRepository() throws ClassNotFoundException, SQLException {
        try {
            String url = "jdbc:sqlserver://DESKTOP-EINH3N1\\SQLEXPRESS04;integratedSecurity=true;";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            String sql;
            sql = "use Lab6_map select * from Teacher";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                long teacherId = resultSet.getLong("TeacherId");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                teacherList.add(new Teacher(teacherId, firstName, lastName));
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

    public void deleteFromTeacherDB(long id) throws ClassNotFoundException, SQLException {
        try {
            String url = "jdbc:sqlserver://DESKTOP-EINH3N1\\SQLEXPRESS04;databaseName=Lab6_map;integratedSecurity=true;";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            String sql;
            sql = "delete from Teacher where TeacherId = " + String.valueOf(id);
            statement.executeQuery(sql);
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertIntoTeacherDB(Teacher teacher) throws ClassNotFoundException, SQLException {
        try {
            String url = "jdbc:sqlserver://DESKTOP-EINH3N1\\SQLEXPRESS04;databaseName=Lab6_map;integratedSecurity=true;";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            String sql;
            String auxEnrolledCourses = "";
            sql = "insert into Teacher (TeacherId, FirstName, LastName) values (" + String.valueOf(teacher.getTeacherId()) +
                    ",'" + teacher.getFirstName() + "','" + teacher.getLastName() + "')";
            statement.executeQuery(sql);
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Teacher findOne(Long id) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        String url = "jdbc:sqlserver://DESKTOP-EINH3N1\\SQLEXPRESS04;integratedSecurity=true;";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
        Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        String sql;
        sql = "use Lab6_map select * from Teacher where TeacherId = " + String.valueOf(id);
        ResultSet resultSet = statement.executeQuery(sql);
        if (!resultSet.next())
            return null;
        long teacherId = (long) resultSet.getInt("TeacherId");
        String firstName = resultSet.getString("FirstName");
        String lastName = resultSet.getString("LastName");
        Teacher teacher = new Teacher(teacherId,firstName,lastName);
        return teacher;
    }

    @Override
    public Iterator<Teacher> findAll() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        List<Teacher> teacherList = new ArrayList<Teacher>();
        String url = "jdbc:sqlserver://DESKTOP-EINH3N1\\SQLEXPRESS04;integratedSecurity=true;";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
        Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        String sql;
        sql = "use Lab6_map select * from Teacher";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            long teacherId = resultSet.getLong("TeacherId");
            String firstName = resultSet.getString("FirstName");
            String lastName = resultSet.getString("LastName");
            teacherList.add(new Teacher(teacherId, firstName, lastName));
        }
        resultSet.close();
        statement.close();
        connection.close();
        return teacherList.iterator();
    }

    @Override
    public Teacher save(Teacher entity) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (findOne(entity.getTeacherId()) != null)
            return entity;
        String url = "jdbc:sqlserver://DESKTOP-EINH3N1\\SQLEXPRESS04;integratedSecurity=true;";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
        Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        String sql;
        sql = "use Lab6_map insert into Teacher values (" + String.valueOf(entity.getTeacherId()) + ",'" +
                entity.getFirstName() + "','" + entity.getLastName() + "')";
        statement.execute(sql);
        //resultSet.close();
        statement.close();
        connection.close();
        return null;
    }

    @Override
    public Teacher delete(Long id) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (findOne(id) == null)
            return null;
        Teacher teacher = findOne(id);
        String url = "jdbc:sqlserver://DESKTOP-EINH3N1\\SQLEXPRESS04;integratedSecurity=true;";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
        Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        String sql;
        sql = "use Lab6_map delete from Teacher where TeacherId = " + String.valueOf(id);
        statement.execute(sql);
        //resultSet.close();
        statement.close();
        connection.close();
        return teacher;
    }

    @Override
    public Teacher update(Teacher entity) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (findOne(entity.getTeacherId()) == null)
            return entity;
        delete(entity.getTeacherId());
        save(entity);
        return null;
    }
}

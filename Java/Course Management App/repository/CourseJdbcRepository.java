package lab3.repository;

import lab3.model.Course;
import lab3.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CourseJdbcRepository implements ICrudRepository<Course> {
    //List<Course> courseList = new ArrayList<Course>();

    /*
    public CourseJdbcRepository() throws ClassNotFoundException, SQLException {
        try {
            String url = "jdbc:sqlserver://DESKTOP-EINH3N1\\SQLEXPRESS04;integratedSecurity=true;";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            String sql;
            sql = "use Lab6_map select * from Course";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                long courseId = resultSet.getLong("CourseId");
                String name = resultSet.getString("Name");
                long teacher = resultSet.getLong("Teacher");
                int maxEnrollment = resultSet.getInt("MaxEnrollment");
                int credits = resultSet.getInt("Credits");
                String strStudentsEnrolled = resultSet.getString("StudentsEnrolled");
                String[] auxStudentsEnrolled = strStudentsEnrolled.split(",");
                List<Long> studentsEnrolled = new ArrayList<Long>();
                Arrays.stream(auxStudentsEnrolled)
                        .forEach(e -> studentsEnrolled.add(Long.parseLong(e)));
                courseList.add(new Course(courseId, name, teacher, maxEnrollment, credits, studentsEnrolled));
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

    public void deleteFromCourseDB(long id) throws ClassNotFoundException, SQLException {
        try {
            String url = "jdbc:sqlserver://DESKTOP-EINH3N1\\SQLEXPRESS04;databaseName=Lab6_map;integratedSecurity=true;";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            String sql;
            sql = "delete from Course where CourseId = " + String.valueOf(id);
            statement.executeQuery(sql);
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertIntoCourseDB(Course course) throws ClassNotFoundException, SQLException {
        try {
            String url = "jdbc:sqlserver://DESKTOP-EINH3N1\\SQLEXPRESS04;databaseName=Lab6_map;integratedSecurity=true;";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            String sql;
            String auxStudentsEnrolled = "";
            for (int i = 0; i < course.getStudentsEnrolled().size(); i++)
                auxStudentsEnrolled += String.valueOf(course.getStudentsEnrolled().get(i)) + ",";
            auxStudentsEnrolled = auxStudentsEnrolled.substring(0,auxStudentsEnrolled.length()-1);
            sql = "insert into Course" +
                    " values (" + String.valueOf(course.getCourseId()) +
                    ",'" + course.getName() + "'," + String.valueOf(course.getTeacher()) + "," +
                    String.valueOf(course.getMaxEnrollment()) + "," + String.valueOf(course.getCredits()) + ",'" +
                    auxStudentsEnrolled + "')";
            statement.executeQuery(sql);
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Course findOne(Long id) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        String url = "jdbc:sqlserver://DESKTOP-EINH3N1\\SQLEXPRESS04;databaseName=Lab6_map;integratedSecurity=true;";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
        Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        String sql;
        sql = "use Lab6_map select * from Course where CourseId = " + String.valueOf(id);
        ResultSet resultSet = statement.executeQuery(sql);
        if (!resultSet.next())
            return null;
        long courseId = resultSet.getLong("CourseId");
        String name = resultSet.getString("Name");
        long teacher = resultSet.getLong("Teacher");
        int maxEnrollment = resultSet.getInt("MaxEnrollment");
        int credits = resultSet.getInt("Credits");
        resultSet.close();
        List<Long> studentsEnrolled = new ArrayList<Long>();
        String sql2;
        sql2 = "use Lab6_map select StudentId from Enrolled where CourseId = " + String.valueOf(id);
        ResultSet rs = statement.executeQuery(sql2);
        while (rs.next())
            studentsEnrolled.add(rs.getLong("StudentId"));
        rs.close();
        statement.close();
        connection.close();
        Course course = new Course(courseId,name,teacher,maxEnrollment,credits,studentsEnrolled);
        return course;
    }

    @Override
    public Iterator<Course> findAll() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        List<Course> courseList = new ArrayList<Course>();
        String url = "jdbc:sqlserver://DESKTOP-EINH3N1\\SQLEXPRESS04;integratedSecurity=true;";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
        Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        String sql;
        sql = "use Lab6_map select * from Course";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            long courseId = resultSet.getLong("CourseId");
            String name = resultSet.getString("Name");
            long teacher = resultSet.getLong("Teacher");
            int maxEnrollment = resultSet.getInt("MaxEnrollment");
            int credits = resultSet.getInt("Credits");
            Statement statement2 = connection.createStatement();
            String sql2;
            sql2 = "use Lab6_map select StudentId from Enrolled where CourseId = " + String.valueOf(courseId);
            ResultSet rs = statement2.executeQuery(sql2);
            List<Long> studentsEnrolled = new ArrayList<Long>();
            while (rs.next())
                studentsEnrolled.add(rs.getLong("StudentId"));
            statement2.close();
            rs.close();
            courseList.add(new Course(courseId, name, teacher, maxEnrollment, credits, studentsEnrolled));
        }
        resultSet.close();
        statement.close();
        connection.close();
        return courseList.iterator();
    }

    @Override
    public Course save(Course entity) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (findOne(entity.getCourseId()) != null)
            return entity;
        String url = "jdbc:sqlserver://DESKTOP-EINH3N1\\SQLEXPRESS04;integratedSecurity=true;";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
        Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        String sql;
        sql = "use Lab6_map insert into Course values (" + String.valueOf(entity.getCourseId()) + ",'" + entity.getName() + "'," +
                String.valueOf(entity.getTeacher()) + "," + String.valueOf(entity.getMaxEnrollment()) + "," +
                String.valueOf(entity.getCredits()) + ")";
        statement.execute(sql);
        //resultSet.close();
        for (int i = 0; i < entity.getStudentsEnrolled().size(); i++) {
            Statement statement2 = connection.createStatement();
            String sql2;
            sql2 = "use Lab6_map insert into Enrolled values (" + String.valueOf(entity.getStudentsEnrolled().get(i)) + "," +
                    String.valueOf(entity.getCourseId()) + ")";
            statement2.execute(sql2);
            //rs.close();
            statement2.close();
        }
        statement.close();
        connection.close();
        return null;
    }

    @Override
    public Course delete(Long id) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (findOne(id) == null)
            return null;
        Course course = findOne(id);
        String url = "jdbc:sqlserver://DESKTOP-EINH3N1\\SQLEXPRESS04;integratedSecurity=true;";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
        Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        String sql = "use Lab6_map delete from Course where CourseId = " + String.valueOf(id);
        statement.execute(sql);
        //resultSet.close();
        statement.close();
        connection.close();
        return course;
    }

    @Override
    public Course update(Course entity) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (findOne(entity.getCourseId()) == null)
            return entity;
        delete(entity.getCourseId());
        save(entity);
        return null;
    }
}

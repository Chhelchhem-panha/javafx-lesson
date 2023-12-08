package com.example.demojavafx.dao;

import com.example.demojavafx.entity.Department;
import com.example.demojavafx.entity.Student;
import com.example.demojavafx.util.DBUtil;
import com.example.demojavafx.util.DaoService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements DaoService<Student> {
    @Override
    public List<Student> fetchAll() throws SQLException, ClassNotFoundException{
        List<Student> students = new ArrayList<>();

        try (Connection connection = DBUtil.createConnection()) {
            String query = """
                        SELECT s.id, s.first_name, s.last_name, s.address, s.department_id, d.name FROM student s JOIN department d ON d.id = s.department_id;
                    """;
            try (PreparedStatement ps = connection.prepareStatement(query)){
                try (ResultSet rs = ps.executeQuery()){
                    while (rs.next() ) {
                        Department department = new Department();
                        department.setId(rs.getInt("department_id"));
                        department.setName(rs.getString("name"));

                        Student student = new Student();
                        student.setId(rs.getString("id"));
                        student.setFistName(rs.getString("first_name"));
                        student.setLastName(rs.getString("last_name"));
                        student.setAddress(rs.getString("address"));
                        student.setDepartment(department);

                        students.add(student);
                    }
                }
            }
        }

        return students;
    }

    @Override
    public int addData(Student student) throws SQLException, ClassNotFoundException{
        int result = 0;

        try (Connection connection = DBUtil.createConnection()) {
            String query ="""        
                    INSERT INTO student (id, first_name, last_name, address, department_id) VALUES (?, ?,?, ?, ?);
                    """;

            try (PreparedStatement ps = connection.prepareStatement(query)){
                ps.setString(1, student.getId());
                ps.setString(2, student.getFistName());
                ps.setString(3, student.getLastName());
                ps.setString(4, student.getAddress());
                ps.setInt(5, student.getDepartment().getId());

                if (ps.executeUpdate() != 0) {
                    result = 1;
                    connection.commit();
                } else {
                    connection.rollback();
                }
            }
        }
        return result;
    }

    @Override
    public int updateData(Student student) {
        return 0;
    }

    @Override
    public int deleteData(Student student) {
        return 0;
    }
}

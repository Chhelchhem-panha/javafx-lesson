package com.example.demojavafx.dao;

import com.example.demojavafx.entity.Department;
import com.example.demojavafx.util.DBUtil;
import com.example.demojavafx.util.DaoService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoImpl implements DaoService<Department> {
    @Override
    public List<Department> fetchAll() throws SQLException, ClassNotFoundException{
        List<Department> departments = new ArrayList<>();

        try (Connection connection = DBUtil.createConnection()) {
            String query = """
                SELECT id, name FROM department ORDER BY name;
                """;
//
            try (PreparedStatement ps = connection.prepareStatement(query)){
                try (ResultSet rs = ps.executeQuery()){
                    while (rs.next() ) {
                        Department department = new Department();
                        department.setId(rs.getInt("id"));
                        department.setName(rs.getString("name"));
                        departments.add(department);

                    }
                }
            }
        }

        return departments;
    }

    @Override
    public int addData(Department department) throws SQLException, ClassNotFoundException{
        int result = 0;

        try (Connection connection = DBUtil.createConnection()) {
            String query = """
                insert into department (id, name)
                values (?, ?);
            """;

            try (PreparedStatement ps = connection.prepareStatement(query)){
                ps.setInt(1, department.getId());
                ps.setString(2, department.getName());

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
    public int updateData(Department department) {
        int result = 0;

        try (Connection connection = DBUtil.createConnection()) {
            String query = """
                update department set name = ? where id = ?
            """;

            try (PreparedStatement ps = connection.prepareStatement(query)){
                ps.setInt(2, department.getId());
                ps.setString(1, department.getName());

                if (ps.executeUpdate() != 0) {
                    result = 1;
                    connection.commit();
                } else {
                    connection.rollback();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int deleteData(Department department) {
        int result = 0;

        try (Connection connection = DBUtil.createConnection()) {
            String query = """
                delete from department where id = ?
            """;

            try (PreparedStatement ps = connection.prepareStatement(query)){
                ps.setInt(1, department.getId());
                if (ps.executeUpdate() != 0) {
                    result = 1;
                    connection.commit();
                } else {
                    connection.rollback();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}

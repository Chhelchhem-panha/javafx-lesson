package com.example.demojavafx.dao;

import com.example.demojavafx.entity.Department;
import com.example.demojavafx.entity.User;
import com.example.demojavafx.util.DBUtil;
import com.example.demojavafx.util.DaoService;
import com.example.demojavafx.util.LoginService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements LoginService, DaoService<User> {
    @Override
    public List<User> fetchAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public int addData(User user) throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public int updateData(User user) throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public int deleteData(User user) throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public User login(String email, String password) throws SQLException, ClassNotFoundException{
        User user = null;
        try (Connection connection = DBUtil.createConnection()) {
            String query = """
                SELECT email, name FROM "user" WHERE email = ? AND password = ?;
                """;

            try (PreparedStatement ps = connection.prepareStatement(query)){
                ps.setString(1, email);
                ps.setString(2, password);
                try (ResultSet rs = ps.executeQuery()){
                    while (rs.next() ) {
                        user = new User();
                        user.setEmail(rs.getString("email"));
                        user.setName(rs.getString("name"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}

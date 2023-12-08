package com.example.demojavafx.util;

import com.example.demojavafx.entity.User;

import java.sql.SQLException;

public interface LoginService {
    User login(String email, String password) throws SQLException, ClassNotFoundException;
}

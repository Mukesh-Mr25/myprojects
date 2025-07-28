package com.hms.dao;

import com.hms.entity.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {

    private final JdbcTemplate jdbcTemplate;

    // Constructor injection of JdbcTemplate
    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // User Registration
    public boolean userRegister(User user) {
        try {
            String sql = "INSERT INTO user_details (full_name, email, password) VALUES (?, ?, ?)";
            
            jdbcTemplate.update(sql, 
                user.getFullName(), 
                user.getEmail(), 
                user.getPassword()
            );
            
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    // User Login
    public User loginUser(String email, String password) {
        try {
            String sql = "SELECT * FROM user_details WHERE email = ? AND password = ?";
            
            return jdbcTemplate.queryForObject(sql, 
                new Object[]{email, password}, 
                new BeanPropertyRowMapper<>(User.class)
            );
        } catch (Exception e) {
            return null;
        }
    }

    // Check Old Password
    public boolean checkOldPassword(int userId, String oldPassword) {
        try {
            String sql = "SELECT COUNT(*) FROM user_details WHERE id = ? AND password = ?";
            
            int count = jdbcTemplate.queryForObject(sql, 
                new Object[]{userId, oldPassword}, 
                Integer.class
            );
            
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Change Password
    public boolean changePassword(int userId, String newPassword) {
        try {
            String sql = "UPDATE user_details SET password = ? WHERE id = ?";
            
            jdbcTemplate.update(sql, newPassword, userId);
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
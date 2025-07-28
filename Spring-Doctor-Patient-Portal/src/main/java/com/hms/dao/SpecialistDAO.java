package com.hms.dao;

import com.hms.entity.Specialist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.dao.DataAccessException;

@Repository
public class SpecialistDAO {

    private final JdbcTemplate jdbcTemplate;

    public SpecialistDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Add a new specialist
    public boolean addSpecialist(String sp) {
        try {
            String sql = "INSERT INTO specialist (specialist_name) VALUES (?)";
            jdbcTemplate.update(sql, sp);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    // Get all specialists
    public List<Specialist> getAllSpecialist() {
        String sql = "SELECT * FROM specialist";
        return jdbcTemplate.query(sql, 
            new BeanPropertyRowMapper<>(Specialist.class));
    }
}
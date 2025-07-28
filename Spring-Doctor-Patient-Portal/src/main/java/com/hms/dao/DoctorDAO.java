package com.hms.dao;

import com.hms.entity.Doctor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.dao.DataAccessException;

@Repository
public class DoctorDAO {

    private final JdbcTemplate jdbcTemplate;

    public DoctorDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Register a new doctor
    public boolean registerDoctor(Doctor doctor) {
        try {
            String sql = "INSERT INTO doctor " +
                "(fullName, dateOfBirth, qualification, specialist, email, phone, password) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            jdbcTemplate.update(sql, 
                doctor.getFullName(),
                doctor.getDateOfBirth(),
                doctor.getQualification(),
                doctor.getSpecialist(),
                doctor.getEmail(),
                doctor.getPhone(),
                doctor.getPassword()
            );
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    // Get all doctors
    public List<Doctor> getAllDoctor() {
        String sql = "SELECT * FROM doctor ORDER BY id DESC";
        return jdbcTemplate.query(sql, 
            new BeanPropertyRowMapper<>(Doctor.class));
    }

    // Get doctor by ID
    public Doctor getDoctorById(int id) {
        try {
            String sql = "SELECT * FROM doctor WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, 
                new BeanPropertyRowMapper<>(Doctor.class));
        } catch (DataAccessException e) {
            return null;
        }
    }

    // Update doctor
    public boolean updateDoctor(Doctor doctor) {
        try {
            String sql = "UPDATE doctor SET " +
                "fullName = ?, dateOfBirth = ?, qualification = ?, " +
                "specialist = ?, email = ?, phone = ?, password = ? " +
                "WHERE id = ?";
            
            jdbcTemplate.update(sql, 
                doctor.getFullName(),
                doctor.getDateOfBirth(),
                doctor.getQualification(),
                doctor.getSpecialist(),
                doctor.getEmail(),
                doctor.getPhone(),
                doctor.getPassword(),
                doctor.getId()
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete doctor by ID
    public boolean deleteDoctorById(int id) {
        try {
            String sql = "DELETE FROM doctor WHERE id = ?";
            jdbcTemplate.update(sql, id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Doctor login
    public Doctor loginDoctor(String email, String password) {
        try {
            String sql = "SELECT * FROM doctor WHERE email = ? AND password = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{email, password}, 
                new BeanPropertyRowMapper<>(Doctor.class));
        } catch (DataAccessException e) {
            return null;
        }
    }

    // Count methods now use queryForObject for more efficient counting
    public int countTotalDoctor() {
        String sql = "SELECT COUNT(*) FROM doctor";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int countTotalAppointment() {
        String sql = "SELECT COUNT(*) FROM appointment";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int countTotalAppointmentByDoctorId(int doctorId) {
        String sql = "SELECT COUNT(*) FROM appointment WHERE doctorId = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{doctorId}, Integer.class);
    }

    public int countTotalUser() {
        String sql = "SELECT COUNT(*) FROM user_details";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int countTotalSpecialist() {
        String sql = "SELECT COUNT(*) FROM specialist";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    // Check old password
    public boolean checkOldPassword(int doctorId, String oldPassword) {
        try {
            String sql = "SELECT COUNT(*) FROM doctor WHERE id = ? AND password = ?";
            int count = jdbcTemplate.queryForObject(sql, new Object[]{doctorId, oldPassword}, Integer.class);
            return count > 0;
        } catch (DataAccessException e) {
            return false;
        }
    }

    // Change password
    public boolean changePassword(int doctorId, String newPassword) {
        try {
            String sql = "UPDATE doctor SET password = ? WHERE id = ?";
            jdbcTemplate.update(sql, newPassword, doctorId);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    // Edit doctor profile
    public boolean editDoctorProfile(Doctor doctor) {
        try {
            String sql = "UPDATE doctor SET " +
                "fullName = ?, dateOfBirth = ?, qualification = ?, " +
                "specialist = ?, email = ?, phone = ? " +
                "WHERE id = ?";
            
            jdbcTemplate.update(sql, 
                doctor.getFullName(),
                doctor.getDateOfBirth(),
                doctor.getQualification(),
                doctor.getSpecialist(),
                doctor.getEmail(),
                doctor.getPhone(),
                doctor.getId()
            );
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }
}
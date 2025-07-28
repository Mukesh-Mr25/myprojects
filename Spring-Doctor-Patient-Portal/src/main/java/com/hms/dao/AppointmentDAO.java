package com.hms.dao;

import com.hms.entity.Appointment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

@Repository
public class AppointmentDAO {

    public JdbcTemplate jdbcTemplate;
@Autowired
    public AppointmentDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Create appointment
    public boolean addAppointment(Appointment appointment) {
        try {
            String sql = "INSERT INTO appointment " +
                "(userId, fullName, gender, age, appointmentDate, email, phone, diseases, doctorId, address, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            jdbcTemplate.update(sql, 
                appointment.getUserId(),
                appointment.getFullName(),
                appointment.getGender(),
                appointment.getAge(),
                appointment.getAppointmentDate(),
                appointment.getEmail(),
                appointment.getPhone(),
                appointment.getDiseases(),
                appointment.getDoctorId(),
                appointment.getAddress(),
                appointment.getStatus()
            );
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    // Get appointments for a specific user
    public List<Appointment> getAllAppointmentByLoginUser(int userId) {
        String sql = "SELECT * FROM appointment WHERE userId = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, 
            new BeanPropertyRowMapper<>(Appointment.class));
    }

    // Get appointments for a specific doctor
    public List<Appointment> getAllAppointmentByLoginDoctor(int doctorId) {
        String sql = "SELECT * FROM appointment WHERE doctorId = ?";
        return jdbcTemplate.query(sql, new Object[]{doctorId}, 
            new BeanPropertyRowMapper<>(Appointment.class));
    }

    // Get appointment by ID
    public Appointment getAppointmentById(int id) {
        try {
            String sql = "SELECT * FROM appointment WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, 
                new BeanPropertyRowMapper<>(Appointment.class));
        } catch (DataAccessException e) {
            return null;
        }
    }

    // Update doctor's appointment comment status
    public boolean updateDrAppointmentCommentStatus(int apptId, int docId, String comment) {
        try {
            String sql = "UPDATE appointment SET status = ? WHERE id = ? AND doctorId = ?";
            jdbcTemplate.update(sql, comment, apptId, docId);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    // Get all appointments (for admin panel)
    public List<Appointment> getAllAppointment() {
        String sql = "SELECT * FROM appointment ORDER BY id DESC";
        return jdbcTemplate.query(sql, 
            new BeanPropertyRowMapper<>(Appointment.class));
    }
}
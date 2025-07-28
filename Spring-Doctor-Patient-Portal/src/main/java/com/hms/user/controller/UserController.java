/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hms.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hms.dao.AppointmentDAO;
import com.hms.dao.UserDAO;
import com.hms.entity.Appointment;
import com.hms.entity.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private AppointmentDAO appointmentDAO;

    // Login Endpoint (converted from UserLoginServlet)
    @PostMapping("/userLogin")
    public String userLogin(@RequestParam String email, 
                            @RequestParam String password, 
                            HttpSession session, 
                            RedirectAttributes redirectAttributes) {
        User user = userDAO.loginUser(email, password);
        
        if (user != null) {
            session.setAttribute("userObj", user);
            return "redirect:/index.jsp";
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "Invalid email or password");
            return "redirect:/user_login.jsp";
        }
    }

    // Logout Endpoint (converted from UserLogoutServlet)
    @GetMapping("/userLogout")
    public String userLogout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.removeAttribute("userObj");
        redirectAttributes.addFlashAttribute("successMsg", "User Logout Successfully.");
        return "redirect:/user_login.jsp";
    }

    // Change Password Endpoint (converted from ChangePasswordServlet)
    @PostMapping("/userChangePassword")
    public String changePassword(@RequestParam int userId, 
                                 @RequestParam String oldPassword, 
                                 @RequestParam String newPassword,
                                 HttpSession session, 
                                 RedirectAttributes redirectAttributes) {
        if (userDAO.checkOldPassword(userId, oldPassword)) {
            if (userDAO.changePassword(userId, newPassword)) {
                redirectAttributes.addFlashAttribute("successMsg", "Password Change Successfully.");
            } else {
                redirectAttributes.addFlashAttribute("errorMsg", "Something wrong on server!");
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "Old password incorrect");
        }
        return "redirect:/change_password.jsp";
    }

    // Add Appointment Endpoint (converted from AppointmentServlet)
    @PostMapping("/addAppointment")
    public String addAppointment(
            @RequestParam int userId,
            @RequestParam String fullName,
            @RequestParam String gender,
            @RequestParam String age,
            @RequestParam String appointmentDate,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String diseases,
            @RequestParam int doctorNameSelect,
            @RequestParam String address,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        Appointment appointment = new Appointment(
            userId, fullName, gender, age, appointmentDate, 
            email, phone, diseases, doctorNameSelect, address, "Pending"
        );
        
        boolean appointmentAdded = appointmentDAO.addAppointment(appointment);
        
        if (appointmentAdded) {
            redirectAttributes.addFlashAttribute("successMsg", "Appointment is recorded Successfully.");
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "Something went wrong on server!");
        }
        
        return "redirect:/user_appointment.jsp";
    }
    

    // User Registration (New Method)
    @PostMapping("/user_register")
    public String userRegister(@RequestParam String fullName,
                               @RequestParam String email,
                               @RequestParam String password,
                               RedirectAttributes redirectAttributes) {
        try {
            // Create User object
            User user = new User(fullName, email, password);

            // Attempt to register user
            boolean registrationSuccess = userDAO.userRegister(user);

            if (registrationSuccess) {
                redirectAttributes.addFlashAttribute("successMsg", "Register Successfully");
            } else {
                redirectAttributes.addFlashAttribute("errorMsg", "Something went wrong!");
            }
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMsg", "An error occurred during registration");
        }

        return "redirect:/signup.jsp";
    }
}
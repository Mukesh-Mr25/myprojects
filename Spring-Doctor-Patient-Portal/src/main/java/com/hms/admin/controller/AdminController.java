/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hms.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hms.dao.DoctorDAO;
import com.hms.dao.SpecialistDAO;
import com.hms.entity.Doctor;
import com.hms.entity.User;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adminLogin")  // Ensure this is present
public class AdminController {

    @Autowired
    private DoctorDAO doctorDAO;

    @Autowired
    private SpecialistDAO specialistDAO;
    // Admin Login Endpoint
    @PostMapping("/admin/index")
    
    public String adminLogin(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session, RedirectAttributes redirectAttributes) {

        // Static admin credentials check
        if ("mukesh25.mariappan@gmail.com".equals(email) && "12345".equals(password)) {
            session.setAttribute("adminObj", new User());
            return "redirect:/admin/index";
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "Invalid Username or Password.");
            return "redirect:/admin_login";
        }
    }

    // Admin Logout Endpoint
    @GetMapping("/adminLogout")
    public String adminLogout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.removeAttribute("adminObj");
        redirectAttributes.addFlashAttribute("successMsg", "Admin Logout Successfully");
        return "redirect:/admin_login";
    }

    // Doctor Management Endpoints
    @PostMapping("/addDoctor")
    public String addDoctor(
            @RequestParam String fullName,
            @RequestParam String dateOfBirth,
            @RequestParam String qualification,
            @RequestParam String specialist,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String password,
            RedirectAttributes redirectAttributes) {

        try {
            Doctor doctor = new Doctor(fullName, dateOfBirth, qualification, specialist, email, phone, password);

            boolean f = doctorDAO.registerDoctor(doctor);

            if (f) {
                redirectAttributes.addFlashAttribute("successMsg", "Doctor added Successfully");
            } else {
                redirectAttributes.addFlashAttribute("errorMsg", "Something went wrong on server!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", "Error adding doctor");
        }

        return "redirect:admin/doctor";
    }

    // Delete Doctor Endpoint
    @GetMapping("/deleteDoctor")
    public String deleteDoctor(
            @RequestParam int id,
            RedirectAttributes redirectAttributes) {

        boolean f = doctorDAO.deleteDoctorById(id);

        if (f) {
            redirectAttributes.addFlashAttribute("successMsg", "Doctor Deleted Successfully.");
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "Something went wrong on server!");
        }

        return "redirect:admin/view_doctor";
    }

    // Specialist Management Endpoint
    @PostMapping("/addSpecialist")
    public String addSpecialist(
            @RequestParam String specialistName,
            RedirectAttributes redirectAttributes) {

        boolean f = specialistDAO.addSpecialist(specialistName);

        if (f) {
            redirectAttributes.addFlashAttribute("successMsg", "Specialist added Successfully.");
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "Something went wrong on server");
        }

        return "redirect:admin/index";
    }

    // Update Doctor Endpoint
    @PostMapping("/updateDoctor")
    public String updateDoctor(
            @RequestParam int id,
            @RequestParam String fullName,
            @RequestParam String dateOfBirth,
            @RequestParam String qualification,
            @RequestParam String specialist,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String password,
            RedirectAttributes redirectAttributes) {

        try {
            // Create Doctor object with ID for update
            Doctor doctor = new Doctor(id, fullName, dateOfBirth, qualification, specialist, email, phone, password);

            boolean f = doctorDAO.updateDoctor(doctor);

            if (f) {
                redirectAttributes.addFlashAttribute("successMsg", "Doctor updated Successfully");
            } else {
                redirectAttributes.addFlashAttribute("errorMsg", "Something went wrong on server!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", "Error updating doctor");
        }

        return "redirect:admin/view_doctor";
    }

    // Alternative method using @ModelAttribute if you prefer object binding
    @PostMapping("/updateDoctorAlt")
    public String updateDoctorAlternative(
            @ModelAttribute Doctor doctor,
            RedirectAttributes redirectAttributes) {

        try {
            boolean f = doctorDAO.updateDoctor(doctor);

            if (f) {
                redirectAttributes.addFlashAttribute("successMsg", "Doctor updated Successfully");
            } else {
                redirectAttributes.addFlashAttribute("errorMsg", "Something went wrong on server!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", "Error updating doctor");
        }

        return "redirect:admin/view_doctor";
    }
}

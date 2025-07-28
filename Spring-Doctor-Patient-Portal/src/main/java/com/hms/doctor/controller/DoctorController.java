/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.hms.doctor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hms.dao.DoctorDAO;
import com.hms.dao.AppointmentDAO;
import com.hms.entity.Doctor;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorDAO doctorDAO;

    @Autowired
    private AppointmentDAO appointmentDAO;

    @PostMapping("/login")
    public String performLogin(
            @RequestParam String email, 
            @RequestParam String password, 
            HttpSession session, 
            RedirectAttributes redirectAttributes) {

        Doctor doctor = doctorDAO.loginDoctor(email, password);

        if (doctor != null) {
            session.setAttribute("doctorObj", doctor);
            return "redirect:/doctor/index";
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "Invalid email or password");
            return "redirect:/doctor-login";
        }
    }

    @GetMapping("/logout")
    public String performLogout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.removeAttribute("doctorObj");
        redirectAttributes.addFlashAttribute("successMsg", "Doctor Logout Successfully.");
        return "redirect:/doctor-login";
    }

    @PostMapping("/edit-profile")
    public String editDoctorProfile(
            @RequestParam int doctorId,
            @RequestParam String fullName,
            @RequestParam String dateOfBirth,
            @RequestParam String qualification,
            @RequestParam String specialist,
            @RequestParam String email,
            @RequestParam String phone,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        try {
            Doctor doctor = new Doctor(doctorId, fullName, dateOfBirth, qualification, 
                                       specialist, email, phone, "");

            boolean updateSuccess = doctorDAO.editDoctorProfile(doctor);

            if (updateSuccess) {
                Doctor updatedDoctorObj = doctorDAO.getDoctorById(doctorId);
                session.setAttribute("doctorObj", updatedDoctorObj);
                redirectAttributes.addFlashAttribute("successMsgForD", "Doctor update Successfully");
            } else {
                redirectAttributes.addFlashAttribute("errorMsgForD", "Something went wrong on server!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsgForD", "Error updating profile");
        }

        return "redirect:/doctor/edit-profile";
    }

    @PostMapping("/change-password")
    public String changeDoctorPassword(
            @RequestParam int doctorId,
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            RedirectAttributes redirectAttributes) {

        if (doctorDAO.checkOldPassword(doctorId, oldPassword)) {
            if (doctorDAO.changePassword(doctorId, newPassword)) {
                redirectAttributes.addFlashAttribute("successMsg", "Password changed successfully.");
            } else {
                redirectAttributes.addFlashAttribute("errorMsg", "Something went wrong on server!");
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "Old Password does not match");
        }

        return "redirect:/doctor/edit-profile";
    }

    @PostMapping("/update-appointment-status")
    public String updateAppointmentStatus(
            @RequestParam int id,
            @RequestParam int doctorId,
            @RequestParam String comment,
            RedirectAttributes redirectAttributes) {

        try {
            boolean updateSuccess = appointmentDAO.updateDrAppointmentCommentStatus(id, doctorId, comment);
            
            if (updateSuccess) {
                redirectAttributes.addFlashAttribute("successMsg", "Comment updated");
            } else {
                redirectAttributes.addFlashAttribute("errorMsg", "Something went wrong on server!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", "Error updating appointment status");
        }

        return "redirect:/doctor/patient";
    }
}

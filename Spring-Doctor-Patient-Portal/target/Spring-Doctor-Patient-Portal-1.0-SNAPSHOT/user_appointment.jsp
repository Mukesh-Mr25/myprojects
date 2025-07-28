<%@ page import="com.hms.dao.AppointmentDAO" %>
<%@ page import="com.hms.entity.Doctor" %>
<%@ page import="java.util.List" %>
<%@ page import="com.hms.entity.Appointment" %>
<%@ page import="java.time.LocalDate" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page isELIgnored="false"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Appointment Page</title>
    
    <!-- Include all CSS -->
    <%@include file="../component/allcss.jsp"%>
    
    <style type="text/css">
        .my-card {
            box-shadow: 0px 0px 10px 1px maroon;
        }
        
        .my-bg-img {
            background: linear-gradient(rgba(0, 0, 0, .4), rgba(0, 0, 0, .4)), url("img/hospital1.jpg");
            height: 20vh;
            width: 100%;
            background-size: cover;
            background-repeat: no-repeat;
        }
    </style>
</head>

<body>
    <%@include file="component/navbar.jsp"%>

    <!-- Background Image Section -->
    <div class="container-fluid my-bg-img p-5">
        <p class="text-center fs-2 text-white"></p>
    </div>

    <!-- Appointment Form Section -->
    <div class="container p-3">
        <div class="row">
            <!-- Image Column -->
            <div class="col-md-6 p-5">
                <img alt="Doctor" src="img/doc1.jpg" width="370">
            </div>
            
            <!-- Form Column -->
            <div class="col-md-6">
                <div class="card my-card">
                    <div class="card-body">
                        <p class="text-center fs-3">User Appointment</p>

                        <!-- Success and Error Message Display -->
                        <%
                        String successMsg = (String) session.getAttribute("successMsg");
                        String errorMsg = (String) session.getAttribute("errorMsg");

                        if (successMsg != null) {
                        %>
                            <p class="text-center text-success fs-5"><%= successMsg %></p>
                        <%
                            session.removeAttribute("successMsg");
                        }

                        if (errorMsg != null) {
                        %>
                            <p class="text-center text-danger fs-5"><%= errorMsg %></p>
                        <%
                            session.removeAttribute("errorMsg");
                        }
                        %>

                        <!-- Appointment Booking Form -->
                        <form class="row g-3" action="addAppointment" method="post">
                            <!-- Hidden User ID Field -->
                            <input type="hidden" name="userId" value="${ userObj.id }">
                            
                            <!-- Form Fields -->
                            <div class="col-md-6">
                                <label class="form-label">Full Name</label>
                                <input required="required" name="fullName" type="text" 
                                       placeholder="Enter full name" class="form-control">
                            </div>

                            <div class="col-md-6">
                                <label class="form-label">Gender</label>
                                <select class="form-control" name="gender" required="required">
                                    <option selected="selected" disabled="disabled">---Select Gender---</option>
                                    <option value="male">Male</option>
                                    <option value="female">Female</option>
                                </select>
                            </div>

                            <div class="col-md-6">
                                <label class="form-label">Age</label>
                                <input name="age" required="required" type="number" 
                                       placeholder="Enter your Age" class="form-control">
                            </div>

                            <div class="col-md-6">
                                <label class="form-label">Appointment Date</label>
                                <input required="required" name="appointmentDate" type="date" class="form-control">
                            </div>

                            <div class="col-md-6">
                                <label class="form-label">Email</label>
                                <input name="email" required="required" type="email" 
                                       placeholder="Enter email" class="form-control">
                            </div>

                            <div class="col-md-6">
                                <label class="form-label">Phone</label>
                                <input name="phone" required="required" type="number" 
                                       maxlength="11" placeholder="Enter Mobile no." class="form-control">
                            </div>

                            <div class="col-md-6">
                                <label class="form-label">Diseases</label>
                                <input required="required" name="diseases" type="text" 
                                       placeholder="Enter diseases" class="form-control">
                            </div>

                            <div class="col-md-6">
                                <label class="form-label">Doctor</label>
                                <select class="form-control" name="doctorId" required="required">
                                    <option selected="selected" disabled="disabled">---Select---</option>
                                    
                                  <c:forEach var="doctor" items="${listOfDoctors}">
        <tr>
            <td>${doctor.name}</td>
            <td>${doctor.specialization}</td>
        </tr>
    </c:forEach>
                                    
                                </select>
                            </div>

                            <div class="col-md-12">
                                <label class="form-label">Full Address</label>
                                <textarea name="address" required="required" 
                                          class="form-control" rows="3"></textarea>
                            </div>

                            <!-- Conditional Submit Button -->
                            <c:if test="${empty userObj}">
                                <div class="col-md-12">
                                    <a href="user_login.jsp" 
                                       class="btn my-bg-color text-white col-md-12">Submit</a>
                                </div>
                            </c:if>

                            <c:if test="${not empty userObj}">
                                <div class="col-md-12">
                                    <button type="submit" 
                                            class="btn my-bg-color text-white col-md-12">Submit</button>
                                </div>
                            </c:if>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <%@include file="component/footer.jsp" %>
</body>
</html>
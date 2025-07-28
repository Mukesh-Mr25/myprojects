<%@page import="com.hms.entity.Doctor"%>
<%@page import="com.hms.dao.DoctorDAO"%>
<%@page import="com.hms.entity.Appointment"%>
<%@page import="java.util.List"%>
<%@ page import="com.hms.config.DatabaseConfig" %>
<%@page import="com.hms.dao.AppointmentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- for jstl tag -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- end of jstl tag -->

<%@page isELIgnored="false"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Patient Details | Admin</title>

<%@include file="../component/allcss.jsp"%>

<!-- customs css for this page -->
<style type="text/css">
.my-card {
	box-shadow: 0px 0px 10px 1px maroon;
	/*box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.3);*/
}
</style>
<!-- end of customs css for this page -->

</head>
<body>
	<%@include file="navbar.jsp"%>

	<div class="col-md-12 p-5">
		<div class="card my-card">
			<div class="card-body">
				<p class="text-center text-danger fs-3">Patient Details</p>

				<table class="table table-success table-striped">
					<thead>
						<tr class="table">
							<th scope="col" style="padding-right:70px">Full Name</th>
							<th scope="col">Gender</th>
							<th scope="col">Age</th>
							<th scope="col">Appointment</th>
							<th scope="col">Email</th>
							<th scope="col">Phone</th>
							<th scope="col" style="padding-right:50px">Diseases</th>
							<th scope="col" style="padding-right:70px">Doctor Name</th>
							<th scope="col">Address</th>
							<th scope="col" style="padding-right:100px">Status</th>

						</tr>
					</thead>
					<tbody>
                                            <c:forEach var="patient" items="${appList}">
						
						<tr>
							<td>${patient.fullName}</td>
                                                        <td>${patient.gender}</td>
                                                        <td>${patient.age}</td>
                                                        <td>${patient.appointmentDate}</td>
							<td>${patient.email}</td>
							<td>${patient.phone}</td>
							<td>${patient.diseases}</td>
							<td>${doctor.fullName}</td>
							<td>${patient.address}</td>
							<td>${patient.status}</td>

						</tr>
					 </c:forEach>
						
				</tbody>
				</table>

			</div>


		</div>

	</div>




</body>
</html>
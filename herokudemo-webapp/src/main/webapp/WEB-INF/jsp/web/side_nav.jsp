<%--
  Created by IntelliJ IDEA.
  User: anh.ndv
  Date: 11/10/2017
  Time: 14:46
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<div class="col-sm-3 sidenav hidden-xs" style="min-height: 100vh;">
    <h2>LVTN SERVER</h2>
    <ul class="nav nav-pills nav-stacked">
        <li <% if(pageName.equals("manager.jsp")){ %> class="active" <%}%>><a href="/managers">NGƯỜI QUẢN LÝ</a></li>
        <li <% if(pageName.equals("employee.jsp")){ %> class="active" <%}%>><a href="/employees">NHÂN VIÊN</a></li>
        <li <% if(pageName.equals("appointment.jsp")){ %> class="active" <%}%>><a href="/appointments">DANH SÁCH CUỘC HẸN</a></li>
        <li <% if(pageName.equals("vehicle.jsp")){ %> class="active" <%}%>><a href="/vehicles">PHƯƠNG TIỆN</a></li>
        <li><a href="/logout">ĐĂNG XUẤT</a></li>
    </ul><br>
</div>
<br>
</html>
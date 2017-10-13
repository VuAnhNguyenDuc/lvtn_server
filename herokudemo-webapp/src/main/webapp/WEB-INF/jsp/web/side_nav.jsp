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

<%
    String pageName = "";
    if(request.getAttribute("pageName") != null){
        pageName = request.getAttribute("pageName").toString();
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<div class="col-sm-3 sidenav hidden-xs" style="min-height: 100vh;">
    <h2>LVTN SERVER</h2>
    <ul class="nav nav-pills nav-stacked">
        <li <% if(pageName.equals("manager")){ %> class="active" <%}%>><a href="/managers">MANAGERS</a></li>
        <li <% if(pageName.equals("employee")){ %> class="active" <%}%>><a href="/employees">EMPLOYEES</a></li>
        <li <% if(pageName.equals("appointment")){ %> class="active" <%}%>><a href="/appointments">APPOINTMENTS</a></li>
        <li <% if(pageName.equals("vehicle")){ %> class="active" <%}%>><a href="/vehicles">VEHICLES</a></li>
        <li><a href="/logout">LOG OUT</a></li>
    </ul><br>
</div>
<br>
</html>
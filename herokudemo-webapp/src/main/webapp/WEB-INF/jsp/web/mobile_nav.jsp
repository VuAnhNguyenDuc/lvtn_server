<%--
  Created by IntelliJ IDEA.
  User: anh.ndv
  Date: 11/10/2017
  Time: 14:49
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
<nav class="navbar navbar-inverse visible-xs">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/home">EMS SERVER</a>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">
                <li <% if(pageName.equals("manager")){ %> class="active" <%}%>><a href="/managers">MANAGERS</a></li>
                <li <% if(pageName.equals("employee")){ %> class="active" <%}%>><a href="/employees">EMPLOYEES</a></li>
                <li <% if(pageName.equals("client")){ %> class="active" <%}%>><a href="/clients">CLIENTS</a></li>
                <li <% if(pageName.equals("appointment")){ %> class="active" <%}%>><a href="/appointments?type=all">APPOINTMENTS</a></li>
                <li <% if(pageName.equals("vehicle")){ %> class="active" <%}%>><a href="/vehicles">VEHICLES</a></li>
                <li <% if(pageName.equals("specialPlace")){ %> class="active" <%}%>><a href="/specialPlaces">SPECIAL PLACES</a></li>
                <li><a href="/logout">LOG OUT</a></li>
            </ul>
        </div>
    </div>
</nav>
</html>
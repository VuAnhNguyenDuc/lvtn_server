<%--
  Created by IntelliJ IDEA.
  User: anh.ndv
  Date: 20/11/2017
  Time: 14:32
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>INSERT SPECIAL PLACE</title>
    <jsp:include page="../header.jsp"/>
</head>
<body>
    <jsp:include page="../mobile_nav.jsp"/>
    <jsp:include page="../side_nav.jsp"/>
    <div class="col-sm-9 col-lg-9 col-sm-12 col-xs-12" style="padding-top: 30px">
        <div class="row" style="height: 15vh;"></div>
        <form:form modelAttribute="specialPlace" method="post">
            <div class="form-group>">
                <label for="name">Name: </label>
                <form:input path="name" type="text" class="form-control" id="name"/>
                <form:errors path="name" cssClass="form-error" class="form-control"/>
            </div>

            <div class="form-group>">
                <label for="type">Type: </label>
                <form:select path="type" class="form-control" id="type">
                    <form:option value="Airport" >Airport</form:option>
                    <form:option value="Train Station" >Train Station</form:option>
                    <form:option value="Harbor" >Harbor</form:option>
                </form:select>

                <form:errors path="type" cssClass="form-error" class="form-control"/>
            </div>

            <div class="form-group>">
                <label for="range">Range (in meters): </label>
                <form:input path="range" type="text" class="form-control" id="range"/>
                <form:errors path="range" cssClass="form-error" class="form-control"/>
            </div>

            <div class="form-group>">
                <label for="latitude">Latitude: </label>
                <form:input path="latitude" type="number" class="form-control" id="latitude"/>
                <form:errors path="latitude" cssClass="form-error" class="form-control"/>
            </div>

            <div class="form-group>">
                <label for="longitude">Longitude: </label>
                <form:input path="longitude" type="number" class="form-control" id="longitude"/>
                <form:errors path="longitude" cssClass="form-error" class="form-control"/>
            </div>


            <div class="form-group">
                <c:if test="${not empty error}">
                    <label class="form-error">${error}</label>
                </c:if>
            </div>
            <div class="text-center" style="margin-top : 60px; margin-bottom: 60px;">
                <button type="submit" class="btn btn-primary">
                    ADD
                </button>
            </div>
        </form:form>
    </div>
</body>
</html>
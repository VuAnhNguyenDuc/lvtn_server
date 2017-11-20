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
    <title>UPDATE SPECIAL PLACE</title>
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
                <form:input path="name" type="text" class="form-control" id="name" value="${specialPlace.name}"/>
                <form:errors path="name" cssClass="form-error" class="form-control"/>
            </div>

            <div class="form-group>">
                <label for="type">Type: </label>
                <form:select path="type" class="form-control" id="type">
                    <c:choose>
                        <c:when test="${specialPlace.type == 'Airport'}">
                            <form:option value="Airport" selected="true">Airport</form:option>
                            <form:option value="Train Station">Train Station</form:option>
                            <form:option value="Harbor">Harbor</form:option>
                        </c:when>
                        <c:when test="${specialPlace.type == 'Train Station'}">
                            <form:option value="Airport">Airport</form:option>
                            <form:option value="Train Station" selected="true">Train Station</form:option>
                            <form:option value="Harbor">Harbor</form:option>
                        </c:when>
                        <c:otherwise>
                            <form:option value="Airport">Airport</form:option>
                            <form:option value="Train Station">Train Station</form:option>
                            <form:option value="Harbor" selected="true">Harbor</form:option>
                        </c:otherwise>
                    </c:choose>
                </form:select>
                <form:errors path="type" cssClass="form-error" class="form-control"/>
            </div>

            <div class="form-group>">
                <label for="range">Range: </label>
                <form:input path="range" type="text" class="form-control" id="range" value="${specialPlace.range}"/>
                <form:errors path="range" cssClass="form-error" class="form-control"/>
            </div>

            <div class="form-group>">
                <label for="latitude">Latitude: </label>
                <form:input path="latitude" type="number" class="form-control" id="latitude" value="${specialPlace.latitude}"/>
                <form:errors path="latitude" cssClass="form-error" class="form-control"/>
            </div>

            <div class="form-group>">
                <label for="longitude">Longitude: </label>
                <form:input path="longitude" type="number" class="form-control" id="longitude" value="${specialPlace.longitude}"/>
                <form:errors path="longitude" cssClass="form-error" class="form-control"/>
            </div>

            <div class="form-group>">
                <label for="status">Status: </label>
                <form:select path="status" class="form-control" id="status">
                    <c:choose>
                        <c:when test="${specialPlace.status == 1}">
                            <form:option value="1" selected="true">Active</form:option>
                            <form:option value="-1">Inactive</form:option>
                        </c:when>
                        <c:otherwise>
                            <form:option value="1" >Active</form:option>
                            <form:option value="-1" selected="true">Inactive</form:option>
                        </c:otherwise>
                    </c:choose>
                </form:select>

                <form:errors path="status" cssClass="form-error" class="form-control"/>
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
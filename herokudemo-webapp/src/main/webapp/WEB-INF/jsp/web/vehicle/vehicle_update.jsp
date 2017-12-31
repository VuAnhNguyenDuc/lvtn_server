<%--
  Created by IntelliJ IDEA.
  User: anh.ndv
  Date: 11/10/2017
  Time: 14:40
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
    <title>UPDATE VEHICLE</title>
    <jsp:include page="../header.jsp"/>
</head>
<body>
    <jsp:include page="../mobile_nav.jsp"/>
    <jsp:include page="../side_nav.jsp"/>
    <div class="col-sm-9 col-lg-9 col-sm-12 col-xs-12" style="padding-top: 30px">
        <div class="row" style="height: 15vh;"></div>
        <form:form modelAttribute="vehicle" method="post">
            <div class="form-group>">
                <label for="name">Vehicle name: </label>
                <form:input path="name" type="text" class="form-control" id="name" value="${vehicle.name}"/>
                <form:errors path="name" cssClass="form-error" class="form-control"/>
                <%--<input type="text" class="form-control" id="name" value="${vehicle.name}" readonly="readonly"/>--%>
            </div>

            <div class="form-group>">
                <label for="is_calculatable">Is calculable: </label>
                <form:input path="is_calculatable" type="checkbox" class="form-control" id="is_calculatable" value="${vehicle.is_calculatable}" step="0.1"/>
                <form:errors path="is_calculatable" cssClass="form-error" class="form-control"/>
            </div>

            <div class="form-group>">
                <label for="warning_rate">Warning rate: </label>
                <form:input path="warning_rate" type="number" class="form-control" id="warning_rate" value="${vehicle.warning_rate}" step="0.1"/>
                <form:errors path="warning_rate" cssClass="form-error" class="form-control"/>
            </div>

            <div class="form-group>">
                <label for="status">Status: </label>
                <form:select path="status" class="form-control" id="status">
                    <c:choose>
                        <c:when test="${vehicle.status == 1}">
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

            <c:if test="${not empty error}">
                <p class="form-error">${error}</p>
            </c:if>
            <div class="text-center" style="margin-top : 60px; margin-bottom: 60px;">
                <button type="submit" class="btn btn-primary">
                    UPDATE
                </button>
            </div>
        </form:form>
    </div>
</body>
</html>
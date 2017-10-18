<%--
  Created by IntelliJ IDEA.
  User: anh.ndv
  Date: 18/10/2017
  Time: 14:20
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
    <title>UPDATE EMPLOYEE</title>
    <jsp:include page="../header.jsp"/>
</head>
<body>
    <jsp:include page="../mobile_nav.jsp"/>
    <jsp:include page="../side_nav.jsp"/>
    <div class="col-sm-9 col-lg-9 col-sm-12 col-xs-12" style="padding-top: 30px">
        <div class="row" style="height: 15vh;"></div>
        <form:form modelAttribute="employeeForm" method="post">
            <div class="form-group>">
                <label for="full_name">Full name: </label>
                <form:input path="full_name" type="text" class="form-control" id="full_name" value="${employeeForm.full_name}"/>
                <form:errors path="full_name" cssClass="form-error" class="form-control"/>
            </div>

            <div class="form-group>">
                <label for="username">Username: </label>
                <form:input path="username" type="text" class="form-control" id="username" value="${employeeForm.username}" disabled="true"/>
                <form:errors path="username" cssClass="form-error" class="form-control"/>
            </div>

            <div class="form-group>">
                <label for="email">Email: </label>
                <form:input path="email" type="text" class="form-control" id="email" value="${employeeForm.email}"/>
                <form:errors path="email" cssClass="form-error" class="form-control"/>
            </div>

            <div class="form-group>">
                <label for="manager_id">Manager: </label>
                <form:select path="manager_id" class="form-control" id="manager">
                    <c:forEach items="${mngSelectList}" var="mng">
                        <c:choose>
                            <c:when test="${employeeForm.manager_id == mng.user_id}">
                                <form:option value="${mng.user_id}" selected="true">${mng.username}</form:option>
                            </c:when>
                            <c:otherwise>
                                <form:option value="${mng.user_id}">${mng.username}</form:option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </form:select>
            </div>

            <div class="form-group>">
                <label for="status">Status: </label>
                <form:select path="status" class="form-control" id="status">
                    <c:choose>
                        <c:when test="${employeeForm.status == 1}">
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
                    UPDATE
                </button>
            </div>
        </form:form>
    </div>
</body>
</html>
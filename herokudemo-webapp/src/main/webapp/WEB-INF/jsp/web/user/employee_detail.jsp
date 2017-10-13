<%--
  Created by IntelliJ IDEA.
  User: anh.ndv
  Date: 11/10/2017
  Time: 14:42
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
    <title>EMPLOYEE DETAILS</title>
</head>
<body>
    <table class="table">
        <%--<thead>
        <tr>
            <th>Thuộc tính</th>
            <th>Giá trị</th>
        </tr>
        </thead>--%>
        <tbody>
        <tr>
            <td>Username</td>
            <td>${emp.username}</td>
        </tr>
        <tr>
            <td>Email</td>
            <td>${emp.email}</td>
        </tr>
        <tr>
            <td>Manager Name</td>
            <td>${emp.manager_name}</td>
        </tr>
        <tr>
            <td>Total amount of appointment taken</td>
            <td>${total}</td>
        </tr>
        </tbody>
    </table>

    <p>Amount of appointment taken compare by months in a year : </p>
    <p>Please input a year</p>
    <input type="number" id="yearInput" />
    <button type="button" class="btn btn-primary" id="month-list">View as chart</button>
    <button type="button" class="btn btn-success" id="month-chart">View as list</button>

    <p>Amount of appointment taken throughout a period : </p>
    <p>Please input the start year</p>
    <input type="number" id="from" />
    <p>Please input the end year</p>
    <input type="number" id="to" />
    <button type="button" class="btn btn-primary" id="year-list">View as chart</button>
    <button type="button" class="btn btn-success" id="year-chart">View as list</button>


    <div id="result-list">

    </div>
</body>
<script type="application/javascript">
    var host =  "https://lvtn-server.herokuapp.com/"; // localhost
    $("#month-list").click(function () {
        var yearInput = $("#yearInput").val();
        if(yearInput != ""){
            $.ajax({
                type:"GET",
                url: "ajax/appointment/month",
                data : "id=${ID}&isCreated=false&&year="+yearInput,
                dataType : "text",
                cache : false,
                success: function(result){
                    var dataArr = $.parseJSON(result);
                    $("#result-list").html("");
                    $("#result-list").html(populateResultList(dataArr));
                },
                error: function (xhr) {
                    var err = eval("(" + xhr.responseText + ")");
                    alert(err.Message);
                }
            });
        } else{
            alert("Please input year value");
        }
    });
    $("#month-chart").click(function () {
        var yearInput = $("#yearInput").val();
        if(yearInput != ""){
            var newUrl = "https://lvtn-server.herokuapp.com/"
            console.log(newUrl);
            var newTab = window.open(newUrl);
            if(newTab){
                newTab.focus();
            } else{
                alert("Cannot create a new tab");
            }
        } else{
            alert("Please input year value");
        }
    });

    $("#year-list").click(function () {
        var from = $("#from").val();
        var to = $("#to").val();
        if(from != "" && to != ""){
            $.ajax({
                type:"GET",
                url: "ajax/year/list",
                data : "id=${ID}&from="+from+"&to="+to,
                dataType : "text",
                cache : false,
                success: function(result){
                    var dataArr = $.parseJSON(result);
                    $("#result-list").html("");
                    $("#result-list").html(populateReseltList(dataArr));
                },
                error: function (xhr) {
                    var err = eval("(" + xhr.responseText + ")");
                    alert(err.Message);
                }
            });
        } else{
            alert("Please input year value");
        }
    });
    $("#year-chart").click(function () {
        var from = $("#from").val();
        var to = $("#to").val();
        if(from != "" && to != ""){
            var newUrl = "http://".concat(host,":8080/admin/employee/getAppointmentsByYear?id=",${ID},"&from=",from,"&to=",to);
            console.log(newUrl);
            var newTab = window.open(newUrl);
            if(newTab){
                newTab.focus();
            } else{
                alert("Cannot create a new tab");
            }
        } else{
            alert("Please input year values");
        }
    });

    function populateReseltList(data){
        var table = "";
        var table_body = "";
        var table_head = "<table class=\"table table-hover\">\n" +
            "        <thead>\n" +
            "        <tr>\n" +
            "            <th>Mã</th>\n" +
            "            <th>Tên công tác</th>\n" +
            "            <th>Người tạo ra</th>\n" +
            "            <th>Giờ bắt đầu</th>\n" +
            "            <th>Trạng thái</th>\n" +
            "        </tr>\n" +
            "        </thead>\n" +
            "        <tbody>";
        for(var i = 0; i < data.length; i++){
            var obj = data[i];
            table_body += "<tr>\n" +
                "                <td>"+obj.appointment_id+"</td>\n" +
                "                <td>"+obj.appointment_name+"</td>\n" +
                "                <td>"+obj.created_by+"</td>\n" +
                "                <td>"+obj.start_date+"</td>\n" +
                "                <td>"+obj.status+"</td>\n" +
                "            </tr>\n";
        }
        var table_end = "</tbody>\n" +
            "    </table>";

        table = table_head + table_body + table_end;
        return table;
    }

</script>
</html>
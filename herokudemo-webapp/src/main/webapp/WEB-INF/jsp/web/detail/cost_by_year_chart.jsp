<%--
  Created by IntelliJ IDEA.
  User: anh.ndv
  Date: 11/10/2017
  Time: 14:44
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
    <title>APPOINTMENT CHART BY YEAR</title>
    <jsp:include page="../header.jsp"/>
</head>
<body>
    <jsp:include page="../mobile_nav.jsp"/>
    <jsp:include page="../side_nav.jsp"/>
    <div class="col-sm-9 col-lg-9 col-sm-12 col-xs-12" style="padding-top: 30px">
        <canvas id="myChart" <%--width="100px" height="100px"--%>></canvas>
    </div>
</body>
<script type="application/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="application/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.0/Chart.bundle.js"></script>
<script type="application/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.0/Chart.bundle.min.js"></script>
<script type="application/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.0/Chart.js"></script>
<script type="application/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.0/Chart.min.js"></script>

<script>
    jQuery(document).ready(function($){
        getAppointmentsByYear();
    });

    function getAppointmentsByYear(){
        $.ajax({
            type:"GET",
            url: "http://lvtn-server.herokuapp.com/ajax/user/infos/year",
            data : "id=${id}&from=${from}&to=${to}",
            dataType : "text",
            cache : false,
            success: function(result){
                console.log(result);
                //dataArr = JSON.stringify(dataArr);
                var dataArr = $.parseJSON(result);
                drawChart(dataArr);
            },
            error: function (xhr) {
                var err = eval("(" + xhr.responseText + ")");
                alert(err.Message);
            }
        });
        return temp;
    }

    function convertStringToIntArray(array){
        var dataArr = array.split(" ");
        for(var i = 0; i < dataArr.length; i++){
            dataArr[i] = parseInt(dataArr[i],10);
        }
        return dataArr;
    }

    function drawChart(dataArr){
        var ctx = document.getElementById("myChart");
        var labels = [];
        var datas = [];
        var borders = [];
        var backgrounds = [];
        for(var i = 0; i < dataArr.length; i++){
            var data = dataArr[i];
            borders.push(data.border);
            labels.push(data.year);
            datas.push(data.amount);
            backgrounds.push(data.background);
        }
        var myChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Thoudsands vnd',
                    data: datas, /*[12, 19, 3, 5, 2, 3]*/
                    backgroundColor: backgrounds,
                    borderColor: borders,
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero:true
                        }
                    }]
                }
            }
        });
    }
</script>
</html>
<%--
  Created by IntelliJ IDEA.
  User: anh.ndv
  Date: 08/11/2017
  Time: 17:52
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
    <title>Update price</title>
    <jsp:include page="../header.jsp"/>
</head>
<body>
    <jsp:include page="../mobile_nav.jsp"/>
    <jsp:include page="../side_nav.jsp"/>
    <div class="container-fluid">
        <div class="col-sm-9 col-lg-9 col-md-9 col-xs-12" style="padding-top: 30px">
            <div style="
    margin-bottom: 1em;
">
                <button class="btn btn-primary" id="addFormula" style="
">ADD FORMULA
                </button>
                <button class="btn btn-primary" id="addVar">ADD VARIABLE</button>
                <button class="btn btn-primary" id="submit">SUBMIT</button>
            </div>

            <div class="row">
                <div class="col-sm-6 col-lg-6 col-md-6 col-xs-12" id="formulas">
                    <%--<div class="form-group row">
                        <div class="col-md-4">
                            <label for="ct1" class="title">Type:</label>
                            <select class="form-control" id="ct1">
                                <option value="if">if</option>
                                <option value="else-if">else if</option>
                                <option value="else">else</option>
                                <option value="no">no condition</option>
                            </select>
                        </div>
                        <div class="col-md-8">
                            <label class="title" for="c1">Condition: </label>
                            <input type="text" class="form-control" id="c1">
                        </div>
                    </div>
                    <div class="form-group row formula-text">
                        <label class="title" for="f1">Formula: </label>
                        <input type="text" class="form-control" id="f1">
                    </div>--%>

                </div>

                <div class="col-sm-6 col-lg-6 col-md-6 col-xs-12">
                    <section id="vars">
                        <%--<div class="title"> Variables :</div>
                        <div class="content row">
                            <div class="col-md-6">
                                <div class="form-group row">
                                    <label for="name1" class="col-md-4 col-form-label" style="
    padding-top: 7px;
">Name:</label>
                                    <div class="col-md-8">
                                        <input type="text" class="form-control" id="name1" placeholder="name..">
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group row">
                                    <label for="value1" class="col-md-4 col-form-label" style="
    padding-top: 7px;
">Value:</label>
                                    <div class="col-md-8">
                                        <input type="number" class="form-control" id="value1" placeholder="value..">
                                    </div>
                                </div>
                            </div>
                        </div>--%>
                    </section>
                </div>
            </div>
        </div>
    </div>

    <script type="application/javascript" class="row">
        $(document).ready(function () {
            var formulas = ${formulas};
            var variables = ${variables};

            populate_formulas(formulas);
            populate_vars(variables);
        });

        var i = 1;
        var j = 1;
        $("#addFormula").click(function () {
            i = i + 1;
            $("#formulas").append(formula_template(i,"","",""));
        });

        $("#addVar").click(function () {
            j = j + 1;
            $("#vars").append(var_template(j,"",""));
        });

        $("#submit").click(function () {
            var variables = [];
            var formulas = [];
            for (k = 1; k <= i; k++) {
                var condition_type = $("#ct"+k).val();
                var condition = $("#c"+k).val();
                var formula = $("#f" + k).val();
                var obj = {};
                obj["condition_type"] = condition_type;
                obj["condition"] = condition;
                obj["formula"] = formula;
                formulas.push(obj);
            }

            for(k = 1; k <= j; k++){
                var name = $("#name"+k).val();
                var value = $("#value"+k).val();
                var obj = {};
                obj["name"] = name;
                obj["value"] = parseFloat(value);
                variables.push(obj);
            }

            var input = {};
            input["id"] = ${id};
            input["formulas"] = formulas;
            input["vars"] = variables;
            console.log(input);

            $.ajax({
                type:"GET",
                url: "http://lvtn-server.herokuapp.com/ajax/vehicle/price",
                /*contentType : 'application/json; charset=utf-8',*/
                data : "input = " +JSON.stringify(input),
                dataType : "text",
                cache : false,
                success: function(result){
                    /*var dataArr = $.parseJSON(result);
                    $("#result-list").html("");
                    $("#result-list").html(populateResultList(dataArr));*/
                    alert(result);
                },
                error: function (xhr) {
                    var err = eval("(" + xhr.responseText + ")");
                    alert(err.Message);
                }
            });
        });

        function populate_formulas(formulas){
            if(formulas.length > 0){
                for(count = 0; count < formulas.length; count++){
                    var obj = formulas[count];
                    $("#formulas").append(formula_template((count+1),obj["condition_type"],obj["condition"],obj["formula"]));
                }
            } else{
                $("#formulas").append(formula_template(1,"","",""));
            }
        }

        function populate_vars(variables){
            if(variables.length > 0){
                for(count = 0; count < variables.length; count++){
                    var obj = variables[count];
                    $("#vars").append(var_template((count + 1),obj["name"],obj["value"]));
                }
            } else{
                $("#vars").append(var_template(1,"",""));
            }
        }

        function formula_template(i,ct,c,f){
            var cts = ["if","else if","else","no condition"];
            var select_template = "<select class=\"form-control\" id=\"ct"+i+"\">\n";

            for(k = 0; k < cts.length;k++){
                if(cts[k] == ct){
                    select_template += "\t<option value=\""+ct+"\" selected=true>"+ct+"</option>\n";
                } else{
                    select_template += "\t<option value=\""+cts[k]+"\">"+cts[k]+"</option>\n";
                }
            }

            select_template += "</select>";

            var template = "<div class=\"form-group row\">\n" +
                "    <div class=\"col-md-4\">\n" +
                "        <label for=\"ct"+i+"\" class=\"title\">Type:</label>\n" +
                select_template +
                "    </div>\n" +
                "    <div class=\"col-md-8\">\n" +
                "        <label class=\"title\" for=\"c"+i+"\">Condition: </label>\n" +
                "        <input type=\"text\" class=\"form-control\" id=\"c"+i+"\" value='"+c+"'>\n" +
                "    </div>\n" +
                "</div>\n" +
                "\n" +
                "<div class=\"form-group row formula-text\">\n" +
                "    <label class=\"title\" for=\"f"+i+"\">Formula: </label>\n" +
                "    <input type=\"text\" class=\"form-control\" id=\"f"+i+"\" value='"+f+"'>\n" +
                "</div>";

            return template;
        }

        function var_template(j,n,v){
            var template = "<div class=\"content row\">\n" +
                "                        <div class=\"col-md-6\">\n" +
                "                            <div class=\"form-group row\">\n" +
                "                                <label for=\"name"+j+"\" class=\"col-md-4 col-form-label\" style=\"\n" +
                "    padding-top: 7px;\n" +
                "\">Name:</label>\n" +
                "                                <div class=\"col-md-8\">\n" +
                "                                    <input type=\"text\" class=\"form-control\" id=\"name"+j+"\" placeholder=\"name..\" value='"+n+"'>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <div class=\"col-md-6\">\n" +
                "                            <div class=\"form-group row\">\n" +
                "                                <label for=\"value"+j+"\" class=\"col-md-4 col-form-label\" style=\"\n" +
                "    padding-top: 7px;\n" +
                "\">Value:</label>\n" +
                "                                <div class=\"col-md-8\">\n" +
                "                                    <input type=\"number\" class=\"form-control\" id=\"value"+j+"\" placeholder=\"value..\" value='"+v+"'>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                    </div>";
            return template;
        }
    </script>
</body>
</html>
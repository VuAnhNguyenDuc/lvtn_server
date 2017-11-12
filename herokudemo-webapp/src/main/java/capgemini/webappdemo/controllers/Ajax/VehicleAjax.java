package capgemini.webappdemo.controllers.Ajax;

import capgemini.webappdemo.domain.Vehicle;
import capgemini.webappdemo.service.Vehicle.VehicleService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class VehicleAjax {
    @Autowired
    private VehicleService vhService;

    @RequestMapping(value = "/ajax/vehicle/price", method = RequestMethod.GET, params = "input")
    public String updatePrice(@RequestParam("input") String input) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(input);
        long id = (long) obj.get("id");
        JSONArray formulas = (JSONArray) obj.get("formulas");
        JSONArray vars = (JSONArray) obj.get("vars");
        ArrayList<String> var_names = new ArrayList<>();
        ArrayList<String> condition_types = new ArrayList<>();

        // Validate the variables first
        for(int i = 0; i < vars.size(); i++){
            obj = (JSONObject) vars.get(i);
            var_names.add(obj.get("name").toString());
        }

        for(int i = 0; i < var_names.size(); i++){
            int occurences = Collections.frequency(var_names,var_names.get(i));
            if(occurences > 1){
                return "Invalid variable : " + var_names.get(i) + ". There can not be two identical variables";
            }
        }

        // Then validate the formulas
        for(int i = 0; i < formulas.size(); i++){
            obj = (JSONObject) formulas.get(i);
            if(i == 0 && (obj.get("condition_type").equals("else if") || obj.get("condition_type").equals("else"))){
                return "Invalid condition type : the first condition must be if or no condition at all";
            } else if(obj.get("condition_type").equals("else") && i != formulas.size()-1){
                return "Invalid condition type : there can not be another condition after else";
            }
            condition_types.add(obj.get("condition_type").toString());
        }

        if(Collections.frequency(condition_types,"no condition") > 0 && formulas.size() > 1){
            return "Invalid condition type : you can not input more conditions if you have a 'no condition' type";
        }

        if(Collections.frequency(condition_types,"else") > 1){
            return "Invalid condition type : there can not be two else statements";
        }

        for(int i = 0; i < formulas.size(); i++){
            obj = (JSONObject) formulas.get(i);
            String condition_type= obj.get("condition_type").toString();
            String condition = obj.get("condition").toString();
            String formula = obj.get("formula").toString();

           /* System.out.println("condition = " +condition);
            System.out.println("formula = " + formula);*/

            if((condition_type.equals("else") || condition_type.equals("no condition")) && !condition.equals("")){
                return "do not input condition to (else) and (no condition)";
            }

            if((condition_type.equals("if") || condition_type.equals("else if")) && condition.equals("")){
                return "do not leave condition blank on (if) and (else if)";
            }

            if(formula.equals("")){
                return "please do not leave formula blank";
            }

            String result = validateExpression(var_names,condition);
            if(condition_type.equals("else") || condition_type.equals("no condition")){
                result = "success";
            }
            String result1 = validateExpression(var_names,formula);
            if(!result.equals("success")){
                return result;
            } else if(!result1.equals("success")){
                return result1;
            }
        }
        Vehicle vehicle = vhService.get((int) id);
        vehicle.setCalculate_formula(input);
        vhService.update(vehicle);
        return "success";
    }

    private String replaceAllVarsWithNumber(String input,ArrayList<String> vars){
        for(int i = 0; i < vars.size(); i++){
            if(!vars.get(i).equals("")){
                input = input.replaceAll(vars.get(i),"1.0");
            }
        }
        return input;
    }

    private String validateExpression(ArrayList<String> vars,String exp){
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        String input = replaceAllVarsWithNumber(exp,vars);
        try {
            input = "( " + input + " )";
            engine.eval(input);
            return "success";
        } catch (ScriptException e) {
            //e.printStackTrace();
            return "Invalid expression : " + exp +", you might have input one or more undefined variable";
        }
       /* String foo = "1 + 2*(3+1) - 1/4 + s/t";
        double a = 100.0;
        double b = 2.0;
        foo = foo.replaceAll("s",Double.toString(a));
        foo = foo.replaceAll("t",Double.toString(b));*/

       /* System.out.println(result);
        foo = "(1 > 0)";
        System.out.println(engine.eval(exp));*/
    }
}

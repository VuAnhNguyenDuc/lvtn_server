package capgemini.webappdemo.controllers.Ajax;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class VehicleAjax {
    private class Formula{
        private String condition;
        private String condition_type;
        private String formula;

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public String getCondition_type() {
            return condition_type;
        }

        public void setCondition_type(String condition_type) {
            this.condition_type = condition_type;
        }

        public String getFormula() {
            return formula;
        }

        public void setFormula(String formula) {
            this.formula = formula;
        }
    }

    private class Variable{
        private String name;
        private double value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }

    private class VehiclePrice{
        private int id;
        private List<Formula> formulas;
        private List<Variable> vars;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<Formula> getFormulas() {
            return formulas;
        }

        public void setFormulas(List<Formula> formulas) {
            this.formulas = formulas;
        }

        public List<Variable> getVars() {
            return vars;
        }

        public void setVars(List<Variable> vars) {
            this.vars = vars;
        }
    }

    @RequestMapping(value = "/ajax/vehicle/price", method = RequestMethod.GET, params = "input")
    public String updatePrice(@RequestParam("input") String input) throws ParseException {
        input = input.replaceAll("%22","\"");
        System.out.println(input);
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
            }
            condition_types.add(obj.get("condition_type").toString());
        }

        if(Collections.frequency(condition_types,"else") > 1){
            return "Invalid condtion type : there can not be two else statements";
        }

        for(int i = 0; i < formulas.size(); i++){
            obj = (JSONObject) formulas.get(i);
            String condition_type= obj.get("condition_type").toString();
            String condition = obj.get("condition").toString();
            String formula = obj.get("formula").toString();
            if((condition_type.equals("else") || condition_type.equals("no condition")) && !condition.equals("")){
                return "do not input condition to (else) and (no condition)";
            }

            if((condition_type.equals("if") || condition_type.equals("else if")) && condition.equals("")){
                return "do not leave condition blank on (if) and (else if)";
            }

            if(formula.equals("")){
                return "please do not leave formula blank";
            }

            String result = validateExpression(vars,condition);
            String result1 = validateExpression(vars,formula);
            if(!result.equals("success")){
                return result;
            } else if(!result1.equals("success")){
                return result1;
            }
        }

        return "success";
    }

    private String replaceAllVarsWithNumber(String input,ArrayList<String> vars){
        for(int i = 0; i < vars.size(); i++){
            input = input.replaceAll(vars.get(i),"1.0");
        }
        return input;
    }

    private String validateExpression(ArrayList<String> vars,String exp){
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        try {
            String input = replaceAllVarsWithNumber(exp,vars);
            input = "(" + input + ")";
            engine.eval(input);
            return "success";
        } catch (ScriptException e) {
            //e.printStackTrace();
            return "Invalid expression : " + exp;
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

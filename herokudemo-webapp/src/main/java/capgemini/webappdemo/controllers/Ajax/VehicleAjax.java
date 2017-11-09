package capgemini.webappdemo.controllers.Ajax;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/ajax/vehicle/price", method = RequestMethod.POST)
    public String updatePrice(@RequestBody VehiclePrice vp){
        return "hello vehicle " + vp.getId();
    }
}

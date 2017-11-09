package capgemini.webappdemo.controllers.Ajax;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
public class VehicleAjax {
    private class VehiclePrice{
        private int id;
        private JSONArray formulas;
        private JSONArray vars;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public JSONArray getFormulas() {
            return formulas;
        }

        public void setFormulas(JSONArray formulas) {
            this.formulas = formulas;
        }

        public JSONArray getVars() {
            return vars;
        }

        public void setVars(JSONArray vars) {
            this.vars = vars;
        }
    }

    @RequestMapping(value = "/ajax/vehicle/price", method = RequestMethod.POST)
    public String updatePrice(@RequestParam int id, @RequestParam JSONArray formulas, @RequestParam JSONArray vars){
        return "hello vehicle " + id;
    }
}

package capgemini.webappdemo.utils;

import capgemini.webappdemo.domain.Vehicle;
import capgemini.webappdemo.service.Vehicle.VehicleService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class CalculateMoney {
    // type of vehicle
    // the total length of the trip
    // the time of the trip

    //https://www.uber.com/vi-VN/drive/hanoi/resources/dong-san-pham/

    //https://www.grab.com/vn/bike/

    //https://www.grab.com/vn/car/

    @Autowired
    private VehicleService service;

    public double getEstimateCost(String formulas_and_vars, double s,long t){
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        double cost = 0;
        JSONParser parser = new JSONParser();
        try {
            JSONObject obj = (JSONObject) parser.parse(formulas_and_vars);
            JSONArray formulas = (JSONArray) obj.get("formulas");
            JSONArray vars = (JSONArray) obj.get("vars");

            for(int i = 0; i < formulas.size(); i++){
                obj = (JSONObject) formulas.get(i);
                String condition_type = obj.get("condition_type").toString();
                String condition = obj.get("condition").toString();
                String formula = obj.get("formula").toString();

                try {
                    if(condition_type.equals("if") || condition_type.equals("else if")){
                        boolean validate = (boolean) engine.eval(condition);
                        if(validate){
                            formula = replaceVarsWithValues(formula,vars,s,t);
                            if(engine.eval(formula) instanceof Integer){
                                cost = (double) (Integer) engine.eval(formula);
                            } else{
                                cost = (double) engine.eval(formula);
                            }
                        }
                    } else if(condition_type.equals("else") || condition_type.equals("no condition")){
                        formula = replaceVarsWithValues(formula,vars,s,t);
                        if(engine.eval(formula) instanceof Integer){
                            cost = (double) (Integer) engine.eval(formula);
                        } else{
                            cost = (double) engine.eval(formula);
                        }
                    }
                } catch (ScriptException e) {
                    e.printStackTrace();
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cost;
    }

    private String replaceVarsWithValues(String input,JSONArray vars,double s,long t){
        for(int i = 0; i < vars.size(); i++){
            JSONObject obj = (JSONObject) vars.get(i);
            String var = obj.get("name").toString();
            double value = (double) obj.get("value");
            if(!var.equals("")){
                input = input.replaceAll(var, String.valueOf(value));
            }
        }
        input = input.replaceAll("s",String.valueOf(s));
        input = input.replaceAll("t",String.valueOf(t));
        return input;
    }

}

/*public double getEstimateCost(String name, double s, long t){
        double cost = 0;
        switch (name){
            case "Uber X":
                    *//*double uberX_start = vp.getValue("uberX_start");
                    double uberX_length = vp.getValue("uberX_length");
                    double uberX_time = vp.getValue("uberX_time");
                    cost = uberX_start + uberX_length*s + uberX_time*t/60;*//*
                    cost = 15 + 7.5*s + 0.3*t/60;
                    break;
            case "Uber Black" :
                    *//*double uberBlack_start = vp.getValue("uberBlack_start");
                    double uberBlack_length = vp.getValue("uberBlack_length");
                    double uberBlack_time = vp.getValue("uberBlack_time");ice
                    cost = uberBlack_start + uberBlack_length*s + uberBlack_time*t/60;*//*
                    cost = 5 + 9.597*s + 0.8*t/60;
                    break;
            case "Uber SUV" :
                    *//*double uberSUV_start = vp.getValue("uberSUV_start");
                    double uberSUV_length = vp.getValue("uberSUV_length");
                    double uberSUV_time = vp.getValue("uberSUV_time");
                    cost = uberSUV_start + uberSUV_length*s + uberSUV_time*t/60;*//*
                    cost = 5 + 9.597*s + 0.8*t/60;
                    break;
            case "Uber MOTO" :
                    *//*double uberMoto_start = vp.getValue("uberMoto_start");
                    double uberMoto_length = vp.getValue("uberMoto_length");
                    double uberMoto_time = vp.getValue("uberMoto_time");
                    cost = uberMoto_start + uberMoto_length*s + uberMoto_time*t/60;*//*
                    cost = 10 + 3.7*s + 0.2*t/60;
                    break;
            case "Grab Bike":
                    *//*double grabBike_min = vp.getValue("grabBike_min");
                    double grabBike_length = vp.getValue("grabBike_length");*//*
                    if(s <= 2){
                        //cost = grabBike_min;
                        cost = 12;
                    } else{
                        //cost = grabBike_min + grabBike_length*(s-2);
                        cost = 12 + 3.8*(s-2);
                    }
                    break;
            case "Grab Bike Premium":
                   *//* double grabBikePre_min = vp.getValue("grabBikePre_min");
                    double grabBikePre_length = vp.getValue("grabBikePre_length");*//*
                    if(s <= 2){
                        //cost = grabBikePre_min;
                        cost = 20;
                    } else{
                        //cost = grabBikePre_min + grabBikePre_length*(s-2);
                        cost = 20 + 7*(s-2);
                    }
                    break;
            case "Grab Car 4 seats":
                    *//*double grab4Seats_min = vp.getValue("grab4Seats_min");
                    double grab4Seats_length = vp.getValue("grab4Seats_length");
                    double grab4Seats_time = vp.getValue("grab4Seats_time");*//*
                    if(s <= 2){
                        //cost = grab4Seats_min + grab4Seats_time*t/60;
                        cost = 20 + 0.3*t/60;
                    } else{
                        //cost = grab4Seats_min + grab4Seats_length*s + grab4Seats_time*t/60;
                        cost = 20 + 9*s + 0.3*t/60;
                    }
                    break;
            case "Grab Car 7 seats":
                    *//*double grab7Seats_min = vp.getValue("grab7Seats_start");
                    double grab7Seats_length = vp.getValue("grab7Seats_length");
                    double grab7Seats_time = vp.getValue("grab7Seats_time");*//*
                    if(s <= 2){
                        //cost = grab7Seats_min + grab7Seats_time*t/60;
                        cost = 24 + 0.3*t/60;
                    } else{
                        //cost = grab7Seats_min + grab7Seats_length*s + grab7Seats_time*t/60;
                        cost = 24 + 11*s + 0.3*t/60;
                    }
                    break;
            default: cost = 0; break;
        }
        return cost;
    }*/
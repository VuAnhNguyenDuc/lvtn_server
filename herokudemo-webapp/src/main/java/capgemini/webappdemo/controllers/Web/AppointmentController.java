package capgemini.webappdemo.controllers.Web;

import capgemini.webappdemo.domain.*;
import capgemini.webappdemo.service.Appointment.AppointmentService;
import capgemini.webappdemo.service.Coordinate.CoordinateService;
import capgemini.webappdemo.service.Detail.DetailService;
import capgemini.webappdemo.service.User.UserService;
import capgemini.webappdemo.service.UserAppointmentView.UserAppointmentViewService;
import capgemini.webappdemo.service.Vehicle.VehicleService;
import capgemini.webappdemo.utils.CommonUtils;
import capgemini.webappdemo.utils.LoginUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AppointmentController {
    @Autowired
    private UserAppointmentViewService service;

    @Autowired
    private AppointmentService appService;

    @Autowired
    private UserService userService;

    @Autowired
    private DetailService detailService;

    @Autowired
    private CoordinateService coorService;

    @Autowired
    private VehicleService vhService;

    private LoginUtil loginUtil = new LoginUtil();

    private CommonUtils commonUtils = new CommonUtils();

    @RequestMapping(value = "/appointments", method = RequestMethod.GET, params = "type")
    public String getAppointments(HttpSession session,ModelMap model,@RequestParam("type") String type){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            List<Appointment> apps = appService.getApmsByStatus(type);
            for(Appointment app : apps){
                User mng = userService.get(app.getManager_id());
                app.setManager_name(mng.getFullname());
                app.setStart_date_str(commonUtils.convertDateToString(app.getStart_date()));
                if(app.getEnd_date() != null){
                    app.setEnd_date_str(commonUtils.convertDateToString(app.getEnd_date()));
                } else{
                    app.setEnd_date_str("");
                }
            }
            model.addAttribute("pageName","appointment");
            model.addAttribute("apms",apps);
            return "web/appointment/appointment";
        }
    }

    @RequestMapping(value = "/appointment/details/snapToRoad", method = RequestMethod.GET, params = {"appointment_id"})
    public String getAppointmentDetails(HttpSession session, @RequestParam("appointment_id") int id, ModelMap model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            Appointment app = appService.get(id);
            double total_cost = 0;
            List<User> users = appService.getUsersOfAppointment(id);
            List<Detail> details = detailService.getDetailsOfAppointment(id);
            JSONArray details_array = new JSONArray();
            for(Detail dt : details){
                dt.setEnd_time_str("");
                dt.setVehicle_name(vhService.get(dt.getVehicle_id()).getName());
                dt.setTotal_length(round(dt.getTotal_length(),3));
                dt.setAverage_velocity(round(dt.getAverage_velocity(),3));
                if(dt.getEstimate_cost() != 0){
                    dt.setEstimate_cost(round(dt.getEstimate_cost(),3));
                }
                if(dt.getStart_time() != null){
                    dt.setStart_time_str(commonUtils.convertDateToStringSec(dt.getStart_time()));
                }
                if(dt.getEnd_time() != null){
                    dt.setEnd_time_str(commonUtils.convertDateToStringSec(dt.getEnd_time()));
                }
                total_cost += dt.getInput_cost();
                List<Coordinate> coords = coorService.getCoordsOfDetail(dt.getId());

                JSONObject obj = new JSONObject();
                obj.put("vehicle_name", dt.getVehicle_name());
                obj.put("start_time", dt.getStart_time_str());
                obj.put("end_time", dt.getEnd_time_str());
                obj.put("avg_velocity", dt.getAverage_velocity());
                obj.put("coords", convertToSnapToRoad(coords));
                details_array.add(obj);
            }
            app.setUsers(users);
            app.setStart_date_str(commonUtils.convertDateToString(app.getStart_date()));
            if(app.getEnd_date() != null){
                app.setEnd_date_str(commonUtils.convertDateToString(app.getEnd_date()));
            }
            app.setDetails(details);
            app.setTotal_cost(total_cost);
            model.addAttribute("details_array",details_array);
            model.addAttribute("pageName","appointment");
            model.addAttribute("apm",app);
            model.addAttribute("dts",details);
            model.addAttribute("mng",userService.get(app.getManager_id()).getFullname());
            return "web/appointment/apm_map";
        }
    }

    @RequestMapping(value = "/appointment/details", method = RequestMethod.GET, params = {"appointment_id"})
    public String getAppointmentDetailsTest(HttpSession session, @RequestParam("appointment_id") int id,ModelMap model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            Appointment app = appService.get(id);
            double total_cost = 0;
            List<User> users = appService.getUsersOfAppointment(id);
            List<Detail> details = detailService.getDetailsOfAppointment(id);
            JSONArray details_array = new JSONArray();
            for(Detail dt : details){
                dt.setEnd_time_str("");
                dt.setVehicle_name(vhService.get(dt.getVehicle_id()).getName());
                dt.setTotal_length(round(dt.getTotal_length(),3));
                dt.setAverage_velocity(round(dt.getAverage_velocity(),3));
                if(dt.getEstimate_cost() != 0){
                    dt.setEstimate_cost(round(dt.getEstimate_cost(),3));
                }
                if(dt.getStart_time() != null){
                    dt.setStart_time_str(commonUtils.convertDateToStringSec(dt.getStart_time()));
                }
                if(dt.getEnd_time() != null){
                    dt.setEnd_time_str(commonUtils.convertDateToStringSec(dt.getEnd_time()));
                }
                total_cost += dt.getInput_cost();
                List<Coordinate> coords = coorService.getCoordsOfDetail(dt.getId());

                JSONObject obj = new JSONObject();
                obj.put("vehicle_name", dt.getVehicle_name());
                obj.put("start_time", dt.getStart_time_str());
                obj.put("end_time", dt.getEnd_time_str());
                obj.put("avg_velocity", dt.getAverage_velocity());
                obj.put("coords", parseCoords(coords));
                details_array.add(obj);
            }
            app.setUsers(users);
            app.setStart_date_str(commonUtils.convertDateToString(app.getStart_date()));
            if(app.getEnd_date() != null){
                app.setEnd_date_str(commonUtils.convertDateToString(app.getEnd_date()));
            }
            app.setDetails(details);
            app.setTotal_cost(total_cost);
            model.addAttribute("details_array",details_array);
            model.addAttribute("pageName","appointment");
            model.addAttribute("apm",app);
            model.addAttribute("dts",details);
            model.addAttribute("mng",userService.get(app.getManager_id()).getFullname());
            return "web/appointment/apm_map";
        }
    }

    private JSONArray parseCoords(List<Coordinate> coords){
        JSONArray result = new JSONArray();
        for(Coordinate coord : coords){
            JSONObject obj = new JSONObject();
            obj.put("lat",coord.getLatitude());
            obj.put("lng",coord.getLongitude());
            result.add(obj);
        }
        return result;
    }

    private JSONArray convertToSnapToRoad(List<Coordinate> coords){
        String coordStr = "";
        JSONArray result = new JSONArray();
        for(int i = 0; i < coords.size() - 1; i++){
            coordStr+=coords.get(i).getLatitude() + "," + coords.get(i).getLongitude()+"|";
        }

        coordStr+=coords.get(coords.size()-1).getLatitude()+","+coords.get(coords.size()-1).getLongitude();

        String api = "https://roads.googleapis.com/v1/snapToRoads?path="+coordStr+"&interpolate=true&key=AIzaSyDhlcbvdlgCkj5u5tLUqzeeyx0a3Dp_nlo";
        try {
            URL url = new URL(api);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept","application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode()+ "/n"
                        + "Error is : " + conn.getErrorStream().toString());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output = "";
            while ((br.readLine()) != null) {
                output+=br.readLine();
            }
            System.out.println(output);
            conn.disconnect();

            JSONParser parser = new JSONParser();
            JSONObject returnedCoords = (JSONObject) parser.parse(output);
            JSONArray snappedPoints = (JSONArray) returnedCoords.get("snappedPoints");
            for(int i = 0; i < snappedPoints.size(); i++){
                JSONObject obj = (JSONObject) snappedPoints.get(i);
                JSONObject locations = (JSONObject) obj.get("location");
                double lat = (double) locations.get("latitude");
                double lng = (double) locations.get("longitude");
                JSONObject temp = new JSONObject();
                temp.put("lat",lat);
                temp.put("long",lng);
                result.add(temp);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}





    /*@RequestMapping(value = "/appointment/details/old", method = RequestMethod.GET, params = {"appointment_id","detail_id"})
    public String getAppointmentDetails(HttpSession session, @RequestParam("appointment_id") int id, @RequestParam("detail_id") int detail_id, ModelMap model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            Appointment app = appService.get(id);
            List<User> users = appService.getUsersOfAppointment(id);
            List<Detail> details = detailService.getDetailsOfAppointment(id);
            List<Coordinate> total_coords = new ArrayList<>();
            for(Detail dt : details){
                //calculateDetail(dt);
                dt.setVehicle_name(vhService.get(dt.getVehicle_id()).getName());
                dt.setTotal_length(round(dt.getTotal_length(),2));
                dt.setAverage_velocity(round(dt.getAverage_velocity(),2));
                if(dt.getStart_time() != null){
                    dt.setStart_time_str(commonUtils.convertDateToString(dt.getStart_time()));
                }
                if(dt.getEnd_time() != null){
                    dt.setEnd_time_str(commonUtils.convertDateToString(dt.getEnd_time()));
                }
                List<Coordinate> coords = coorService.getCoordsOfDetail(dt.getId());
                if(detail_id == 0){
                    total_coords.addAll(coords);
                } else {
                    if(dt.getId() == detail_id){
                        total_coords.addAll(coords);
                    }
                }
            }
            app.setUsers(users);
            app.setStart_date_str(commonUtils.convertDateToStringSec(app.getStart_date()));
            if(app.getEnd_date() != null){
                app.setEnd_date_str(commonUtils.convertDateToStringSec(app.getEnd_date()));
            }
            app.setDetails(details);
            model.addAttribute("coords",parseCoords(total_coords));
            model.addAttribute("pageName","appointment");
            model.addAttribute("apm",app);
            model.addAttribute("dts",details);
            model.addAttribute("mng",userService.get(app.getManager_id()).getFullname());
            return "web/appointment/apm_detail";
        }
    }*/

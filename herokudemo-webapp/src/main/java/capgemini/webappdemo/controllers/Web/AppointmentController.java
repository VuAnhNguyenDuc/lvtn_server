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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
            List<Appointment> apps = sortAppointments(type);
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

    @RequestMapping(value = "/appointment/details", method = RequestMethod.GET, params = {"appointment_id","snapToRoad"})
    public String getAppointmentDetails(HttpSession session, @RequestParam("appointment_id") int id,@RequestParam("snapToRoad") boolean snapToRoad, ModelMap model){
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

                // We will only let google draw the coordinates that actually exist
                if(coords.size() > 0){
                    JSONObject obj = new JSONObject();
                    obj.put("vehicle_name", dt.getVehicle_name());
                    obj.put("start_time", dt.getStart_time_str());
                    obj.put("end_time", dt.getEnd_time_str());
                    obj.put("avg_velocity", dt.getAverage_velocity());
                    if(snapToRoad){
                        JSONArray total_coords = new JSONArray();
                        if(coords.size() > 100){
                            int a = coords.size() / 100;
                            int b = coords.size() - a*100;
                            int start = 0;
                            int end;
                            for(int i = 0; i <= a; i++){
                                if(i == a){
                                    end = start + b - 1;
                                } else{
                                    end = start + 99;
                                }
                                List<Coordinate> temp = coords.subList(start,end);
                                total_coords = convertToSnapToRoad(temp,total_coords);
                                start += 100;
                            }
                        } else{
                            total_coords = convertToSnapToRoad(coords,total_coords);
                        }
                        obj.put("coords", total_coords);
                    } else{
                        obj.put("coords", parseCoords(coords));
                    }
                    details_array.add(obj);
                }
            }
            app.setUsers(users);
            app.setStart_date_str(commonUtils.convertDateToString(app.getStart_date()));
            if(app.getEnd_date() != null){
                app.setEnd_date_str(commonUtils.convertDateToString(app.getEnd_date()));
            }
            app.setDetails(details);
            //app.setTotal_cost(total_cost);
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

    private JSONArray convertToSnapToRoad(List<Coordinate> coords,JSONArray result){
        String coordStr = "";
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
                System.out.println("Error : " );
                System.out.println("Input API : ");
                System.out.println(api);

                InputStream error = conn.getErrorStream();
                InputStreamReader isrerror = new InputStreamReader(error);
                BufferedReader bre = new BufferedReader(isrerror);
                String linee;
                while ((linee = bre.readLine()) != null) {
                    System.out.println(linee);
                }
                System.out.println("End of Error");

                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String apiResult = "";
            String output;
            while ((output = br.readLine()) != null) {
                apiResult+=output;
            }
            conn.disconnect();

            JSONParser parser = new JSONParser();
            JSONObject returnedCoords = (JSONObject) parser.parse(apiResult);
            JSONArray snappedPoints = (JSONArray) returnedCoords.get("snappedPoints");
            for(int i = 0; i < snappedPoints.size(); i++){
                JSONObject obj = (JSONObject) snappedPoints.get(i);
                JSONObject locations = (JSONObject) obj.get("location");
                double lat = (double) locations.get("latitude");
                double lng = (double) locations.get("longitude");

                JSONObject temp = new JSONObject();
                temp.put("lat",lat);
                temp.put("lng",lng);
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

    private List<Appointment> sortAppointments(String type){
        if(type.equals("all")){
            List<Appointment> result = new ArrayList<>();
            List<Appointment> actives = appService.getApmsByStatus("active");
            List<Appointment> finished = appService.getApmsByStatus("finished");
            List<Appointment> warnings = appService.getApmsByStatus("warning");
            List<Appointment> others = new ArrayList<>();
            others.addAll(finished);
            others.addAll(warnings);

            sortActive(actives);
            sortOthers(others);

            result.addAll(actives);
            result.addAll(others);
            return result;
        } else{
            List<Appointment> apms = appService.getApmsByStatus(type);
            if(type.equals("active")){
                sortActive(apms);
            } else {
                sortOthers(apms);
            }
            return apms;
        }
    }

    private void sortActive(List<Appointment> actives){
        Collections.sort(actives, new Comparator<Appointment>() {
            @Override
            public int compare(Appointment o1, Appointment o2) {
                return o1.getStart_date().before(o2.getStart_date())?1:-1;
            }
        });
    }

    private void sortOthers(List<Appointment> others){
        Collections.sort(others, new Comparator<Appointment>() {
            @Override
            public int compare(Appointment o1, Appointment o2) {
                return o1.getEnd_date().before(o2.getEnd_date())?1:-1;
            }
        });
    }

}


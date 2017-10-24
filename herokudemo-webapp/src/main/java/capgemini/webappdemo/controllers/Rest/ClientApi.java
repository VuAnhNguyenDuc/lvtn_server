package capgemini.webappdemo.controllers.Rest;

import capgemini.webappdemo.domain.Client;
import capgemini.webappdemo.service.Client.ClientService;
import capgemini.webappdemo.utils.JsonTokenUtil;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ClientApi {
    @Autowired
    private ClientService service;

    private JsonTokenUtil jsonTokenUtil = new JsonTokenUtil();

    @RequestMapping(value = "/createClient",method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addClient(@RequestBody JSONObject input){
        String jsonToken = input.get("json_token").toString();
        JSONObject result = new JSONObject();
        result.put("message",0);
        if(jsonToken.equals("") || !jsonTokenUtil.validateKey(jsonToken)){
            result.put("description","invalid json token");
        } else{
            int id = jsonTokenUtil.getUserIdFromJsonKey(jsonToken);
            String name = input.get("name").toString();
            String phone = input.get("phone_number").toString();
            String address = input.get("address").toString();
            String email = input.get("email").toString();

            Client client = new Client();
            client.setName(name);
            client.setEmail(email);
            client.setAddress(address);
            client.setPhone_number(phone);
            client.setUser_create_id(id);
            service.add(client);
            if(client.getId() != 0){
                result.put("message",1);
            } else{
                result.put("description","error while creating a new client");
            }
        }
        return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
    }

}

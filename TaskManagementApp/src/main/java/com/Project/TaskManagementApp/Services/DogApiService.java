package com.Project.TaskManagementApp.Services;

import com.Project.TaskManagementApp.ApiResponses.DogResponse;
import com.Project.TaskManagementApp.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class DogApiService {


@Autowired
private RestTemplate restTemplate;

    @Value("${dog.service.api}")
    private  String api;


    public String getDogApi(){

//        DogResponse response = restTemplate.getForObject(api, DogResponse.class);
        ResponseEntity<DogResponse> exchange = restTemplate.exchange(api, HttpMethod.GET, null, DogResponse.class);
        DogResponse response = exchange.getBody();
        if(response != null && response.getData() != null && response.getData().getAttributes() != null){

            return ""+exchange.getStatusCode()+"..."+response.getData().getAttributes().getName() + " : " +
                    response.getData().getAttributes().getDescription();
        }

        return "No data found";
    }

    public boolean createUser(User user){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        ResponseEntity<Boolean> exchange = restTemplate.exchange("http://localhost:8080/Public/new", HttpMethod.POST, entity, Boolean.class);
        if(exchange.getStatusCode().is2xxSuccessful()){
            return true;
        }
        return false;

    }
}

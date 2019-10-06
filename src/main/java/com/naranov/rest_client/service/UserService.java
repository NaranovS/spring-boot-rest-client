package com.naranov.rest_client.service;

import com.naranov.rest_client.model.User;
import com.naranov.rest_client.model.UserProfile;
import com.naranov.rest_client.model.UserProfileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private RestTemplate restTemplate;
    
    public List<User> findAllUsers(){
        return restTemplate.getForObject("http://localhost:8080/api/users", List.class);
    }
    
    public void createNewUser(User user){
        restTemplate.postForObject("http://localhost:8080/api/users", user, User.class);
    }

    public User findUserById(int id) {
        return restTemplate.getForObject("http://localhost:8080/api/users/id/" + id, User.class);
    }

    public void updateUser(int id, User user) throws HttpClientErrorException {
        try {
            restTemplate.put("http://localhost:8080/api/users/id/" + id, user);
        } catch (HttpClientErrorException ex) {
            throw ex;
        }
    }

    public void deleteById(int id) {
        restTemplate.delete("http://localhost:8080/api/users/id/" + id);
    }

    public void findByEmail(String email){
        try {
            restTemplate.getForObject("http://localhost:8080/api/users/email/" + email, User.class);
        } catch (HttpClientErrorException ex){
            throw ex;
        }
    }
}

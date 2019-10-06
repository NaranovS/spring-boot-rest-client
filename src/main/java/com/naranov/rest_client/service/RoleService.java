package com.naranov.rest_client.service;

import com.naranov.rest_client.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RestTemplate restTemplate;

    public UserProfile findUserProfileById(int id) {
        return restTemplate.getForObject("http://localhost:8080/api/roles/" + id, UserProfile.class);
    }

    public List<UserProfile> findAllUserProfiles() {
        List<UserProfile> userProfiles = Arrays.asList(restTemplate.getForObject("http://localhost:8080/api/roles", UserProfile[].class));
        return new ArrayList<>(userProfiles);
    }
}
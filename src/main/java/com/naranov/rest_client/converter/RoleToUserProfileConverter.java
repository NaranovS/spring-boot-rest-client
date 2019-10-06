package com.naranov.rest_client.converter;

import com.naranov.rest_client.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.naranov.rest_client.model.UserProfile;
import com.naranov.rest_client.service.UserService;

@Component
public class RoleToUserProfileConverter implements Converter<Object, UserProfile>{

    @Autowired
    RoleService roleService;

    public UserProfile convert(Object element) {
        Integer id = Integer.parseInt((String)element);
        return roleService.findUserProfileById(id);
    }
}
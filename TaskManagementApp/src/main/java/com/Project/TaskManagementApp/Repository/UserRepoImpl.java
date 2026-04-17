package com.Project.TaskManagementApp.Repository;

import com.Project.TaskManagementApp.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class UserRepoImpl {

@Autowired
private MongoTemplate mongoTemplate;

    public List<User> findAll(){
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is("lafda"));
        List<User> users = mongoTemplate.find(query, User.class);
        return users;

    }
}

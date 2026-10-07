package com.demo.oaush2api.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.demo.oaush2api.dto.User;

import jakarta.persistence.criteria.Predicate;

public class UserSpecification {

    public static Specification<User> searchUsers(User condition) {
    	  return (root, query, cb) -> {

              List<Predicate> predicates = new ArrayList<>();

              if (condition.getUserId() != null
                      && !condition.getUserId().isBlank()) {

                  predicates.add(
                      cb.like(
                          root.get("userId"),
                          "%" + condition.getUserId() + "%"
                      )
                  );
              }
              
              if (condition.getUserName() != null
                      && !condition.getUserName().isBlank()) {

                  predicates.add(
                      cb.like(
                          root.get("userName"),
                          "%" + condition.getUserName() + "%"
                      )
                  );
              }

              if (condition.getDept() != null
                      && !condition.getDept().isBlank()) {

                  predicates.add(
                      cb.like(
                          root.get("dept"),
                          "%" + condition.getDept() + "%"
                      )
                  );
              }

              if (condition.getEmail() != null
                      && !condition.getEmail().isBlank()) {

                  predicates.add(
                      cb.like(
                          root.get("email"),
                          "%" + condition.getEmail() + "%"
                      )
                  );
              }

              if (condition.getMobile() != null
                      && !condition.getMobile().isBlank()) {

                  predicates.add(
                      cb.like(
                          root.get("mobile"),
                          "%" + condition.getMobile() + "%"
                      )
                  );
              }
              
              return cb.and(predicates.toArray(new Predicate[0]));
          };
    }
}
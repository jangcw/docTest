package com.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.demo.dto.Userlist;

import jakarta.persistence.criteria.Predicate;

public class UserlistSpecification {

    public static Specification<Userlist> searchUsers(Userlist condition) {
    	  return (root, query, cb) -> {

              List<Predicate> predicates = new ArrayList<>();

              if (condition.getUname() != null
                      && !condition.getUname().isBlank()) {

                  predicates.add(
                      cb.like(
                          root.get("uname"),
                          "%" + condition.getUname() + "%"
                      )
                  );
              }

              if (condition.getUdept() != null
                      && !condition.getUdept().isBlank()) {

                  predicates.add(
                      cb.like(
                          root.get("udept"),
                          "%" + condition.getUdept() + "%"
                      )
                  );
              }

              if (condition.getWcode() != null
                      && !condition.getWcode().isBlank()) {

                  predicates.add(
                      cb.like(
                          root.get("wcode"),
                          "%" + condition.getWcode() + "%"
                      )
                  );
              }

              if (condition.getCompName() != null
                      && !condition.getCompName().isBlank()) {

                  predicates.add(
                      cb.like(
                          root.get("compName"),
                          "%" + condition.getCompName() + "%"
                      )
                  );
              }

              return cb.and(predicates.toArray(new Predicate[0]));
          };
    }
}
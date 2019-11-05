package com.vitarrico.springboot.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.vitarrico.springboot.app.models.entity.Mail;

public interface IMailDao extends CrudRepository<Mail, Long> {

}

package com.yubi.monitorwebsites.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yubi.monitorwebsites.entity.Website;

@Repository
public interface WebsiteRepo extends JpaRepository<Website,Integer> {

}

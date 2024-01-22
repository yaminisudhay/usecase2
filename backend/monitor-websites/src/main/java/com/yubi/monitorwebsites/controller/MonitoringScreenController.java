package com.yubi.monitorwebsites.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yubi.monitorwebsites.entity.Website;
import com.yubi.monitorwebsites.service.MonitoringScreenService;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/monitor-screen")
public class MonitoringScreenController {
	
	@Autowired
	private MonitoringScreenService service;
	
	@PostMapping("/add")
	public ResponseEntity<Website> addwebsite(@RequestBody Website obj){
		Website result= service.addWebsite(obj);
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<Website>> getwebsites(){
		return new ResponseEntity<>(service.webSiteLst(),HttpStatus.OK);
	}
}

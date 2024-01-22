package com.yubi.monitorwebsites.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.yubi.monitorwebsites.dao.WebsiteRepo;
import com.yubi.monitorwebsites.entity.Website;

@EnableScheduling
@Service
public class MonitoringScreenService {
	@Autowired
	private WebsiteRepo repo;
	
	private static final Logger LOGGER =LoggerFactory.getLogger(MonitoringScreenService.class);
	
	List<Website> webSiteLst;
	
	public Website addWebsite(Website obj) {
		Website webObj = repo.save(obj);
		setStatus(webObj);
		return webObj;
	}
	
	public List<Website> webSiteLst(){
		return webSiteLst;
	}
	
	
	@Scheduled(fixedRate = 2 * 60 * 1000)
	public void checkConnection() {
		List<Website>  lst = repo.findAll();
		LOGGER.info("Before CheckConnection-> {}",lst);
		webSiteLst = lst.stream().map(e -> {this.setStatus(e);return e;}).collect(Collectors.toList());
		LOGGER.info("After CheckConnection-> {}",webSiteLst);
	}
	
	private void setStatus(Website website){
			String myUrl = website.getWebsiteName();
			boolean isSuccess = false;
			if(myUrl.startsWith("http://"))
				isSuccess = tryWithHttp(website.getWebsiteName());
			else if(myUrl.startsWith("https://"))
				isSuccess = tryWithHttps(website.getWebsiteName());
			else {
				isSuccess = tryWithHttp(website.getWebsiteName())
						|| tryWithHttps(website.getWebsiteName());
			}
			website.setStatus(isSuccess?"Success":"Failure");
	}
	
	private boolean tryUrl(String myUrl) {
		try {
			URL url = new URL(myUrl);
			LOGGER.info("Trying URL-> {}",url.toString());
			HttpURLConnection huc = (HttpURLConnection) url.openConnection();
			return huc.getResponseCode()==200;
		}catch(Exception e) {
			LOGGER.error("Exception -",e);
			return false;
		}
	}
	
	private boolean tryWithHttp(String myUrl) {
		if(!myUrl.startsWith("http://"))
			myUrl = "http://"+myUrl;
		return tryUrl(myUrl);
	}
	
	private boolean tryWithHttps(String myUrl) {
		if(!myUrl.startsWith("https://"))
			myUrl = "https://"+myUrl;
		return tryUrl(myUrl);
	}
}

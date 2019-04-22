package com.petio.petIO.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.petio.petIO.services.AdoptionListService;

@Component
public class DailyWork {
	@Autowired
	AdoptionListService adoptionListService;
	
	@Scheduled(cron = "0 0 0 * * *")
	public void initApplyTimes() {
		adoptionListService.initApplyTimes();
	}
}

package com.petio.petIO.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.petio.petIO.services.AdoptionListService;
import com.petio.petIO.services.AdoptionService;

@Component
public class DailyWork {
	@Autowired
	AdoptionService adoptionService;
	
	@Scheduled(cron = "0 0 0 * * *")
	public void initApplyTimes() {
		adoptionService.initApplyTimes();
	}
}

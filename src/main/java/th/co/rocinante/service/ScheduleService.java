package th.co.rocinante.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import th.co.rocinante.AppGlobalParam;

@Service
public class ScheduleService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AppGlobalParam appGlobal;

	@Scheduled(cron="0 0 8 * * *")
	public void everyDay() {
		log.info("reload token");
		appGlobal.EnrollUser();
	}

}

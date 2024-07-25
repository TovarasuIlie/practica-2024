package com.PracticaVara.springJwt.config;

import com.PracticaVara.springJwt.service.AnnouncementService;
import org.springframework.scheduling.annotation.Scheduled;

public class ScheduledTasks {
    private final AnnouncementService announcementService;

    public ScheduledTasks(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    // cica asta ruleaza la 00:00 zilnic, acuma eu stiu?
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteExpiredAnnouncements(){
        announcementService.deleteExpiredAnnouncements();
    }
}

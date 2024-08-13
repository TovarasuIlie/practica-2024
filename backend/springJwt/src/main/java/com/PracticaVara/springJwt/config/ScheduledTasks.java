package com.PracticaVara.springJwt.config;

import com.PracticaVara.springJwt.service.AnnouncementManagementService;
import org.springframework.scheduling.annotation.Scheduled;

public class ScheduledTasks {
    private final AnnouncementManagementService announcementManagementService;

    public ScheduledTasks(AnnouncementManagementService announcementManagementService) {
        this.announcementManagementService = announcementManagementService;
    }


    // cica asta ruleaza la 00:00 zilnic, acuma eu stiu?
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteExpiredAnnouncements(){
        announcementManagementService.deleteExpiredAnnouncements();
    }
}

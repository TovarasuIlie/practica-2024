package com.PracticaVara.springJwt.DTOs;

import com.PracticaVara.springJwt.model.Announcement;
import com.PracticaVara.springJwt.model.LogHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDetailsDTO {
    private Long numberOfUsers;
    private Long numberOfUsersToday;
    private Long numberOfAnnouncements;
    private Long numberOfAnnouncementsToday;
    private Long numberOfSuspendedAccount;
    private Long numberOfSuspendedAccountToday;
    private Long numberOfUnsolvedReports;
    private Long numberOfUnsolvedReportsToday;
    private List<Announcement> last24Ads;
    private List<LogHistory> last24Logs;
}

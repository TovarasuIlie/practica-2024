package com.PracticaVara.springJwt.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}

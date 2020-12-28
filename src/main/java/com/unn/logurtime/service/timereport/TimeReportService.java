package com.unn.logurtime.service.timereport;

import com.unn.logurtime.model.TimeReport;

import java.util.List;
import java.util.Map;

public interface TimeReportService {
    TimeReport addTimeReport(TimeReport timeReport);
    TimeReport addTimeReport(String username, Long taskId, int spentTime);
    TimeReport getTimeReport(Long timeReportId);
    List<TimeReport> getTimeReportsByUsername(String username);
    List<TimeReport> getTimeReportsByTaskId(Long taskId);
    void deleteTimeReport(TimeReport timeReport);
    TimeReport updateTimeReport(TimeReport timeReport);
    TimeReport updateTimeReport(Map<String, Object> newValues);
    int calculateSpentTime(List<TimeReport> timeReports);
}

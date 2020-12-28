package com.unn.logurtime.service.timereport;

import com.unn.logurtime.model.Task;
import com.unn.logurtime.model.TimeReport;
import com.unn.logurtime.model.User;
import com.unn.logurtime.model.constants.Constants;
import com.unn.logurtime.repository.TaskRepository;
import com.unn.logurtime.repository.TimeReportRepository;
import com.unn.logurtime.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Service
public class TimeReportServiceImpl implements TimeReportService {
    @Autowired
    TimeReportRepository timeReportRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskRepository taskRepository;

    @Override
    public TimeReport addTimeReport(TimeReport timeReport) {
        return timeReportRepository.saveAndFlush(timeReport);
    }

    @Override
    public TimeReport addTimeReport(String username, Long taskId, int spentTime) {
        User user = userRepository.findByUsername(username);
        Task task = taskRepository.getOne(taskId);
        if (user != null && task != null) {
            return addTimeReport(new TimeReport(user, task, spentTime));
        }
        return null;
    }

    @Override
    public TimeReport getTimeReport(Long timeReportId) {
        return timeReportRepository.getOne(timeReportId);
    }

    @Override
    public List<TimeReport> getTimeReportsByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return timeReportRepository.findByUser(user);
        }
        return null;
    }

    @Override
    public List<TimeReport> getTimeReportsByTaskId(Long taskId) {
        Task task = taskRepository.getOne(taskId);
        if (task != null) {
            return timeReportRepository.findByTask(task);
        }
        return null;
    }

    @Override
    public void deleteTimeReport(TimeReport timeReport) {
        timeReportRepository.delete(timeReport);
    }

    @Override
    public TimeReport updateTimeReport(TimeReport timeReport) {
        return timeReportRepository.saveAndFlush(timeReport);
    }

    @Override
    public TimeReport updateTimeReport(Map<String, Object> newValues) {
        Long reportId = (Long) newValues.get(Constants.Common.Parameters.ID);
        TimeReport timeReport = reportId != null ?
                timeReportRepository.getOne((Long) newValues.get(Constants.Common.Parameters.ID))
                : null;
        if (timeReport != null) {
            for(Map.Entry<String, Object> entry: newValues.entrySet()) {
                switch (entry.getKey()) {
                    case Constants.TimeReport.Parameters.USER_ID:
                        timeReport.setUser(userRepository.findByUsername((String) entry.getValue()));
                        break;
                    case Constants.TimeReport.Parameters.TASK_ID:
                        timeReport.setTask(taskRepository.getOne((Long) entry.getValue()));
                        break;
                    case Constants.TimeReport.Parameters.SPENT_TIME:
                        timeReport.setSpentTime((Integer) entry.getValue());
                        break;
                    default:
                        ;
                }
            }
        }

        return timeReport;
    }

    @Override
    public int calculateSpentTime(List<TimeReport> timeReports) {
        int result = 0;
        if (!CollectionUtils.isEmpty(timeReports)) {
            result = timeReports.stream().mapToInt(TimeReport::getSpentTime).sum();
        }
        return result;
    }
}

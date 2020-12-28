package com.unn.logurtime.controller;

import com.unn.logurtime.model.TimeReport;
import com.unn.logurtime.model.constants.Constants;
import com.unn.logurtime.service.timereport.TimeReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report")
public class TimeReportController {
    @Autowired
    private TimeReportService timeReportService;

    @PostMapping()
    public TimeReport newTimeReport(@RequestBody TimeReport timeReport) {
        return timeReportService.addTimeReport(timeReport);
    }

    @PostMapping("/create")
    public TimeReport newTimeReport(@RequestBody String username,
                                    @RequestBody Long taskId,
                                    @RequestBody int spentTime) {
        return timeReportService.addTimeReport(username, taskId, spentTime);
    }

    @GetMapping("/{id}")
    public TimeReport getTimeReport(@PathVariable Long id) {
        return timeReportService.getTimeReport(id);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<TimeReport>> getTimeReportsByUsername(@PathVariable String username) {
        if (StringUtils.isEmpty(username)) {
            return new ResponseEntity<>(timeReportService.getTimeReportsByUsername(username), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTimeReport(@PathVariable Long id) {
        TimeReport timeReport = timeReportService.getTimeReport(id);
        if (timeReport != null) {
            timeReportService.deleteTimeReport(timeReport);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping()
    public TimeReport updateTimeReport(@RequestBody TimeReport timeReport){
        return timeReportService.updateTimeReport(timeReport);
    }

    @PutMapping("/update")
    public ResponseEntity<TimeReport> updateTimeReport(@RequestBody Map<String, Object> values){
        if (!CollectionUtils.isEmpty(values) && values.containsKey(Constants.Common.Parameters.ID)) {
            timeReportService.updateTimeReport(values);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

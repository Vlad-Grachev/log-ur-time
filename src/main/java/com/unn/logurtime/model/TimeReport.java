package com.unn.logurtime.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "time_reports")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TimeReport {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "time_reports_seq")
    @SequenceGenerator(name = "time_reports_seq", sequenceName = "time_reports_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "task_id")
    private Task task;

    @Column(name = "spent_time")
    private int spentTime;

    public TimeReport() {
    }

    public TimeReport(User user, Task task, int spentTime) {
        this.user = user;
        this.task = task;
        this.spentTime = spentTime;
    }
}

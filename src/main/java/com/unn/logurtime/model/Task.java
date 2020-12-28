package com.unn.logurtime.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "tasks"})
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tasks_seq")
    @SequenceGenerator(name = "tasks_seq", sequenceName = "tasks_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "estimation")
    private int estimation;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "creator")
    @JsonIgnoreProperties({"password", "role"})
    private User creator;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "assignee")
    @JsonIgnoreProperties({"password", "role"})
    private User assignee;

    @Column(name = "status")
    private String status;

    @Column(name = "move_dd_counter")
    private int moveDdCounter;

    @Column(name = "priority")
    private String priority;

    @OneToMany(mappedBy = "task", fetch = FetchType.EAGER)
    private List<TimeReport> timeReports;

    public Task() {
    }
}

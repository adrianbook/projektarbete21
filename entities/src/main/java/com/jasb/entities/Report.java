package com.jasb.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne(cascade = CascadeType.DETACH)
    private Toilet toilet;
    @ManyToOne(cascade = CascadeType.DETACH)
    private ToiletUser owningUser;
    private String issue;
    private boolean notAToilet;
    @Transient
    private int toiletId;

    public Report(String issue, boolean notAToilet, int toiletId) {
        this.issue = issue;
        this.notAToilet = notAToilet;
        this.toiletId = toiletId;
    }
}

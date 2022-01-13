package com.jasb.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;
    private int rating;
    private String notes;
    @ManyToOne
    private ToiletUser toiletUser;
    @ManyToOne
    private Toilet toilet;
}

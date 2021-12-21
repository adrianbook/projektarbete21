package com.jasb.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating implements Serializable {
    private static final long serialVersionUID = 9133820020922670840L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;
    private int rating;
    private String notes;
    /*@ManyToOne
    private Toilet toilet;*/
}

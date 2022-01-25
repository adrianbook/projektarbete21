
package com.jasb.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
//  @Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"toilet_user_id", "toilet_id"})})
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int rating;
    private String notes;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "toilet_user_id")
    private ToiletUser toiletUser;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "toilet_id")
    private Toilet toilet;
    @Transient
    private long toiletId;

    public Rating(long toiletId, int rating, String notes) {
        this.toiletId = toiletId;
        this.rating = rating;
        this.notes = notes;
    }

    public Rating(Toilet toilet, ToiletUser toiletUser, int rating, String notes) {
        this.toilet = toilet;
        this.toiletUser = toiletUser;
        this.rating = rating;
        this.notes = notes;
    }

    @Override
    public String toString() {
        return String.format("rating_id: %d, toilet_id: %d, user_id: %d, rating: %d",
                this.id, this.toilet.getId(), this.toiletUser.getId(), this.rating);
    }
}

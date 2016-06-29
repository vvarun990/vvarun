/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Sangram
 */
public class SeatHold {

    private int id;
    private String email;
    private Date holdTime;
    private Set<Seat> seats;
    //Auto generate seathold id
    private static final AtomicInteger ID = new AtomicInteger();

    public SeatHold(String email, Set<Seat> seats) {
        this.email = email;
        this.seats = seats;
        this.holdTime = new Date();
        this.id = ID.incrementAndGet();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getHoldTime() {
        return holdTime;
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }  

}

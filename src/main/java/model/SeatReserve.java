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
public class SeatReserve {
    private String id;
    private Set<Seat> seats;
    private String email;
    private Date date;
    //Auto generate seat reseve id
    private static final AtomicInteger ID = new AtomicInteger();
    
    public SeatReserve(Set<Seat> seats, String email){
        this.seats = seats;
        this.email = email;
        this.date = new Date();
        this.id = String.valueOf(ID.incrementAndGet());
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }
}

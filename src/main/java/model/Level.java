/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author Sangram
 */
public class Level {

    private int id;
    private String name;
    private int numberOfRows;
    private int seatsPerRow;
    private double price;
    private Queue<Seat> availableSeats;

    public Level(int id, String name, int numberOfRows, int seatPerRow, double price) {
        this.id = id;
        this.name = name;
        this.numberOfRows = numberOfRows;
        this.seatsPerRow = seatPerRow;
        this.price = price;
        this.availableSeats = new PriorityQueue<>(Seat.SEAT_COMPARATOR);

        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < seatPerRow; j++) {
                availableSeats.add(new Seat(id, i, j));
            }
        }
    }

    public int getNumberOfAvailableSeats() {
        return availableSeats.size();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    public void setSeatsPerRow(int seatsPerRow) {
        this.seatsPerRow = seatsPerRow;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    //get specified number of seats from available seats

    public Set<Seat> takeSeats(int numOfSeats) {
        Set<Seat> result = new HashSet<Seat>();
        for (int i = 0; i < numOfSeats; i++) {
            if (availableSeats.size() > 0) {
                result.add(availableSeats.poll());
            }
        }
        return result;
    }

    public void addEmptySeats(Seat seat) {
        availableSeats.add(seat);
    }
}

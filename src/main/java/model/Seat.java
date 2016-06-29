/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Comparator;

/**
 *
 * @author Sangram
 */
public class Seat {

    private Integer level;
    private Integer row;
    private Integer number;

    public Seat(int level, int row, int number) {
        this.level = level;
        this.row = row;
        this.number = number;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    static final Comparator<Seat> SEAT_COMPARATOR = new Comparator<Seat>() {
        @Override
        public int compare(Seat o1, Seat o2) {
            //Check for level
            int compareLevel = o1.getLevel().compareTo(o2.getLevel());
            if (compareLevel != 0) {
                return compareLevel;
            }
            //Check for rows
            int compareRow = o1.getRow().compareTo(o2.getRow());
            if (compareRow != 0) {
                return compareRow;
            }
            //check for seat number
            return o1.getNumber().compareTo(o2.getNumber());
        }
    };

}

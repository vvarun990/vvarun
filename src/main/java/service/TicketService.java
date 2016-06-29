/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import exception.InvalidEmail;
import exception.LevelOutofBoundException;
import exception.SeatCountExceeded;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Logger;
import model.Level;
import model.Seat;
import model.SeatHold;
import model.SeatReserve;

/**
 *
 * @author Sangram
 */
public class TicketService implements TicketInterface {

    private static final Logger TICKET_SERVICE_LOGGER = Logger.getLogger(TicketService.class.getName());
    //venue levels
    private final Map<Integer, Level> levels;
    //Store held seats
    public final Queue<SeatHold> heldSeats;
    //Store reserved seats
    private final Map<String, SeatReserve> reservedSeats;
    //After 2 seconds, held seats can be made available again
    private static final long HOLD_TTL = 2000l;

    public TicketService() {
        TICKET_SERVICE_LOGGER.info("TicketService constructor. Initialize all values.");//Update msg
        this.levels = new HashMap<Integer, Level>();
        Level[] createdLevels = new Level[]{
            new Level(1, "Orchestra", 25, 50, 100.00),
            new Level(2, "Main", 20, 100, 75.00),
            new Level(3, "Balcony 1", 15, 100, 50.00),
            new Level(4, "Balcony 2", 15, 100, 40.00)
        };
        for (Level l : createdLevels) {
            levels.put(l.getId(), l);
        }
        heldSeats = new PriorityQueue<SeatHold>();
        reservedSeats = new HashMap<String, SeatReserve>();
    }

    @Override
    public int numSeatsAvailable(Optional<Integer> venueLevel) throws LevelOutofBoundException {
        if (venueLevel.isPresent() && venueLevel.get() > 4) {
            TICKET_SERVICE_LOGGER.log(java.util.logging.Level.SEVERE, "Exception Occurred: Level number cannot be greater than 4");
            throw new LevelOutofBoundException("Level number cannot be greater than 4");
        }

        int result = 0;
        if (venueLevel.isPresent()) {
            //Get available seats for that level
            result = levels.get(venueLevel.get()).getNumberOfAvailableSeats();

        } else {
            //Get all available seats
            for (Level l : levels.values()) {
                result += l.getNumberOfAvailableSeats();
            }
        }
        return result;
    }

    @Override
    public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel, String customerEmail) throws SeatCountExceeded, InvalidEmail {
        //If seat number is invalid return null
        if (numSeats < 0) {
            return null;
        }
        //Check if number of seats less than 6250
        if (numSeats > 6250) {
            TICKET_SERVICE_LOGGER.log(java.util.logging.Level.SEVERE, "Exception occured: Maximum number of seats that you can reserve is 6250.");
            throw new SeatCountExceeded("Maximum number of seats that you can reserve is 6250.");
        }
        if (customerEmail == null) {
            TICKET_SERVICE_LOGGER.log(java.util.logging.Level.SEVERE, "Exception occured: Email can't be equal to null.");
            throw new InvalidEmail("Email can't be equal to null.");
        }

        //Check if minLevel and maxLevel are present. If equal to null assign min value as 1 and max as number of levels
        int minL, maxL;
        if (minLevel.isPresent()) {
            minL = minLevel.get();
        } else {
            minL = 1;
        }
        if (maxLevel.isPresent()) {
            maxL = maxLevel.get();
        } else {
            maxL = levels.size();
        }
        //Calculate available seats between these levels
        int availableSeats = 0;
        for (int i = minL; i <= maxL; i++) {
            availableSeats += levels.get(i).getNumberOfAvailableSeats();
        }
        //If available seats less than numSeats then return null
        if (availableSeats < numSeats) {
            return null;
        }

        Set<Seat> seats = new HashSet<Seat>();
        for (int i = minL; i <= maxL && numSeats > 0; i++) {
            Level currentLevel = levels.get(i);
            //Check if required seats can be accomodated in the currentl level. If not, we will move on to next level after
            //filling current level
            int levelAvailableSeats = Math.min(currentLevel.getNumberOfAvailableSeats(), numSeats);
            //if empty seats found
            if (levelAvailableSeats > 0) {
                seats.addAll(currentLevel.takeSeats(levelAvailableSeats));
            }
            //Subtract already taken seats and move on to next level with remaining seats
            numSeats -= levelAvailableSeats;

        }
        //Hold seats
        SeatHold res = new SeatHold(customerEmail, seats);
        heldSeats.add(res);
        return res;
    }

    @Override
    public String reserveSeats(int seatHoldId, String customerEmail) {
        Iterator<SeatHold> i = heldSeats.iterator();
        while (i.hasNext()) {
            SeatHold seatHold = i.next();
            //If given id and email matches with SeatHold id and email then reserve seats
            if (seatHold.getEmail().equals(customerEmail) && seatHold.getId() == seatHoldId) {
                SeatReserve seatRes = new SeatReserve(seatHold.getSeats(), customerEmail);
                //Add setas to reservedSeats map
                reservedSeats.put(seatRes.getId(), seatRes);
                //remove seats from heldSeats 
                i.remove();
                //Return generated seat reserve id
                return seatRes.getId();
            } else {
                //incorrect id or email
                return "ID or email does not match records. Please check the entered details.";
            }
        }
        return null;
    }

    //For removing held seats. Method needs to be called explicitly.

    public void removeHold() throws InterruptedException {
        Iterator<SeatHold> i = heldSeats.iterator();
        while (i.hasNext()) {
            Date now = new Date();
            SeatHold seatHold = i.next();
            //after 2 seconds, held seats are removed
            if (seatHold.getHoldTime().getTime() + HOLD_TTL < now.getTime()) {
                for (Seat s : seatHold.getSeats()) {
                    Level level = levels.get(s.getLevel());
                    level.addEmptySeats(s);
                }
                heldSeats.remove(seatHold);
            }
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import exception.InvalidEmail;
import exception.LevelOutofBoundException;
import exception.SeatCountExceeded;
import java.util.Optional;
import model.SeatHold;
import org.junit.Assert; 
import org.junit.Test;
import service.TicketService;

/**
 *
 * @author Sangram
 */
public class TicketServiceTest {
    
    private TicketService ticketService = new TicketService();

    //Check Available Seats for Level 1
    @Test
    public void testNumSeatsAvailableLevel1() throws LevelOutofBoundException{
        Assert.assertEquals(ticketService.numSeatsAvailable(Optional.of(1)), 1250);       
    }
    //Check Available Seats for Level 2
    @Test
    public void testNumSeatsAvailableLevel2() throws LevelOutofBoundException{
        Assert.assertEquals(ticketService.numSeatsAvailable(Optional.of(2)), 2000);
    }
    //Check Available Seats for Level 3
    @Test
    public void testNumSeatsAvailableLevel3() throws LevelOutofBoundException{
        Assert.assertEquals(ticketService.numSeatsAvailable(Optional.of(3)), 1500);
    }
    //Check Available Seats for Level 4
    @Test
    public void testNumSeatsAvailableLevel4() throws LevelOutofBoundException{
        Assert.assertEquals(ticketService.numSeatsAvailable(Optional.of(4)), 1500);
    }
    //Check Available Seats when no level is specified
    @Test
    public void testNumSeatsAvailableNull() throws LevelOutofBoundException{
        Integer a = null;
        Optional <Integer> temp  = Optional.ofNullable(a);
        Assert.assertEquals(ticketService.numSeatsAvailable(temp), 6250);
    }
    //Check for exception if level number exceeds available limit which is 4
    @Test
    public void testLevelNumberExceededException(){
        boolean test = false;
        try{
            //Only 4 levels are present. When 5 is sent as level id it will throw LevelNumberExceededException.
            ticketService.numSeatsAvailable(Optional.of(5));
            
        }catch(LevelOutofBoundException e){
            //Catch LevelNumberExceededException and check for message. If it matches, set boolean value true.
            if(e.getMessage().contains("Level number cannot be greater than 4")){
                test = true;
            }
        }finally{
            //If exception was thrown, then test will hold true.
            Assert.assertTrue(test);
        }
        
    }
    
    //When null levels are sent, it should start assigning seats from level 1
    @Test
    public void testFindAndHoldSeatsWithNullLevel() throws LevelOutofBoundException, SeatCountExceeded, InvalidEmail{
        Integer a = null;
        Optional <Integer> temp  = Optional.ofNullable(a);
        Integer b = null;
        Optional <Integer> temp2  = Optional.ofNullable(b);
        SeatHold seatHold = ticketService.findAndHoldSeats(5, temp, temp2, "abc@xyz.com");
        //When no level is specified, it will start from first level
        Assert.assertEquals(ticketService.numSeatsAvailable(Optional.of(1)), 1245);
    }
    //For holding seats across multiple levels first previous level should be completely filled
    @Test
    public void testFindAndHoldSeatsAcrossMultipleLevels() throws LevelOutofBoundException, SeatCountExceeded, InvalidEmail{
        //First level 1 is filled and then it will move on to level 2.
        SeatHold seatHold = ticketService.findAndHoldSeats(2250, Optional.of(1), Optional.of(2), "abc@xyz.com");
        //Number of seats available in level 1 = 0
        Assert.assertEquals(ticketService.numSeatsAvailable(Optional.of(1)), 0);
        //Number of seats available in level 2 = 2250-1250 = 1000
        Assert.assertEquals(ticketService.numSeatsAvailable(Optional.of(2)), 1000);
    }
    
    //test when seat count is less than 0
    @Test
    public void testSeatCountLessThanZero() throws SeatCountExceeded, InvalidEmail{
        SeatHold seatHold = ticketService.findAndHoldSeats(-2, Optional.of(1), Optional.of(4), "abc@xyz.com");
        //Invalid number of seats. So it will return null object.
        Assert.assertNull(seatHold);
    }
    
    //Check for exception if seat count is greater than 6250
    @Test
    public void testSeatCountExceededException() throws SeatCountExceeded, InvalidEmail{
        //initialize boolean variable to false
        boolean test = false;
        try{
            Integer a = null;
            Optional<Integer> temp = Optional.ofNullable(a);
            Integer b = null;
            Optional<Integer> temp2 = Optional.ofNullable(b);
            //Send request for holding 6251 seats. Maximum venue size is 6250. So it will throw SeatCountExceeded exception
            SeatHold seatHold = ticketService.findAndHoldSeats(6251, temp, temp2, "abc@xyz.com");
            
        }catch(SeatCountExceeded e){
            //Exception is catched and if we get same message then set boolean value to true
            if(e.getMessage().contains("Maximum number of seats that you can reserve is 6250.")){
                test = true;
            }
        }finally{
            //If exception was thrown, inside catch block boolean value would be set to true
            Assert.assertTrue(test);
        }
    }
    
    @Test
    public void testFindAndHoldSeatsWhenLevelFull() throws SeatCountExceeded, InvalidEmail{
        SeatHold seatHold = ticketService.findAndHoldSeats(1000, Optional.of(1), Optional.of(1), "abc@xyz.com");
        SeatHold seatHold2 = ticketService.findAndHoldSeats(251, Optional.of(1), Optional.of(1), "abc@xyz.com");
        //First object holds 1000 seats. When 251 seats are requested second time, it would return null object
        Assert.assertNull(seatHold2);
    }
    
    //test when null email is sent
    @Test
    public void testFindAndHoldSeatsNullEmail() throws SeatCountExceeded, InvalidEmail{
        //initialize boolean variable to false
        boolean test = false;
        try{
            SeatHold seatHold = ticketService.findAndHoldSeats(1000, Optional.of(1), Optional.of(1), null);
        }catch (InvalidEmail e){
            //Exception is catched and if we get same message then set boolean value to true
            if(e.getMessage().contains("Email can't be equal to null.")){
                test = true;
            }
        }finally{
            //If exception was thrown, inside catch block boolean value would be set to true
            Assert.assertTrue(test);
        }
    }
    
    //Check if holds are removed after 2 sec Not working right now
    @Test
    public void testRemoveHold() throws InterruptedException, LevelOutofBoundException, SeatCountExceeded, InvalidEmail{
        SeatHold seatHold = ticketService.findAndHoldSeats(5, Optional.of(1), Optional.of(1), "abc@xyz.com");
        int availableSeatsInLevel1 = ticketService.numSeatsAvailable(Optional.of(1));
        //After holding 5 seats, number of available seats will be 1245
        Assert.assertEquals(1245, availableSeatsInLevel1);
        //After 2 seconds, all holds are removed. So wait for 2 seconds and then call removehold method.
        Thread.sleep(2001);
        ticketService.removeHold();
        int availableSeatsInLevel1AfterRemovingHold = ticketService.numSeatsAvailable(Optional.of(1));
        //After removing hold, again seat count is restored to 1250
        Assert.assertEquals(1250, availableSeatsInLevel1AfterRemovingHold);
    }
    //Check if held seats can be reserved with correct id and email.
    @Test
    public void testReserveSeats() throws LevelOutofBoundException, SeatCountExceeded, InvalidEmail{
        SeatHold seatHold = ticketService.findAndHoldSeats(5, Optional.of(1), Optional.of(1), "abc@xyz.com");
        String res_Id = ticketService.reserveSeats(seatHold.getId(), seatHold.getEmail());
        //Reserved 5 seats
        Assert.assertEquals(seatHold.getSeats().size(), 5);
        //Held seats are reserved only with correct email and hold id. So count will reduce by 5 and we will get reservation confirmation id
        Assert.assertEquals(ticketService.numSeatsAvailable(Optional.of(1)), 1245);
    }
    
    //Check if held seats can be reserved with incorrect id or email
    @Test
    public void testReserveSeatsWrongEmail() throws SeatCountExceeded, InvalidEmail{
        SeatHold seatHold = ticketService.findAndHoldSeats(5, Optional.of(1), Optional.of(1), "abc@xyz.com");
        //Held seats are reserved only with correct email and hold id. We are passing wrong email id. So it will return an error message.
        String message = ticketService.reserveSeats(seatHold.getId(), "xyz@abc.com");
        Assert.assertEquals(message, "ID or email does not match records. Please check the entered details.");
    }
    
    
}

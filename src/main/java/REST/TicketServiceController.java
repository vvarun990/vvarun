/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;

import exception.InvalidEmail;
import exception.LevelOutofBoundException;
import exception.SeatCountExceeded;
import java.util.Optional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import model.SeatHold;
import service.TicketService;

/**
 *
 * @author Sangram
 */
@Path("/ticketService")
public class TicketServiceController {

    //find number of seats available in entire venue.

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public int findNumSeatsAvailable() throws LevelOutofBoundException {
        int res = 0;
        TicketService ticketService = new TicketService();
        Integer a = null;
        Optional<Integer> num = Optional.ofNullable(a);
        //get number of seats available 
        res = ticketService.numSeatsAvailable(num);
        return res;
    }

    //find number of seats available in a particular venue specified by path parameter levelId

    @GET
    @Path("/{levelId}")
    @Produces(MediaType.APPLICATION_JSON)
    public int findNumSeatsAvailableSpecifiedLevel(@PathParam("levelId") int levelId) throws LevelOutofBoundException {
        int res = 0;
        TicketService ticketService = new TicketService();
        Optional<Integer> num = Optional.of(levelId);
        res = ticketService.numSeatsAvailable(num);
        return res;
    }

    //Hold seats. lowerlevel and higherlevel parameters are optional
    //Sample request: http://localhost:8080/WalmartTicketServiceREST/api/ticketService/holdSeats?numOfSeats=15&lowerLevel=1&higherLevel=3&email=abc@xyz.com

    @POST
    @Path("/holdSeats")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public SeatHold findAndHoldSeats(@QueryParam("numOfSeats") int numOfSeats, @QueryParam("lowerLevel") Integer lowerLevel,
            @QueryParam("higherLevel") Integer higherLevel, @QueryParam("email") String email) throws SeatCountExceeded, InvalidEmail {
        TicketService ticketService = new TicketService();
        Optional<Integer> num1 = Optional.ofNullable(lowerLevel);
        Optional<Integer> num2 = Optional.ofNullable(higherLevel);
        SeatHold seatHold = ticketService.findAndHoldSeats(numOfSeats, num1, num2, email);
        return seatHold;
    }

    //Reserve seats. lowerlevel and higherlevel parameters are optional
    //Sample request: http://localhost:8080/WalmartTicketServiceREST/api/ticketService/reserve?numOfSeats=15&lowerLevel=1&higherLevel=3&email=abc@xyz.com    

    @POST
    @Path("/reserve")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String reserveSeats(@QueryParam("numOfSeats") int numOfSeats, @QueryParam("lowerLevel") Integer lowerLevel,
            @QueryParam("higherLevel") Integer higherLevel, @QueryParam("email") String email) throws SeatCountExceeded, InvalidEmail {
        TicketService ticketService = new TicketService();
        Optional<Integer> num1 = Optional.ofNullable(lowerLevel);
        Optional<Integer> num2 = Optional.ofNullable(higherLevel);
        SeatHold seatHold = ticketService.findAndHoldSeats(numOfSeats, num1, num2, email);
        String res = ticketService.reserveSeats(seatHold.getId(), seatHold.getEmail());
        return res;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import REST.TicketServiceController;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Sangram
 */
public class TicketServiceControllerTest extends JerseyTest{
    
    public TicketServiceControllerTest() {
    }
    
    
    @Override
    protected Application configure(){
       return new ResourceConfig(TicketServiceController.class); 
    }
   
    //Test for getting number of seats available in all levels
    @Test
    public void testFindNumSeatsAvailable(){
        String s = target("ticketService").request().get(String.class);
        int numOfSeats = Integer.parseInt(s);
        Assert.assertEquals(numOfSeats, 6250);
    }
    
    //Test for getting number of seats for level 1
    @Test
    public void testFindNumSeatsAvailableLevel1(){
        String s = target("ticketService/1").request().get(String.class);
        int numOfSeats = Integer.parseInt(s);
        Assert.assertEquals(numOfSeats, 1250);
    }
    
    //Test for getting number of seats for level 2
    @Test
    public void testFindNumSeatsAvailableLevel2(){
        String s = target("ticketService/2").request().get(String.class);
        int numOfSeats = Integer.parseInt(s);
        Assert.assertEquals(numOfSeats, 2000);
    }
    
    //Test for getting number of seats for level 3
    @Test
    public void testFindNumSeatsAvailableLevel3(){
        String s = target("ticketService/3").request().get(String.class);
        int numOfSeats = Integer.parseInt(s);
        Assert.assertEquals(numOfSeats, 1500);
    }
    
    //Test for getting number of seats for level 4
    @Test
    public void testFindNumSeatsAvailableLevel4(){
        String s = target("ticketService/4").request().get(String.class);
        int numOfSeats = Integer.parseInt(s);
        Assert.assertEquals(numOfSeats, 1500);
    }
    
    //Test for getting number of seats for level more than present. So exception will be thrown
    //Hence check for response type. It should be equal to 500
    @Test
    public void testFindNumSeatsAvailableLevelException(){
        Response response = target("ticketService/5").request().get();
        int status = response.getStatus();
        Assert.assertEquals(status, 500);
    }
    
    //Test to see if we can hold seats properly. We can read the response with the help of readEntity
    @Test
    public void testFindAndHoldSeats(){
        Response response = target("ticketService/holdSeats").queryParam("lowerLevel", 3).queryParam("higherLevel", 4).queryParam("numOfSeats", 10).queryParam("email", "abc@xyz.com").request().post(null);
        Assert.assertEquals(response.getStatus(), 200);
    }
    //Test when levels are not specified. It holds the mentioned number of seats from level 1
    @Test
    public void testFindAndHoldSeatsLevelNotSpecified(){
        Response response = target("ticketService/holdSeats").queryParam("numOfSeats", 1).queryParam("email", "abc@xyz.com").request().post(null);
        Assert.assertEquals(response.getStatus(), 200);
    }
    
    //test when email is null while holding seats
    @Test
    public void testFindAndHoldSeatsEmailNull(){
        Response response = target("ticketService/holdSeats").queryParam("numOfSeats", 1).request().post(null);
        Assert.assertEquals(response.getStatus(), 500);
    }
    
    //Reserve seats
    @Test
    public void testReserveSeats(){
        Response response = target("ticketService/reserve").queryParam("lowerLevel", 3).queryParam("higherLevel", 4).queryParam("numOfSeats", 10).queryParam("email", "abc@xyz.com").request().post(null);
        Assert.assertEquals(response.getStatus(), 200);
    }
}

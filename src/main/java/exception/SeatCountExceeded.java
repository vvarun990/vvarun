/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author Sangram
 */
//Exception when more than 6250 seats are requested.
public class SeatCountExceeded extends Exception{
     public SeatCountExceeded(String msg){
        super(msg);
    }
    public SeatCountExceeded(String msg, Throwable cause){
        super(msg, cause);
    }
}

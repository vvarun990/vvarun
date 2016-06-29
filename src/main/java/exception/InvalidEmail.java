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
//Exception when email sent to findAndHoldSeats method is null
public class InvalidEmail extends Exception{
    public InvalidEmail(String msg){
        super(msg);
    }
}

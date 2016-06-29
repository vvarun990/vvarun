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
//Exception when level id sent to findAvailableSeats exceeds available limit which is 4
public class LevelOutofBoundException extends Exception{
    public LevelOutofBoundException(String msg){
        super(msg);
    }
    public LevelOutofBoundException(String msg, Throwable cause){
        super(msg, cause);
    }
}

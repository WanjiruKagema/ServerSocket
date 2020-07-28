/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toysserver;

/**
 *
 * @author Kagema
 */
public class ToyServerProtocol {

    private static final int WAITING = 0;
    private static final int SENTTOYINFOREQUEST = 1;
    private static final int SENTPOSSIBLETOYREQUESTITEMS = 2;

    private int state = WAITING;
    private int currentItem = 0;

    private String[] possibleRequests = {"572", "Helicopter", "500", "Gulf"};
    private String[] moreItemsRequest = {"Please enter the Toy Name", "Please enter the toy price", "Please enter the manufacturer", "Thanks"};

    public String processRequest(String input) {
        String output = null;
        if (state == WAITING) {
            output = "WELCOME, PLEASE SEND THE TOY CODE";
            state = SENTTOYINFOREQUEST;
        } else if (state == SENTTOYINFOREQUEST) {
            if (input.equalsIgnoreCase(possibleRequests[currentItem])) {
                output = moreItemsRequest[currentItem];
                currentItem++;
                state = SENTTOYINFOREQUEST;
            }
        } 
//        else if (state == SENTPOSSIBLETOYREQUESTITEMS) {
//
//            if (input.equalsIgnoreCase(possibleRequests[currentItem])) {
//                output = moreItemsRequest[currentItem];
//                currentItem++;
//                state = SENTTOYINFOREQUEST;
//            }
//
//        }

        return output;
    }

}

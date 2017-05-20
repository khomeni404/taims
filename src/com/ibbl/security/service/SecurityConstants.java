package com.ibbl.security.service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Khomeni
 * Created on : 17-May-17 at 8:44 PM
 */

public interface SecurityConstants {

    String SecuritySessionBean_ser_path =  "D:\\cloud\\doc\\taims\\SecuritySessionBean.ser";
    //String SecuritySessionBean_ser_path =  "C:\\Users\\core-khomeni\\Downloads\\taims\\src\\com\\ibbl\\security\\resources\\SecuritySessionBean.ser";

    String SESSION_USER = "session.user";
    String SESSION_USER_ID = "session.user.id";
    String SESSION_USER_CASM_OID = "session.user.casm.oid";
    String SESSION_USER_CASM_USER_ID = "session.user.username";
    String SESSION_USER_GRANTED_ACTIONS = "session.user.actions";
    String SESSION_USER_GRANTED_MAPPINGS = "session.user.actions.mappings";
    String REQUEST_MAPPING = "RequestMapping";


    Map<String, String> MESSAGE_MAP = new HashMap<String, String>() {
        {
            put("FOUND", "Already exist.");
            put("NOT_FOUND", "Not available.");
            put("INVALID_FORMAT", "Invalid Data.");
            put("ZERO_LENGTH", "Privilege ID is missing.");
            put("NOT_EQUAL", "Values doesn't match.");
            put("EQUAL", "Values are same");
            put("SESSION_EXPIRED", "User Session expired. Please sign in again.");
            put("INSUFFICIENT_PRIVILEGE", "You have no privilege to execute this operation");
            put("ACCESS_DENIED", "Access denied.");
            put("NOT_LOGGED_IN", "User is not signed in. Please Sign in");
            put("IRREGULAR_STATE", "User Account state is inactive.");
            put("VALUE_MIS_MATCH", "Values doesn't match.");
            put("CASM_SERVER_CONNECTION_FAILED", "Unable to connect with CASM Server.");
            put("ITEM_ZERO_LENGTH", "Something is missing.");
            put("TYPE_MIS_MATCH", "doesn't match. Please check!!");

            /*CASM Messages*/
            put("MSG_FOUND", "Already exist.");
            put("MSG_NOT_FOUND", "Not available.");
            put("MSG_INVALID_FORMAT", "Invalid Data.");
            put("MSG_ZERO_LENGTH", "XML configuration missing. / Privilege ID is missing. / MSG_ZERO_LENGTH");
            put("MSG_NOT_EQUAL", "Values doesn't match.");
            put("MSG_EQUAL", "Values are same");
            put("MSG_SESSION_EXPIRED", "User Session expired. Please sign in again.");
            put("MSG_INSUFFICIENT_PRIVILEGE", "You have no privilege to execute this operation");
            put("MSG_ACCESS_DENIED", "Access denied.");
            put("MSG_NOT_LOGGED_IN", "User is not signed in. Please Sign in");
            put("MSG_IRREGULAR_STATE", "User Account state is inactive.");
            put("MSG_VALUE_MIS_MATCH", "Values doesn't match.");
            put("MSG_CASM_SERVER_CONNECTION_FAILED", "Unable to connect with CASM Server.");
            put("MSG_ITEM_ZERO_LENGTH", "Something is missing.");
            put("MSG_TYPE_MIS_MATCH", "doesn't match. Please check!!");
        }
    };
}

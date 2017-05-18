package com.ibbl.util;

import java.util.Arrays;
import java.util.List;

/**
 * @author Khomeni
 *         Created on : 16-May-17
 */
public interface Constants {

    List<String> MONTH_LIST = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

    List<String> INCIDENT_TYPE_LIST = Arrays.asList(
            "Internet",
            "eIBS",
            "Database",
            "Network",
            "Hardware",
            "Others"
    );

}

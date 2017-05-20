package com.ibbl.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

/**
 * @author Khomeni
 *         Created on : 16-May-17
 */
public interface Constants {

    SimpleDateFormat SDF_MM = new SimpleDateFormat("MM");
    SimpleDateFormat SDF_YY = new SimpleDateFormat("yy");
    SimpleDateFormat SDF_YYYY = new SimpleDateFormat("yyyy");
    SimpleDateFormat SDF_DD_MM_YYYY = new SimpleDateFormat("dd/MM/yyyy");



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

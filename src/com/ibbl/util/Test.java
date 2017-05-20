package com.ibbl.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Khomeni
 *         Created on : 20-May-17 at 12:57 AM
 */

public class Test {
    public static void main(String[] args) throws Exception{
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH);
        Date t= ft.parse("05/16/2017 07:56 AM");
        //ft.applyPattern("MM.dd");
        //System.out.println(ft.format(t));
        System.out.println(t);
    }
}

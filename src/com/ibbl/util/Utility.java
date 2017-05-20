package com.ibbl.util;

import com.ibbl.security.model.LoggedUser;
import com.ibbl.security.service.SecurityConstants;
import com.ibbl.security.service.SessionUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Khomeni
 * Created on : 16-May-17
 */
public class Utility {
    public static LoggedUser LU = SessionUtil.getSessionUser();

    public static List<String> getIncidentTypeList() {
        return Constants.INCIDENT_TYPE_LIST;
    }

    public static String getIncidentType(Integer index) {
        try {
            return Constants.INCIDENT_TYPE_LIST.get(index);
        } catch (Exception e) {
            return "";
        }
    }


    public static String getMessage(Exception e) {
        String msgKey = e.getMessage();
        if (GenericValidator.isBlankOrNull(msgKey)) {
            return e.toString();
        }
        String message = SecurityConstants.MESSAGE_MAP.get(msgKey);
        return message == null ? (e.getClass().getSimpleName() + " : " + msgKey) : message;

    }

    public static String getUniqueFileName() {
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = sdf.format(new Date());
        return dateString + "-" + uuid.version() + uuid.variant() + "-" + randomUUIDString;
    }

    public static String generateId_12() {
        DateFormat df = new SimpleDateFormat("yyMMddHHmmss");
        return df.format(new Date());
    }

    public static String getRandomNumber(String format) {
        DecimalFormat df = new DecimalFormat(format);
        Random rand = new Random();
        return df.format(rand.nextInt(50));
    }

    public static String getRandomChar(Integer len) {
        return RandomStringUtils.randomAlphanumeric(len);
    }

    public static List<String> generateListOfCode(Integer nos) {

        DecimalFormat df = new DecimalFormat("0000");
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 1; i < nos; i++) {
            list.add(df.format(i));
        }
        Collections.shuffle(list);
        return list;
    }

    public static String getFileExtension(MultipartFile file) {
        String name = file.getOriginalFilename();
        try {
            return name.substring(name.lastIndexOf("."));
        } catch (Exception e) {
            return "";
        }
    }

    public static String getFileNameWithoutExtension(MultipartFile file) {
        String name = file.getOriginalFilename();
        try {
            return name.substring(0, name.lastIndexOf("."));
        } catch (Exception e) {
            return "";
        }
    }

    public static String cutOrPadRight(String str, int size, String padStr) {
        str = str == null ? "" : str;
        int len = str.trim().length();
        str = len > size ? str.substring(0, size) : str;
        return StringUtils.rightPad(str, size, padStr);
    }

    public static String cutOrPadLeft(String str, int size, String padStr) {
        str = str == null ? "" : str;
        int len = str.trim().length();
        str = len > size ? str.substring(0, size) : str;
        return StringUtils.leftPad(str, size, padStr);
    }

    public static PrintWriter getWriter(HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "86400");

        return writer;
    }

    public static String getMonth(Integer monthSerial) {
        try {
            return Constants.MONTH_LIST.get(monthSerial - 1);
        } catch (Exception e) {
            return "";
        }
    }
}

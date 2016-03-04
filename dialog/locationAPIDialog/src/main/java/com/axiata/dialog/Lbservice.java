/*
 * Lbservice.java
 * Apr 2, 2013  11:20:38 AM
 * Roshan.Saputhanthri
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */
package com.axiata.dialog;

import com.axiata.dialog.entity.CurrentLocation;
import com.axiata.dialog.entity.LocationRequest;
import com.axiata.dialog.entity.TerminalLocation;
import com.axiata.dialog.entity.TerminalLocationList;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

import java.util.Random;

/**
 * <TO-DO>
 * <code>Lbservice</code>
 *
 * @version $Id: Lbservice.java,v 1.00.000
 */
public class Lbservice {

    private static final Logger LOG = Logger.getLogger(Lbservice.class.getName());

    
    /**
     * Return <code>randam</code> list of locations temporary. 
     * @param address, address value
     * @param requestedAccuracy, accuracy of the location to be retrived
     * @return <code>LocatinRequest</code> Object
     */
    public LocationRequest retriveLbs(String address, String requestedAccuracy) {

        String[] locarr = getrandamlocation().split(",");
        CurrentLocation cloc = new CurrentLocation(locarr[0], locarr[1], locarr[2], locarr[3],locarr[4]);
        cloc.setTimestamp(DateFormatUtils.formatUTC(new java.util.Date(), DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.getPattern()));        
        
        LocationRequest locationrequet = new LocationRequest();
        TerminalLocation tloc = new TerminalLocation();
        tloc.setCurrentLocation(cloc);
        tloc.setAddress(address);
        tloc.setLocationRetrievalStatus("Retrieved");
        
        TerminalLocationList tloclist = new TerminalLocationList();
        tloclist.setTerminalLocation(tloc);
        locationrequet.setTerminalLocationList(tloclist);
        
        return locationrequet;
    }

    public String getrandamlocation() {

        Random ran = new Random();
        int R = ran.nextInt(10 - 0) + 0;
        String randomlbs = FileUtil.getApplicationProperty("location" + R);
        return randomlbs;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author SBS
 */
import java.time.ZonedDateTime;

public class CalendarActivity {
    private String clientName;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;

    public CalendarActivity(String clientName, ZonedDateTime startDate, ZonedDateTime endDate) {
        this.clientName = clientName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getClientName() {
        return clientName;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }
}

  

package com.example.web.beans;

import com.example.common.converter.TimeConversion;
import com.example.web.model.TimeSheetSlotModel;
import com.example.web.service.TimeSheetService;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named("TimeSheetBean")
@RequestScoped
public class TimeSheetBean extends AbstractBean implements Serializable {

    private static final Logger log = Logger.getLogger(TimeSheetBean.class);

    @Inject
    private TimeSheetService timeSheetService;

    private List<TimeSheetSlotModel> timeSheetSlots;
    private Map<String, String> values = new HashMap<>();


    @PostConstruct
    public void onLoad() {
        super.initAccount();
        timeSheetSlots = timeSheetService.generateDailySlots(timeSheetService.getTodayEntries(getAccount().getTenantId(), getAccount().getId()));
        values.put("TODAY", getTodayDateFormatted());
    }

    public Map<String, String> getValues() {
        return values;
    }

    public List<TimeSheetSlotModel> getTimeSheetSlots() {
        return timeSheetSlots;
    }

    //todo: can use facelets converter now
    private String getTodayDateFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd-MMM-yyyy");
        return formatter.format(TimeConversion.getStartOfDay());
    }


}

package com.example.web.converter;

import com.example.common.converter.TimeConversion;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

@FacesConverter("instantToTimeConverter")
public class InstantConverter implements Converter {

    private final String FORMAT_VALUE = "HH:mm";
    private final String FORMAT_ATTRIBUTE = "format";

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (null == s || s.isEmpty()) {
            return null;
        }

        Instant instant;
        try {
            instant = Instant.parse(s.trim());
        } catch (DateTimeParseException e) {
            throw new ConverterException("Bad format for Instant");
        }

        return TimeConversion.fromInstant(instant);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (null == o) {
            return "";
        }

        if (!(o instanceof Instant)) {
            throw new ConverterException("Must be applied on an Instant");
        }

        LocalDateTime localDateTime;
        try {
            Instant instant = Instant.parse(o.toString().trim());
            localDateTime = TimeConversion.fromInstant(instant);
        } catch (DateTimeParseException e) {
            throw new ConverterException("Bad format for Instant");
        }

        Map<String, Object> attributes = uiComponent.getAttributes();
        String formatString = (String) attributes.get(FORMAT_ATTRIBUTE);
        if (null == formatString) {
            formatString = FORMAT_VALUE;
        }

        return localDateTime.format(DateTimeFormatter.ofPattern(formatString).withZone(ZoneOffset.UTC));
    }
}

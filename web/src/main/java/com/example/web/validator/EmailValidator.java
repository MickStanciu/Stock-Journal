package com.example.web.validator;


import org.apache.log4j.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@FacesValidator("emailValidator")
public class EmailValidator implements Validator {

    private static final Logger log = Logger.getLogger(EmailValidator.class);
    private static final String EMAIL_PATTERN = "^[(a-zA-Z0-9-_+.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$";

    private final Pattern pattern;

    public EmailValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        Matcher matcher = pattern.matcher(o.toString());
        log.info("Validating Email ...");
        if(!matcher.matches()){
            log.error("Email is not valid: " + o.toString());
            FacesMessage msg = new FacesMessage("Invalid E-mail format");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}

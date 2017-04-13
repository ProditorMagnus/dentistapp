package com.cgi.dentistapp.controller;

import com.cgi.dentistapp.dao.entity.DentistVisitEntity;
import com.cgi.dentistapp.dto.DentistVisitDTO;
import com.cgi.dentistapp.service.DentistVisitService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@EnableAutoConfiguration
public class DentistAppController extends WebMvcConfigurerAdapter {
    public static final int openH = 10;
    public static final int closeH = 18;
    public static final int appointmentsPerH = 2;
    public static final int appointmentLen = 60 / appointmentsPerH;

    @Autowired
    private DentistVisitService dentistVisitService;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }

    @GetMapping("/")
    public String showRegisterForm(DentistVisitDTO dentistVisitDTO) {
        return "form";
    }

    @PostMapping("/")
    public String postRegisterForm(@Valid DentistVisitDTO dentistVisitDTO, BindingResult bindingResult) {
        String timeField = "visitTimeH";
        String dateField = "visitTime";
        TimeValidationResult dateValidation = invalidDate(dentistVisitDTO);
        TimeValidationResult timeValidation = invalidTime(dentistVisitDTO);
        if (dateValidation.hasError(dateField)) {
            bindingResult.addError(new FieldError(dateField, dateField, dateValidation.getMessages(dateField)));
        }
        if (timeValidation.hasError(dateField)) {
            bindingResult.addError(new FieldError(dateField, dateField, timeValidation.getMessages(dateField)));
        }
        if (dateValidation.hasError(timeField)) {
            bindingResult.addError(new FieldError(timeField, timeField, dateValidation.getMessages(timeField)));
        }
        if (timeValidation.hasError(timeField)) {
            bindingResult.addError(new FieldError(timeField, timeField, timeValidation.getMessages(timeField)));
        }
        if (bindingResult.hasErrors()) {
            return "form";
        }
        dentistVisitService.addVisit(dentistVisitDTO.getDentistName(), dentistVisitDTO.getDocName(), dentistVisitDTO.getVisitTime(), dentistVisitDTO.getVisitTimeH());
        return "redirect:/results";
    }

    private TimeValidationResult invalidTime(DentistVisitDTO dentistVisitDTO) {
        String field = "visitTimeH";
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dentistVisitDTO.getVisitTimeH());
            if (calendar.get(Calendar.HOUR_OF_DAY) > closeH || calendar.get(Calendar.HOUR_OF_DAY) < openH) {
                return new TimeValidationResult(false, field, "Vigane vastuvõtukellaaeg");
            }
            if (calendar.get(Calendar.MINUTE) % appointmentLen != 0) {
                return new TimeValidationResult(false, field, "Vigane minutite hulk, tunnis on " + appointmentsPerH + " vastuvõttu");
            }
        } catch (NullPointerException e) {
            // message added by thymeleaf already
        } catch (Exception e) {
            return new TimeValidationResult(false, field, "Exception: " + e.getMessage());
        }
        return new TimeValidationResult(true);
    }

    private TimeValidationResult invalidDate(DentistVisitDTO dentistVisitDTO) {
        String field = "visitTime";
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dentistVisitDTO.getVisitTime());

            if (DateUtils.isSameDay(calendar, Calendar.getInstance())) {
                // same day, so need to check h too
                Calendar hours = Calendar.getInstance();
                hours.setTime(dentistVisitDTO.getVisitTimeH());
                long chosen = DateUtils.getFragmentInMinutes(hours, Calendar.DATE);
                long current = DateUtils.getFragmentInMinutes(Calendar.getInstance(), Calendar.DATE);
                if (chosen < current) {
                    // same day, and earlier time
                    return new TimeValidationResult(false, field, "Vastuvõtuaeg on minevikus 2").addInvalidField("visitTimeH").addMessage("visitTimeH", "Vastuvõtuaeg on minevikus");
                }
            } else if (calendar.before(Calendar.getInstance())) {
                return new TimeValidationResult(false, field, "Vastuvõtuaeg on minevikus 1");
            }
        } catch (NullPointerException e) {
            // message added by thymeleaf already
        } catch (Exception e) {
            e.printStackTrace();
            return new TimeValidationResult(false, field, "Exception: " + e.getMessage());
        }
        return new TimeValidationResult(true);
    }

    @GetMapping("/view")
    public String showRegistrations(DentistVisitDTO dentistVisitDTO, ModelMap model) {
        model.addAttribute("visits", dentistVisitService.listVisits());
        return "view";
    }

    @PostMapping("/view")
    public String searchRegistrations(DentistVisitDTO dentistVisitDTO, ModelMap model) {
        model.addAttribute("visits", dentistVisitService.listVisits().stream().filter(dentistVisitEntity -> searchMatches(dentistVisitDTO, dentistVisitEntity)).collect(Collectors.toList()));
        return "view";
    }

    private boolean searchMatches(DentistVisitDTO dentistVisitDTO, DentistVisitEntity dentistVisitEntity) {
        Date visitTime = dentistVisitDTO.getVisitTime();
        Date visitTimeH = dentistVisitDTO.getVisitTimeH();
        String dentistName = dentistVisitDTO.getDentistName();
        String docName = dentistVisitDTO.getDocName();
        if (docName != null && !"".equals(docName) && dentistVisitEntity.getDocName() != null && !docName.equals(dentistVisitEntity.getDocName())) {
            return false;
        }
        return true;
    }

    private static class TimeValidationResult {
        final boolean valid;
        final Map<String, List<String>> messages = new HashMap<>();
        final Set<String> invalidFields = new HashSet<>();

        private TimeValidationResult(boolean valid, String field, String... message) {
            this.valid = valid;
            invalidFields.add(field);
            messages.putIfAbsent(field, new ArrayList<>());
            messages.get(field).addAll(Arrays.asList(message));
        }

        private TimeValidationResult(boolean valid) {
            this.valid = valid;
        }

        private TimeValidationResult addInvalidField(String field) {
            invalidFields.add(field);
            messages.putIfAbsent(field, new ArrayList<>());
            return this;
        }

        private TimeValidationResult addMessage(String field, String message) {
            messages.get(field).add(message);
            return this;
        }

        private TimeValidationResult clearMessages() {
            messages.clear();
            return this;
        }

        private boolean hasError(String field) {
            return invalidFields.contains(field);
        }

        private String getMessages(String field) {
            StringBuilder sb = new StringBuilder();

            messages.get(field).forEach(sb::append);
            return sb.toString();
        }
    }
}

package com.cgi.dentistapp.controller;

import com.cgi.dentistapp.dao.entity.DentistVisitEntity;
import com.cgi.dentistapp.dto.DentistVisitDTO;
import com.cgi.dentistapp.service.DentistVisitService;
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
import java.util.Calendar;
import java.util.Date;
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
        if (bindingResult.hasErrors()) {
            return "form";
        }
        if (invalidDate(dentistVisitDTO)) {
            bindingResult.addError(new FieldError("visitTime", "visitTime", "Vigane kuupäev"));
        }
        if (invalidTime(dentistVisitDTO)) {
            bindingResult.addError(new FieldError("visitTimeH", "visitTimeH", "Vigane kellaaeg"));
        }
        if (bindingResult.hasErrors()) {
            return "form";
        }
        dentistVisitService.addVisit(dentistVisitDTO.getDentistName(), dentistVisitDTO.getDocName(), dentistVisitDTO.getVisitTime(), dentistVisitDTO.getVisitTimeH());
        return "redirect:/results";
    }

    private boolean invalidTime(DentistVisitDTO dentistVisitDTO) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dentistVisitDTO.getVisitTimeH());
            if (calendar.get(Calendar.HOUR_OF_DAY) > closeH || calendar.get(Calendar.HOUR_OF_DAY) < openH) {
                return true;
            }
            if (calendar.get(Calendar.MINUTE) % appointmentLen != 0) {
                return true;
            }
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    private boolean invalidDate(DentistVisitDTO dentistVisitDTO) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dentistVisitDTO.getVisitTime());
            if (calendar.before(Calendar.getInstance())) {
                return true;
            }
        } catch (Exception e) {
            return true;
        }
        return false;
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
}

package com.nomaan.Flightbooking.controller;

import com.nomaan.Flightbooking.model.Flight;
import com.nomaan.Flightbooking.service.FlightService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/flights")
public class FlightController {
    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public String getAllFlights(Model model) {
        List<Flight> flights = flightService.getAllFlights();
        model.addAttribute("flights", flights);
        return "flights";
    }

    // Get flight by ID
    @GetMapping("/{id}")
    public String getFlightById(@PathVariable String id, Model model) {
        Optional<Flight> flightOpt = flightService.getFlightById(id);
        if (flightOpt.isPresent()) {
            model.addAttribute("flight", flightOpt.get());
            return "flight";  // create flight.html to show flight details
        } else {
            return "redirect:/flights";
        }
    }

    // Get flight schedules by flight ID with optional date filtering
    @GetMapping("/{id}/schedules")
    public String getFlightSchedules(@PathVariable String id,
                                     @RequestParam(required = false) List<String> dates,
                                     Model model) {
        Optional<Flight> flightOpt = flightService.getFlightById(id);
        if (flightOpt.isPresent()) {
            Flight flight = flightOpt.get();
            List<String> schedules = flight.getScheduleDates();

            if (dates != null && !dates.isEmpty()) {
                schedules = schedules.stream()
                        .filter(dates::contains)
                        .collect(Collectors.toList());
            }

            model.addAttribute("flight", flight);
            model.addAttribute("schedules", schedules);
            return "schedules";  // create schedules.html to list schedules
        } else {
            return "redirect:/flights";
        }
    }
}

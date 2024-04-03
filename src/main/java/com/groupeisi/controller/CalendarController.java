package com.groupesisi.web.rest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalendarController {

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    @GetMapping("/services/calendar/dayfinder")
    public DayOfWeekResponse findDayOfWeek(@RequestParam("date") String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(dateStr, formatter);
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        SearchHistory history = new SearchHistory();
        history.setRequest(dateStr);
        history.setResponse(dayOfWeek.toString());
        searchHistoryRepository.save(history);

        return new DayOfWeekResponse(dateStr, dayOfWeek.toString());
    }

    @GetMapping("/historique/all")
    public List<SearchHistory> getSearchHistory() {
        return searchHistoryRepository.findAll();
    }

    static class DayOfWeekResponse {

        private String date;
        private String dayOfWeek;

        public DayOfWeekResponse(String date, String dayOfWeek) {
            this.date = date;
            this.dayOfWeek = dayOfWeek;
        }

        public String getDate() {
            return date;
        }

        public String getDayOfWeek() {
            return dayOfWeek;
        }
    }
}

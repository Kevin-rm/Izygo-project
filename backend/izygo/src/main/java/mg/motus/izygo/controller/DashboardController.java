package mg.motus.izygo.controller;

import mg.motus.izygo.service.DashboardService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/profitInPeriod")
    public Double profitInPeriod(
        @RequestParam("dateMin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String dateMin,
        @RequestParam("dateMax") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String dateMax
    ) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Calendar calendar = Calendar.getInstance();

        // Convertir et ajuster dateMin
        calendar.setTime(dateFormat.parse(dateMin));
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Timestamp timestampMin = new Timestamp(calendar.getTimeInMillis());

        // Convertir et ajuster dateMax
        calendar.setTime(dateFormat.parse(dateMax));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Timestamp timestampMax = new Timestamp(calendar.getTimeInMillis());

        return dashboardService.profitInPeriod(timestampMin, timestampMax);
    }

    @GetMapping("/profitThroughYears")
    public List<Map<String, Object>> profitThroughYears() {
        return dashboardService.profitThroughYears();
    }

    @GetMapping("/countReservationsInPeriod")
    public Double countReservationsInPeriod(
        @RequestParam("dateMin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String dateMin,
        @RequestParam("dateMax") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String dateMax
    ) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Calendar calendar = Calendar.getInstance();

        // Convertir et ajuster dateMin
        calendar.setTime(dateFormat.parse(dateMin));
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Timestamp timestampMin = new Timestamp(calendar.getTimeInMillis());

        // Convertir et ajuster dateMax
        calendar.setTime(dateFormat.parse(dateMax));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Timestamp timestampMax = new Timestamp(calendar.getTimeInMillis());

        return dashboardService.countReservationsInPeriod(timestampMin, timestampMax);
    }

    @GetMapping("/reservationsThroughYears")
    public List<Map<String, Object>> reservationsThroughYears() {
        return dashboardService.reservationsThroughYears();
    }
}

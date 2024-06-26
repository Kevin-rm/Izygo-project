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
        Timestamp timestampMin = new Timestamp(dateFormat.parse(dateMin).getTime());
        Timestamp timestampMax = new Timestamp(dateFormat.parse(dateMax).getTime());
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
        Timestamp timestampMin = new Timestamp(dateFormat.parse(dateMin).getTime());
        Timestamp timestampMax = new Timestamp(dateFormat.parse(dateMax).getTime());
        return dashboardService.countReservationsInPeriod(timestampMin, timestampMax);
    }

    @GetMapping("/reservationsThroughYears")
    public List<Map<String, Object>> reservationsThroughYears() {
        return dashboardService.reservationsThroughYears();
    }
}

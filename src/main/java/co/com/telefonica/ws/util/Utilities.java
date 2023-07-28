package co.com.telefonica.ws.util;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Utilities {
    public static String getTimestampValue() {
        PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
        var zoneIdCo = ZoneId.of("America/Bogota");
        var now = ZonedDateTime.now(zoneIdCo);
        var dtf = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        return policy.sanitize(now.truncatedTo(ChronoUnit.MILLIS).format(dtf));
    }

    public static String blindStr(String value) {
        PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
        return policy.sanitize(value)
                .replace("&#43;", "+")
                .replace("&#39;", "'")
                .replace("&#61;", "=")
                .replace("&amp;", " & ");
    }
    private Utilities() {}
}

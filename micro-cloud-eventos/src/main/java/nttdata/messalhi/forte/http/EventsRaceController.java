package nttdata.messalhi.forte.http;

import nttdata.messalhi.forte.services.EventsRaceService;
import nttdata.messalhi.forte.utils.DatabaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("event")
public class EventsRaceController {
    Logger logger = LoggerFactory.getLogger(EventsRaceService.class);
    private final EventsRaceService eventsRaceService;

    @Autowired
    public EventsRaceController(EventsRaceService eventsRaceService) {
        this.eventsRaceService = eventsRaceService;
    }

    @PostMapping("/byId/{id}")
    public ResponseEntity<String> getEventByID(
            @PathVariable String id,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "1") Integer pageNumber) {
        return this.eventsRaceService.getEventsById("id", id, pageSize, pageNumber);
    }




    @PostMapping("/byScheduleId/{id}") //localhost:8083/event/byScheduleId/456?pageSize=5&pageNumber=1&order=ASC
    public ResponseEntity<String> getEventScheduleByID(
            @PathVariable String id,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "ASC") String order) { //ASC y DESC
        logger.info("Petición a byScheduleId con id: " + id);
        return this.eventsRaceService.getEventsByIdScheduleId("schedule_id", id, pageSize, pageNumber, order);
    }

    @PostMapping("/numElementsByScheduleId/{id}")
    public ResponseEntity<String> countEventsByScheduleId(
            @PathVariable String id) {
        return this.eventsRaceService.countEventsByScheduleId(id);
    }
}

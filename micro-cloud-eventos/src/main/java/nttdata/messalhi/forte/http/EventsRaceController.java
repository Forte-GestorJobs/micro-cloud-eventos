package nttdata.messalhi.forte.http;

import nttdata.messalhi.forte.services.EventsRaceService;
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

    @PostMapping("/byUserId/{user_id}")
    public ResponseEntity<String> getEventByID(
            @PathVariable String user_id,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "1") Integer pageNumber) {
        return this.eventsRaceService.getEventsByUserId("user_id", user_id, pageSize, pageNumber);
    }

    @PostMapping("/countByUserId/{user_id}")
    public ResponseEntity<String> countEventsByUserId(
            @PathVariable String user_id) {
        return this.eventsRaceService.countEvents("user_id",user_id);
    }


    @PostMapping("/byScheduleId/{id}")
    public ResponseEntity<String> getEventScheduleByID(
            @PathVariable String id,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "ASC") String order) { //ASC y DESC
        logger.info("Petición a byScheduleId con id: " + id);
        return this.eventsRaceService.getEventsByScheduleId("schedule_id", id, pageSize, pageNumber, order);
    }

    @PostMapping("/countByScheduleId/{id}")
    public ResponseEntity<String> countEventsByScheduleId(
            @PathVariable String id) {
        return this.eventsRaceService.countEvents("schedule_id",id);
    }
}

package nttdata.messalhi.forte.http;

import nttdata.messalhi.forte.services.EventsRaceService;
import nttdata.messalhi.forte.utils.DatabaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("event")
public class EventsRaceController {

    private final EventsRaceService eventsRaceService;

    @Autowired
    public EventsRaceController(EventsRaceService eventsRaceService) {
        this.eventsRaceService = eventsRaceService;
    }

    @PostMapping("/byId/{id}")
    public DatabaseResult getEventByID(
            @PathVariable String id,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "1") Integer pageNumber) {
        return this.eventsRaceService.getEventsBy("id", id, pageSize, pageNumber);
    }

    @PostMapping("/numElementsByScheduleId/{id}")
    public DatabaseResult countEventsByScheduleId(
            @PathVariable String id) {
        return this.eventsRaceService.countEventsByScheduleId(id);
    }

    @PostMapping("/byScheduleId/{id}") ///event/byScheduleId/456?pageSize=5&pageNumber=1
    public DatabaseResult getEventScheduleByID(
            @PathVariable String id,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "1") Integer pageNumber) {
        return this.eventsRaceService.getEventsBy("schedule_id", id, pageSize, pageNumber);
    }
}

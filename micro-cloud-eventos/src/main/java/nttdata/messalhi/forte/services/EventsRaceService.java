
package nttdata.messalhi.forte.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nttdata.messalhi.forte.config.DatabaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

import java.util.List;
import java.util.Map;

@Service
public class EventsRaceService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EventsRaceService(DatabaseConfig databaseConfig) {
        DataSource dataSource = databaseConfig.dataSource();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public ResponseEntity<String> getEventsByUserId(String variable, String id, int pageSize, int pageNumber) {
        String sql = buildSqlQueryById(variable, id, pageSize, pageNumber);
        return getEventsBySql(sql);
    }
    public String buildSqlQueryById(String variable, String id, int pageSize, int pageNumber) {
        int offset = pageSize * (pageNumber - 1);
        return "SELECT * FROM postgres.events WHERE " + variable + " = '" + id + "' LIMIT " + pageSize + " OFFSET " + offset;
    }

    public ResponseEntity<String> getEventsByScheduleId(String variable, String id, int pageSize, int pageNumber, String order) {
        String sql = buildSqlQueryByScheduleId(variable, id, pageSize, pageNumber, order);
        return getEventsBySql(sql);
    }
    public String buildSqlQueryByScheduleId(String variable, String id, int pageSize, int pageNumber, String order) {
        int offset = pageSize * (pageNumber - 1);
        return "SELECT * FROM postgres.events WHERE " + variable + " = '" + id + "' ORDER BY date " + order + " LIMIT " + pageSize + " OFFSET " + offset + ";";
    }


    public ResponseEntity<String> getEventsBySql(String sql) {
        try {
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(resultList);

            return ResponseEntity.ok().body(json);
        } catch(JsonProcessingException e) {
            return ResponseEntity.internalServerError().body("Error al procesar el JSON: " + e.getMessage());
        } catch(Exception e) {
            return ResponseEntity.internalServerError().body("Algo salió mal: " + e.getMessage());
        }
    }


    public ResponseEntity<String> countEvents(String variable, String id) {
        try {
            String sql = "SELECT COUNT(*) FROM postgres.events WHERE "+ variable +" = ?";
            int count = jdbcTemplate.queryForObject(sql, Integer.class, id);
            return ResponseEntity.ok().body(String.valueOf(count));
        } catch(Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
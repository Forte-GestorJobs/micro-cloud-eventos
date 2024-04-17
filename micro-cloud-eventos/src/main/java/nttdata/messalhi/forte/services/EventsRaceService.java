
package nttdata.messalhi.forte.services;

import nttdata.messalhi.forte.config.DatabaseConfig;
import nttdata.messalhi.forte.utils.DatabaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

@Service
public class EventsRaceService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EventsRaceService(DatabaseConfig databaseConfig) {
        DataSource dataSource = databaseConfig.dataSource();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public DatabaseResult getEventsBy(String variable, String id, int pageSize, int pageNumber) {
        try {
            int offset = pageSize * (pageNumber - 1);
            String sql = "SELECT * FROM events WHERE " + variable + " = ? LIMIT ? OFFSET ?";

            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, id, pageSize, offset);
            StringBuilder resultString = new StringBuilder("{");

            for (Map<String, Object> row : resultList) {
                resultString.append("{");
                for (Map.Entry<String, Object> entry : row.entrySet()) {
                    resultString.append(entry.getKey()).append(": ").append(entry.getValue()).append(", ");
                }
                // Eliminar la coma y el espacio después del último elemento
                resultString.delete(resultString.length() - 2, resultString.length());
                resultString.append("}, ");
            }
            // Eliminar la coma y el espacio después del último objeto
            resultString.delete(resultString.length() - 2, resultString.length());
            resultString.append("}");

            return new DatabaseResult(true, resultString.toString());
        } catch(Exception e) {
            return new DatabaseResult(false, e.getMessage());
        }
    }

    public DatabaseResult countEventsByScheduleId(String id) {
        try {
            String sql = "SELECT COUNT(*) FROM events WHERE schedule_id = ?";
            int count = jdbcTemplate.queryForObject(sql, Integer.class, id);
            return new DatabaseResult(true, "Count: " + count);
        } catch(Exception e) {
            return new DatabaseResult(false, e.getMessage());
        }
    }

}
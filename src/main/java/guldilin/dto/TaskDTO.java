package guldilin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private String title;
    private String description = "";
    private List<String> tags;
    private String deadline;

    @Override
    public String toString() {
        return "\tTitle: " + this.title +
                "\n\tDescription: " + this.description +
                "\n\tDeadline: " + this.deadline +
                "\n\tTags: " + String.join(", ", this.tags);
    }
}

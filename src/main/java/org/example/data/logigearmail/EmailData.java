package org.example.data.logigearmail;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.io.FilenameUtils;

import java.util.Objects;

@Data
@Builder
public class EmailData {

    String to;
    String cc;
    String subject;
    String content;
    String attachment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmailData that = (EmailData) o;

        if (!Objects.equals(to, that.to)) return false;
        if (!Objects.equals(cc, that.cc)) return false;
        if (!Objects.equals(subject, that.subject)) return false;
        if (!Objects.equals(content, that.content)) return false;
        return Objects.equals(FilenameUtils.getName(attachment), FilenameUtils.getName(that.attachment));
    }
}

package org.example.data.logigearmail;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FilenameUtils;

import java.util.Objects;

@Getter
@Setter
@Builder
public class EmailData {

    String to;
    String cc;
    String subject;
    @Builder.Default
    String content = "";
    String attachment;
    String insertionImage;

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

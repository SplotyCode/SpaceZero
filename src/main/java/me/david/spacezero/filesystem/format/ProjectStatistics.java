package me.david.spacezero.filesystem.format;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ProjectStatistics {

    @Getter private int opens;
    @Getter private long onTime;

}

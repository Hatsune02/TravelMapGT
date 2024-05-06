package com.navi.backend.objs;
import lombok.*;

import java.util.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Traffic {
    private String origin, destiny;
    private int startTime, endTime, trafficProbability;


}

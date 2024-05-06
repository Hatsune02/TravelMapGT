package com.navi.backend.objs;
import com.navi.backend.structures.graph.TypeGraph;
import lombok.*;

import java.util.*;

@Getter @Setter @Builder @ToString
@NoArgsConstructor @AllArgsConstructor
public class Route {
    private String origin, destiny;
    private int vehicleTime, walkTime, gas, personEffort, distance;
    private ArrayList<Traffic> trafficSchedules;

    public double getGasDistance(){
        double d = (double) distance /gas;
        return Math.round(d * 100.0) / 100.0;
    }
    public double getEffortDistance(){
        double d = (double) distance /personEffort;
        return Math.round(d * 100.0) / 100.0;
    }
    public double getSpeedVehicle(int hour){
        boolean find = false;
        int time = hour + vehicleTime;
        Traffic traffic = new Traffic();
        for(var t : trafficSchedules){
            if(t.getStartTime() >= time && t.getEndTime() <= time){
                traffic = t;
                find = true;
                break;
            }
        }
        double d;
        if(find) d = (double) distance / (time * (1 + traffic.getTrafficProbability()));
        else d = (double) distance /time;

        return Math.round(d * 100.0) / 100.0;
    }

    public double getSpeedWalk(int hour){
        int time = hour + walkTime;
        double d = (double) distance / time;
        return Math.round(d * 100.0) / 100.0;
    }

}

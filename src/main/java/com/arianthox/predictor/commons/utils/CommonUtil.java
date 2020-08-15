package com.arianthox.predictor.commons.utils;

import akka.japi.function.Function;
import akka.stream.Supervision;
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;

import java.util.Locale;
import java.util.logging.Level;

import static akka.stream.Supervision.*;

@Log
@UtilityClass
public class CommonUtil {

    public String getTopicName(Class packetClass){
        return formatTopicName(packetClass.getSimpleName());
    }
    public String formatTopicName(String name){
        return String.join("-",String.join("-", name.split("(?=\\p{Upper})")).toLowerCase(Locale.getDefault()).replace("-","_").split("(?=\\p{Upper})"));
    }

    public final static Function<Throwable, Supervision.Directive> decider =
            exc -> {
                if (exc instanceof Exception) {
                    log.log(Level.SEVERE, exc.getMessage());
                    return (Supervision.Directive) resume();
                } else {
                    log.log(Level.SEVERE, "Error:" + exc.getMessage());
                    return (Supervision.Directive) stop();
                }
            };
}

package net.swierczynski.alexaairly;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AirQualityStreamHandler extends SkillStreamHandler {

    static final Logger logger = LoggerFactory.getLogger(AirQualityStreamHandler.class);

    public AirQualityStreamHandler() {
        super(getSkill());
        logger.info("Inside AirQualityStreamHandler");
    }

    private static Skill getSkill() {
        logger.info("getSkill()");
        AirlyClient airlyClient = new AirlyClient();
        AirQualityIntentHandler airQualityIntentHandler = new AirQualityIntentHandler(airlyClient);

        return Skills.standard()
                .addRequestHandlers(
                        airQualityIntentHandler,
                        new LaunchRequestHandler(airQualityIntentHandler)
                )
                .build();
    }
}

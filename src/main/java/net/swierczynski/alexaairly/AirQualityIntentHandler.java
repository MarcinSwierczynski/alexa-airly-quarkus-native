package net.swierczynski.alexaairly;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

import java.util.Optional;

import static java.lang.String.format;

class AirQualityIntentHandler implements RequestHandler {

    private static final String INTENT_NAME = "AirQualityIntent";
    private static final String WELCOME_MESSAGE = "Ok, here's Airly";

    private final AirlyClient airlyClient;

    AirQualityIntentHandler(AirlyClient airlyClient) {
        this.airlyClient = airlyClient;
    }

    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(Predicates.intentName(INTENT_NAME));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
//        new ProgressiveResponseSender(handlerInput).sendProgressiveResponse(WELCOME_MESSAGE);

        String speechText = getSpeechText();
        return buildResponse(handlerInput, speechText);
    }

    private String getSpeechText() {
        AirQuality airQuality = airlyClient.fetchAirQuality("50.273966", "19.004781");
        return formatResponse(airQuality);
    }

    private static String formatResponse(AirQuality airQuality) {
        String description = airQuality.getDescription();
        int caqi = (int) Math.round(airQuality.getCaqi());
        int pm25percent = (int) Math.round(airQuality.getPm25Percent());
        return format(
                "%s The Common Air Quality Index is %s. The PM2.5 is %s%%",
                description, caqi, pm25percent
        );
    }

    private static Optional<Response> buildResponse(HandlerInput handlerInput, String speechText) {
        return handlerInput.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("AlexaAirly", speechText)
                .build();
    }
}

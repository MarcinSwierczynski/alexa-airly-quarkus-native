package net.swierczynski.alexaairly;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

import java.util.Optional;

class LaunchRequestHandler implements RequestHandler {

  private final AirQualityIntentHandler airQualityIntentHandler;

  public LaunchRequestHandler(AirQualityIntentHandler airQualityIntentHandler) {
    this.airQualityIntentHandler = airQualityIntentHandler;
  }

  @Override
  public boolean canHandle(HandlerInput handlerInput) {
    return handlerInput.matches(Predicates.requestType(LaunchRequest.class));
  }

  @Override
  public Optional<Response> handle(HandlerInput handlerInput) {
    return airQualityIntentHandler.handle(handlerInput);
  }
}

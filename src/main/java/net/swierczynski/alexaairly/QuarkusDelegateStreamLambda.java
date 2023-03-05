package net.swierczynski.alexaairly;

import com.amazon.ask.SkillStreamHandler;
import com.amazonaws.lambda.thirdparty.com.fasterxml.jackson.core.JsonProcessingException;
import com.amazonaws.lambda.thirdparty.com.fasterxml.jackson.databind.ObjectMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import javax.inject.Named;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Named("alexa")
public class QuarkusDelegateStreamLambda implements RequestHandler<Map<?, ?>, Map<?, ?>> {
    private static final ObjectMapper JSON_OBJECT_MAPPER = new ObjectMapper();

    private final SkillStreamHandler delegate = new AirQualityStreamHandler();

    private InputStream streamFromRequest(Map<?, ?> request) {
        try {
            return new ByteArrayInputStream(
                    JSON_OBJECT_MAPPER.writerFor(Map.class)
                            .writeValueAsBytes(request)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<?, ?> handleRequest(Map<?, ?> request, Context context) {
        final ByteArrayOutputStream os =
                new ByteArrayOutputStream();
        try {
            delegate.handleRequest(streamFromRequest(request), os, context);
            return JSON_OBJECT_MAPPER.readerFor(Map.class)
                    .readValue(os.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

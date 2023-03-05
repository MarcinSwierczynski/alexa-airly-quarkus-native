package net.swierczynski.alexaairly;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.services.directive.DirectiveServiceClient;
import com.amazon.ask.model.services.directive.Header;
import com.amazon.ask.model.services.directive.SendDirectiveRequest;
import com.amazon.ask.model.services.directive.SpeakDirective;

class ProgressiveResponseSender {

    private final String requestId;
    private final DirectiveServiceClient directiveService;

    ProgressiveResponseSender(HandlerInput handlerInput) {
        this.requestId = handlerInput.getRequest().getRequestId();
        this.directiveService = handlerInput.getServiceClientFactory().getDirectiveService();
    }

    void sendProgressiveResponse(String speech) {
        SpeakDirective speakDirective = SpeakDirective.builder().withSpeech(speech).build();
        Header header = Header.builder().withRequestId(requestId).build();

        SendDirectiveRequest directiveRequest = SendDirectiveRequest.builder()
                .withDirective(speakDirective)
                .withHeader(header)
                .build();

        directiveService.enqueue(directiveRequest);
    }

}

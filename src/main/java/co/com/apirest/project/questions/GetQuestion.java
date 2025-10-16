package co.com.apirest.project.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.annotations.Subject;

import static co.com.apirest.project.utils.Constants.STATUS;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.equalTo;

public class GetQuestion implements Question<Boolean> {

    private final int statusOK;
    private String expectedReference;
    private String expectedStatus;
    private String paymentMethodType;

    public GetQuestion(int statusOK) {
        this.statusOK = statusOK;
    }

    @Override
    @Subject("{0} valida la respuesta de la API")
    public Boolean answeredBy(Actor actor) {

        actor.should(seeThatResponse(STATUS, response -> response.statusCode(statusOK)));

        if (expectedReference != null) {
            actor.should(seeThatResponse(STATUS, response ->
                    response.body("data.reference", equalTo(actor.recall(expectedReference)))
            ));
        }

        if (expectedStatus != null) {
            actor.should(seeThatResponse(STATUS, response ->
                    response.body("data.status", equalTo(expectedStatus))
            ));
        }

        if (paymentMethodType != null) {
            actor.should(seeThatResponse(STATUS, response ->
                    response.body("data.payment_method_type", equalTo(paymentMethodType))
            ));
        }

        return true;
    }

    public static GetQuestion successful(int statusOK) {
        return new GetQuestion(statusOK);
    }

    public GetQuestion withReference(String keyInActorMemory) {
        this.expectedReference = keyInActorMemory;
        return this;
    }

    public GetQuestion withStatus(String expectedStatus) {
        this.expectedStatus = expectedStatus;
        return this;
    }

    public GetQuestion withPaymentMethod(String paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
        return this;
    }

}

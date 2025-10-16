package co.com.apirest.project.questions;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class GetAcceptanceToken implements Question<Boolean> {

    @Override
    public Boolean answeredBy(Actor actor) {
        String acceptanceToken = SerenityRest.lastResponse()
                .jsonPath().getString("data.presigned_acceptance.acceptance_token");

        String personalAuth = SerenityRest.lastResponse()
                .jsonPath().getString("data.presigned_personal_data_auth.acceptance_token");

        actor.remember("acceptance_token", acceptanceToken);
        actor.remember("accept_personal_auth", personalAuth);

        return acceptanceToken != null && personalAuth != null;
    }

    public static GetAcceptanceToken captured() {
        return new GetAcceptanceToken();
    }
}

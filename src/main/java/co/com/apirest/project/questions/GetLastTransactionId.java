package co.com.apirest.project.questions;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class GetLastTransactionId implements Question<Boolean> {

    @Override
    public Boolean answeredBy(Actor actor) {
        String transactionId = SerenityRest.lastResponse()
                .jsonPath().getString("data.id");

        if (transactionId != null) {
            actor.remember("last_transaction_id", transactionId);
            return true;
        } else {
            throw new IllegalStateException("❌ No se encontró ID de transacción en la respuesta.");
        }
    }

    public static GetLastTransactionId captured() {
        return new GetLastTransactionId();
    }
}

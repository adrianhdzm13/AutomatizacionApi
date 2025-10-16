package co.com.apirest.project.stepdefinition;

import co.com.apirest.project.questions.GetLastTransactionId;
import co.com.apirest.project.questions.GetQuestion;
import co.com.apirest.project.taks.post.CrearTransaccionNequi;
import io.cucumber.java.ast.Cuando;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.apache.http.HttpStatus.*;

public class TarjetaTransactionStepdefinition {

    @Cuando("el usuario crea una transacción con tarjeta")
    public void elUsuarioCreaUnaTransaccionConTarjeta() {
        theActorInTheSpotlight().attemptsTo(CrearTransaccionNequi.conValoresDinamicos("crear_transaction_tarjeta.json", "transactionTarjeta"));
        theActorInTheSpotlight().should(
                seeThat(GetQuestion.successful(SC_CREATED)
                        .withPaymentMethod("CARD")
                        .withStatus("PENDING")
                )
        );
        theActorInTheSpotlight().should(seeThat(GetLastTransactionId.captured()));
    }

    @Cuando("el usuario crea una transacción declinada con tarjeta")
    public void elUsuarioCreaUnaTransaccionDeclinadaConTarjeta() {
        theActorInTheSpotlight().attemptsTo(CrearTransaccionNequi.conValoresDinamicos("crear_transaction_tarjeta.json", "transactionTarjetaDeclinada"));
        theActorInTheSpotlight().should(
                seeThat(GetQuestion.successful(SC_CREATED)
                        .withPaymentMethod("CARD")
                        .withStatus("PENDING")
                )
        );
        theActorInTheSpotlight().should(seeThat(GetLastTransactionId.captured()));
    }


}

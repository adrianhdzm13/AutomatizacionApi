package co.com.apirest.project.stepdefinition;

import co.com.apirest.project.questions.GetAcceptanceToken;
import co.com.apirest.project.questions.GetLastTransactionId;
import co.com.apirest.project.questions.GetQuestion;
import co.com.apirest.project.questions.PostQuestion;
import co.com.apirest.project.taks.get.Call;
import co.com.apirest.project.taks.post.CrearTransaccionNequi;
import io.cucumber.java.ast.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.actors.OnStage;

import static co.com.apirest.project.utils.Constants.*;
import static co.com.apirest.project.utils.Uri.ACCESS_TOKEN;
import static co.com.apirest.project.utils.Uri.TRANSACTIONS;
import static io.restassured.parsing.Parser.JSON;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.apache.hc.core5.http.HttpStatus.*;
import static org.apache.http.HttpStatus.SC_OK;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.equalTo;
import static org.apache.http.HttpStatus.SC_UNPROCESSABLE_ENTITY;

public class NequiTransactionStepdefinition {

    @Dado("que el usuario obtiene los tokens de aceptación de Wompi")
    public void queElUsuarioObtieneLosTokensDeAceptacionDeWompi() {
        OnStage.theActorCalled(ACTOR).attemptsTo(
                Call.service().apiget(
                        BASE_URL.replace(TYPE_ENVIRONMENT, ENV_QA),
                        ACCESS_TOKEN.getUri(),
                        String.valueOf(JSON)));

        theActorInTheSpotlight().should(seeThat(GetQuestion.successful(SC_OK)));
        theActorInTheSpotlight().should(seeThat(GetAcceptanceToken.captured()));
    }


    @Cuando("el usuario crea una transacción con Nequi")
    public void elUsuarioCreaUnaTransaccionConNequi() {
        theActorInTheSpotlight().attemptsTo(CrearTransaccionNequi.conValoresDinamicos("crear_transaction_nequi.json", "transactionNequi"));
        theActorInTheSpotlight().should(seeThat(PostQuestion.registerSuccess(SC_CREATED)));
        theActorInTheSpotlight().should(seeThat(GetLastTransactionId.captured()));
    }


    @Entonces("la transacción debe ser aprobada")
    public void laTransaccionDebeSerAprobada() throws InterruptedException {
        Thread.sleep(3000);
        OnStage.theActorCalled(ACTOR).attemptsTo(
                Call.service().apiget(
                        BASE_URL.replace(TYPE_ENVIRONMENT, ENV_QA),
                        TRANSACTIONS.getUri() + "/" + theActorInTheSpotlight().recall("last_transaction_id"),
                        String.valueOf(JSON)));

        theActorInTheSpotlight().should(seeThat(GetQuestion.successful(SC_OK)
                .withStatus("APPROVED")

        ));
    }

    @Cuando("el usuario crea una transacción declinada con Nequi")
    public void elUsuarioCreaUnaTransaccionDeclinadaConNequi() {
        theActorInTheSpotlight().attemptsTo(
                CrearTransaccionNequi.conValoresDinamicos("crear_transaction_nequi.json", "transactionNequiDeclinada")
        );
        theActorInTheSpotlight().should(seeThat(PostQuestion.registerSuccess(SC_CREATED)));
        theActorInTheSpotlight().should(seeThat(GetLastTransactionId.captured()));
    }

    @Entonces("la transacción debe ser declinada")
    public void laTransaccionDebeSerDeclinada() throws InterruptedException {
        Thread.sleep(3000);
        OnStage.theActorCalled(ACTOR).attemptsTo(
                Call.service().apiget(
                        BASE_URL.replace(TYPE_ENVIRONMENT, ENV_QA),
                        TRANSACTIONS.getUri() + "/" + theActorInTheSpotlight().recall("last_transaction_id"),
                        String.valueOf(JSON)));

        theActorInTheSpotlight().should(
                seeThat(GetQuestion.successful(SC_OK)
                        .withReference("last_reference")
                        .withStatus("DECLINED")
                )
        );
    }

    @Cuando("consulta por id inexistente")
    public void consultaPorIdInexistente() {
        OnStage.theActorCalled(ACTOR).attemptsTo(
                Call.service().apiget(
                        BASE_URL.replace(TYPE_ENVIRONMENT, ENV_QA),
                        TRANSACTIONS.getUri() + "/" + theActorInTheSpotlight().recall("last_reference"),
                        String.valueOf(JSON)));
    }

    @Entonces("la transacción debe devolver error")
    public void laTransaccionDebeDevolverError() {
        theActorInTheSpotlight().should(seeThat(GetQuestion.successful(SC_NOT_FOUND)));
    }

    @Cuando("el usuario crea una transacción con Nequi con datos invalidos")
    public void elUsuarioCreaUnaTransaccionConNequiConDatosInvalidos() {
        theActorInTheSpotlight().attemptsTo(CrearTransaccionNequi.conValoresDinamicos("crear_transaction_nequi.json", "transactionNequiDatosInvalidos"));
    }

    @Entonces("la transacción debe fallar con el mensaje {string}")
    public void laTransaccionDebeFallarConElMensaje(String mensajeEsperado) {
        theActorInTheSpotlight().should(
                seeThatResponse("Validar mensaje de error de validación",
                        response -> response
                                .statusCode(SC_UNPROCESSABLE_ENTITY)
                                .body("error.type", equalTo("INPUT_VALIDATION_ERROR"))
                                .body("error.messages.valid_amount_in_cents[0]", equalTo(mensajeEsperado))
                )
        );
    }


}

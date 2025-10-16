package co.com.apirest.project.taks.post;

import co.com.apirest.project.interactions.PostRequest;
import co.com.apirest.project.utils.Constants;
import co.com.apirest.project.utils.JsonFileReader;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

import static co.com.apirest.project.utils.Constants.*;

public class CrearTransaccionNequi implements Task {

    private final String jsonFile;
    private final String jsonNode;

    public CrearTransaccionNequi(String jsonFile, String jsonNode) {
        this.jsonFile = jsonFile;
        this.jsonNode = jsonNode;
    }


    public static CrearTransaccionNequi conValoresDinamicos(String jsonFile, String jsonNode) {
        return Tasks.instrumented(CrearTransaccionNequi.class, jsonFile, jsonNode);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        try {
            String jsonBody = JsonFileReader.leerNodoJson(jsonFile, jsonNode);

            String currency = "COP";
            String reference = "order-" + System.currentTimeMillis();

            String acceptanceToken = actor.recall("acceptance_token");
            String personalAuth = actor.recall("accept_personal_auth");

            // Parsear a JsonNode para obtener amount_in_cents
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(jsonBody);
            int amount = node.get("amount_in_cents").asInt();

            // Calcular signature
            String rawSignature = reference + amount + currency + Constants.INTEGRITY_KEY;
            String signature = org.apache.commons.codec.digest.DigestUtils.sha256Hex(rawSignature);

            // Reemplazar valores dinámicos en el JSON
            jsonBody = jsonBody
                    .replace("{{currency}}", currency)
                    .replace("{{reference}}", reference)
                    .replace("{{acceptance_token}}", acceptanceToken)
                    .replace("{{accept_personal_auth}}", personalAuth)
                    .replace("{{signature}}", signature);

            actor.remember("last_reference", reference);

            actor.attemptsTo(
                    PostRequest.params(
                            BASE_URL.replace(TYPE_ENVIRONMENT, ENV_QA),
                            jsonBody,
                            "/transactions",
                            "application/json"
                    )
            );

        } catch (Exception e) {
            throw new RuntimeException("Error creando transacción Nequi: " + e.getMessage(), e);
        }
    }
}

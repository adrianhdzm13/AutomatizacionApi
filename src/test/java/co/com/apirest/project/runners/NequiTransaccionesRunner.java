package co.com.apirest.project.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/nequi_transaction.feature",
        glue = "co.com.apirest.project.stepdefinition",
        snippets = CucumberOptions.SnippetType.CAMELCASE
)

public class NequiTransaccionesRunner {

}

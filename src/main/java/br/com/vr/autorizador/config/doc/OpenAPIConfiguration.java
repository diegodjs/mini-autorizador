package br.com.vr.autorizador.config.doc;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
  info =@Info(
    title = "Mini Autorizador API",
    version = "${app.version}",
	contact = @Contact(
			name = "Contatos de Suporte API",
			email = "diegojs@gmail.com"
	)
  )
)
public class OpenAPIConfiguration {

}

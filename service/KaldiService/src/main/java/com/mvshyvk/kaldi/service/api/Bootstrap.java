package com.mvshyvk.kaldi.service.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
    info = @Info(
        title = "Kaldi speach recognition service", 
        version = "0.9.0", 
        description = "Simple REST interface for posting tasks for non realtime speach recognition",
        termsOfService = "",
        contact = @Contact(email = ""),
        license = @License(
            name = "",
            url = "http://unlicense.org"
        )
    )
)
public class Bootstrap {
}

package com.itau.pix.integration.config

import com.itau.pix.resources.v1.API_V1_APPLICATION_JSON_ITAU
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.http.ContentType
import org.springframework.test.context.TestContext
import org.springframework.test.context.TestExecutionListener

class RestAssuredExecutionListener : TestExecutionListener {

    override fun beforeTestMethod(testContext: TestContext) {
        val port = testContext.applicationContext
            .environment.getProperty(SERVER_PORT, Int::class.java, 8080)
        RestAssured.requestSpecification = RequestSpecBuilder()
            .setAccept(API_V1_APPLICATION_JSON_ITAU)
            .setContentType(ContentType.JSON)
            .setPort(port)
            .build()
    }

    companion object {
        private const val SERVER_PORT = "local.server.port"

    }
}

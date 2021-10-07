package org.alga.handlers

import org.http4k.serverless.ApiGatewayRestLambdaFunction
import org.http4k.serverless.AppLoader

@Suppress("unused")
// entry point for the Publications lambda (from AWS)
class PublicationsLambdaHandler : ApiGatewayRestLambdaFunction(AppLoader {
    PublicationsApi()
})
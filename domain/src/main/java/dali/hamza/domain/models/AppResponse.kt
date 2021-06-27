package dali.hamza.domain.models

abstract class IResponse


sealed class MyResponse<T>(data: T?, error: Any?) : IResponse()

data class SuccessResponse<T>(val data: T) : MyResponse<T>(
    data = data,
    error = null
)

class ErrorResponse(error: Any) : MyResponse<Any>(
    data = null,
    error = error
)
package dali.hamza.domain.models

abstract class IResponse


sealed class MyResponse<T>(data: T?, error: Any?) : IResponse()

class SuccessResponse<T>(data: T) : MyResponse<T>(
    data = data,
    error = null
)

class ErrorResponse<T>(error: Any) : MyResponse<T>(
    data = null,
    error = error
)
package dali.hamza.core.common

import android.util.Log
import dali.hamza.domain.models.ErrorResponse
import dali.hamza.domain.models.MyResponse
import dali.hamza.domain.models.SuccessResponse
import okio.IOException
import retrofit2.Response
inline fun <T> Response<T>.onSuccess(
    action: (T) -> Unit
): Response<T> {
    if (isSuccessful) {
        body()?.run(action)
    }
    return this
}

inline fun <T> Response<T>.onFailure(
    action: (Any) -> Unit
) {
    if (!isSuccessful) {
        Log.e("code request ${this.code()}", this.errorBody()?.string() ?: "error unknown")
        errorBody()?.run {
            action(this.string())
        }
    }
}

fun <T, R : Any> Response<T>.data(
    mapTo: (T) -> R
): MyResponse<R> {
    try {
        onSuccess {
            return SuccessResponse(mapTo(it))
        }
        onFailure {
            return ErrorResponse(it)
        }
        return ErrorResponse(
            error = Exception("GENERAL_NETWORK_ERROR")
        )
    } catch (e1: IOException) {
        return ErrorResponse(
            error = IOException("GENERAL_NETWORK_ERROR")
        )
    } catch (e: Exception) {
        return ErrorResponse(
            error = Exception(e.fillInStackTrace().message)
        )
    }

}

fun <T, R : Any> Response<T>.simpleData(
    mapTo: (T) -> R
): R {
    try {
        onSuccess {
            return mapTo(it)
        }
        onFailure {
            throw Exception(it as String)
        }
        throw  Exception("GENERAL_NETWORK_ERROR")
    } catch (e1: IOException) {
        throw IOException("GENERAL_NETWORK_ERROR")
    } catch (e: Exception) {
        throw Exception(e.fillInStackTrace().message)
    }

}
package dali.hamza.orderfoodapp.model

import android.os.CountDownTimer
import dali.hamza.core.common.DateExpiration
import dali.hamza.domain.Order

data class UIOrder(
    val order: Order,
    var isExpired: Boolean = false,
    var dateExpirationIn: DateExpiration
)

data class OrderTimeUI(
    val dateExpiration: DateExpiration,
    val timer: CountDownTimer? = null
)
data class SaverOrderUI(
    val dateExpiration: DateExpiration,
    val order: Order
)


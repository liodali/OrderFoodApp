package dali.hamza.orderfoodapp.model

import dali.hamza.core.common.DateExpiration
import dali.hamza.domain.Order

data class UIOrder(
    val order: Order,
    var isExpired: Boolean = false,
    var dateExpirationIn: DateExpiration
)
package dali.hamza.core

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import dali.hamza.core.datasource.network.AppClientApi
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class HttpCallsTest {
    private lateinit var api: AppClientApi


    @Before
    fun init() {
        val testContext: Context = getInstrumentation().context

        api = Retrofit.Builder()
            .baseUrl("https://foodingredientapi.herokuapp.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(AppClientApi::class.java)
    }

    @Test
    fun testOrder() = runBlocking {
        val response = api.getOrders()

        assertEquals(response.isSuccessful, true)
        assertEquals(response.body()!!.data.isEmpty(), false)
    }
    @Test
    fun testIngredients() = runBlocking {
        val response = api.getIngredients(1)

        assertEquals(response.isSuccessful, true)
        assertEquals(response.body()!!.data.size, 50)
    }

}
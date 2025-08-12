package org.onion.read

import com.dokar.quickjs.binding.define
import com.dokar.quickjs.binding.function
import com.dokar.quickjs.quickJs
import com.onion.model.BookSource
import com.onion.network.constant.UA_NAME
import com.onion.network.di.getHttpClient
import com.onion.network.http.getPlatformHttpEngine
import com.skydoves.sandwich.ktor.getApiResponse
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import io.ktor.client.request.header
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ComposeAppCommonTest {

    @Test
    fun example() {
        assertEquals(3, 1 + 2)
    }


    private val httpClient = getHttpClient()

    @Test
    fun requestBookSource() = runTest{
        val url = "https://www.yckceo.sbs/yuedu/shuyuans/json/id/891.json"
        val requestUrl: String
        val noUaRequest = url.endsWith("#requestWithoutUA")
        requestUrl = if (noUaRequest) {
            url.substringBeforeLast("#requestWithoutUA")
        } else {
            url
        }
        // 发起请求并获取响应
        println("url-> $requestUrl")
        httpClient.getApiResponse<List<BookSource>>(requestUrl) {
            if (noUaRequest) {
                header(UA_NAME, "null")
            }
        }.onSuccess {
            data.forEach {
                println("source-> ${it.bookSourceName}")
            }
        }
    }

    @Test
    fun testQuickJs() = runTest {
        quickJs {
            define("console") {
                function("log") { args ->
                    println(args.joinToString(" "))
                }
            }


            function<String, String>("greet") { "Hello, $it!" }

            evaluate<Any?>(
                """
        console.log("Hello from JavaScript!")
        """.trimIndent()
            )
        }
    }
}
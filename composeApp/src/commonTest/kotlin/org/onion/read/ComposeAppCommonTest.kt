package org.onion.read

import com.dokar.quickjs.binding.define
import com.dokar.quickjs.binding.function
import com.dokar.quickjs.quickJs
import com.onion.model.BookKind
import com.onion.model.BookSource
import com.onion.network.constant.UA_NAME
import com.onion.network.di.getHttpClient
import com.skydoves.sandwich.getOrElse
import com.skydoves.sandwich.ktor.getApiResponse
import com.skydoves.sandwich.onSuccess
import io.ktor.client.request.header
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class ComposeAppCommonTest {

    @Test
    fun example() {
        assertEquals(3, 1 + 2)
    }


    private val httpClient = getHttpClient()
    private val httpJson = Json {
        // 忽略 JSON 中存在但数据类中没有的字段，避免崩溃
        ignoreUnknownKeys = true
        // 允许不规范的 JSON 格式（例如，属性名没有引号）
        isLenient = true
        // 如果一个字段在 JSON 中不存在，但类中有默认值，则使用默认值
        coerceInputValues = true
        // 将 null 值编码进去，而不是省略字段
        encodeDefaults = true
    }

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
            data.get(1).run {
                val ruler = exploreUrl ?: ""
                var jsStr = ruler
                if (ruler.startsWith("<js>", true) || ruler.startsWith("@js:", true)){
                    jsStr = if (ruler.startsWith("@")) {
                        ruler.substring(4)
                    } else {
                        ruler.substring(4, ruler.lastIndexOf("<"))
                    }
                }
                println("rule-> $jsStr")
                launch {
                    quickJs {
                        define("source"){
                            function("getVariable"){
                                ""
                            }
                            function("key"){
                                bookSourceUrl
                            }
                            function("getLoginInfoMap"){
                                mapOf<String, String>()
                            }
                        }
                        define("cookie"){
                            function("getCookie"){
                                ""
                            }
                        }
                        define("java"){
                            function("getCookie"){
                                ""
                            }
                            function("androidId"){
                                ""
                            }
                            function("ajax"){
                                println("ajax -> $it")
                                val result = runBlocking {
                                    httpClient.getApiResponse<String>(it.first().toString())
                                }
                                result.getOrElse { "" }
                            }
                            function("longToast"){
                                println("js toast -> $it")
                                ""
                            }
                        }
                        function("getArguments"){ args ->
                            ""
                        }
                        function("ck"){ args ->
                            ""
                        }
                        function("gets_key"){ args ->
                            ""
                        }
                        val result = evaluate<Any?>(jsStr.trim())
                        httpJson.decodeFromString<List<BookKind>>(result.toString()).forEach { bookKind ->
                            println("bookKind-> $bookKind")
                        }
                    }
                }
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
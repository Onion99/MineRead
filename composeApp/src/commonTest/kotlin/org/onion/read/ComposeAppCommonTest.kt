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
import kotlinx.serialization.json.JsonObject
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
            data.get(0).run {
                val ruler = exploreUrl ?: ""
                var jsStr = ruler
                if (ruler.startsWith("<js>", true) || ruler.startsWith("@js:", true)){
                    jsStr = if (ruler.startsWith("@")) {
                        ruler.substring(4)
                    } else {
                        ruler.substring(4, ruler.lastIndexOf("<"))
                    }
                }
                println("sourceUrl-> $bookSourceUrl")
                val finalBookSourceUrl = bookSourceUrlRegex.find(bookSourceUrl)?.groupValues?.first() ?: ""
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
                        val kindList = httpJson.decodeFromString<List<BookKind>>(result.toString())
                        kindList.get(3).let { kind ->
                            println("kind-> ${kind}")
                            // 解析还原JS请求地址
                            val bookKindMatch = bookKindRegex.findAll(kind.url?:"")
                            // 输出需要替换占位的内容
                            bookKindMatch
                                .map { it.groupValues[1] } // groupValues[0]是完整匹配(如"{{page}}")，[1]是第一个捕获组的内容(如"page")
                                .toList().let { placeContent ->
                                    println("kind replace content -> $placeContent")
                                }
                            // 替换占位请求参数
                            val requestParams = mapOf(
                                "page" to 1,
                                "source.bookSourceUrl" to "$finalBookSourceUrl/"
                            )
                            val finalKindUrl = bookKindRegex.replace(kind.url ?: ""){ matchResult ->
                                // groupValues[1] 对应 `(.+?)` 捕获的内容，例如 "page",0则是完整匹配 {{page}}
                                val key = matchResult.groupValues[1]
                                val value = requestParams[key]?.toString()
                                value ?: matchResult.value
                            }
                            val requestBookKindUrl = if(isHttpUrlWithKtor(finalKindUrl)) finalKindUrl else
                                finalBookSourceUrl+finalKindUrl
                            println("final kind url -> $requestBookKindUrl")
                            val exploreData = httpClient.getApiResponse<String>(requestBookKindUrl)
                            define("result"){
                                exploreData.toString()
                            }
                            println("rule explore -> ${ruleExplore!!}")
                            val contentList = evaluate<Any?>(ruleExplore!!.bookList!!.substring(1))
                            println("final kind content list -> $contentList")
                        }
                    }
                }
            }
        }
    }

    val bookSourceUrlRegex = Regex("""(https?://[^/\s]+)""")
    // ------------------------------------------------------------------------
    // \{\{: 匹配字面量的 {{。花括号在正则中有特殊含义，所以需要用反斜杠 \ 来转义。
    // ( 和 ): 创建一个捕获组。这是关键，因为我们只想要括号里面的内容。
    // .+?: 这是捕获组的内容。
    // .: 匹配除换行符外的任何字符。
    // +: 匹配前面的元素一次或多次。
    // ?: 使 + 变为非贪婪（non-greedy）模式。这意味着它会匹配尽可能少的字符，直到遇到后面的 }} 为止。如果没有 ?，对于输入 {{a}} and {{b}}，.+ 会贪婪地匹配到 a}} and {{b。
    // \}\}: 匹配字面量的 }}
    // ------------------------------------------------------------------------
    val bookKindRegex = Regex("""\{\{(.+?)\}\}""")


    fun isHttpUrlWithKtor(urlString: String?): Boolean {
        if (urlString.isNullOrBlank()) {
            return false
        }
        return urlString.startsWith("http://", ignoreCase = true) || urlString.startsWith("https://", ignoreCase = true)
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
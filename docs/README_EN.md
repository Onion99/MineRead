

### 搭建合适的网络请求

> Ktor是目前KMP下最好的最好的网络请求框架,没有之一

为什么这么说,因为它在下层做了非常好的功能层抽象,可以扩展各种各样的插件,如
- ContentNegotiation: 用于自动处理 JSON (配合 kotlinx.serialization) 或其他数据格式的序列化/反序列化。 
- Logging: 记录网络请求和响应的详细信息。 
- Auth: 简化 Bearer Token、Basic Auth 等认证逻辑。 
- DefaultRequest: 为所有请求统一添加 Header 或 URL 前缀。 
- HttpRequestRetry: 自动重试失败的请求。

并且可以为不同平台选择合适的网络引擎



### 寻找JS解释器


从所周知,开源阅读里的许多书源功能都依赖Rhino引擎,这是一个由mozilla开发并维护至今的JavaScript引擎,
https://github.com/mozilla/rhino,


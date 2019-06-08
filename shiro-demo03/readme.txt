1.在shiro-demo02的基础上进一步完善，支持分布式后台，session共享

2.传统结构项目中，shiro从cookie中读取sessionId以此来维持会话，在前后端分离的项目中（也可在移动APP项目使用），
      我们选择在ajax的请求头中传递sessionId，因此需要重写shiro获取sessionId的方式。

3.所以我们需要自定义MySessionManager类继承DefaultWebSessionManager类，重写getSessionId方法

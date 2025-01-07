RPC
负责传输数据(Data  to Object),包括request , response, 
同时向外提供一个调用接口

Interfaces
接口具体的实现
先让request类传入ActivityID拿到PO对象,
然后将DAO 层的数据 变成 DTO,
再将起返回给response

Common
定义了一个结果集合,将Service之间的RPC通信结果展示.

现在要做的就是是搞个Test,通信
1.导入lottery依赖(lottery和lottery RPC必需要先install到Maven仓库才能被识别到,最后导入进来)
2.RPC层类(数据)都要实现序列化,包括Dto Req Res.
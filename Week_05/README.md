## 学习笔记
### java动态代理
代理模式是设计模式中非常重要的一种类型，而设计模式又是编程中非常重要的知识点，特别是在业务系统的重构中，更是有举足轻重的地位。代理模式从类型上来说，可以分为静态代理和动态代理两种类型。

动态代理与静态代理的区别是静态代理只能针对特定一种产品（蛋糕、面包、饼干、酸奶）做某种代理动作（撒杏仁），而动态代理则可以对所有类型产品（蛋糕、面包、饼干、酸奶等）做某种代理动作（撒杏仁）。

#### 代理模式中角色
1. 目标接口
    - 作用:定义对外暴露公共方法
2. 目标实现
    - 作用:业务逻辑具体实现
3. 代理类
    - 作用:对业务逻辑的增强
#### JDK动态代理
1. 原理:jvm在类加载时期,通过目标接口和实现类,动态生成二进制的class
2. 实现
    - 涉及目标类:java.lang.reflect.Proxy , java.lang.reflect.InvocationHandler
    - 创建过程
        - jvm会根据目标对象和目标接口在内存中生成和.class文件等同的字节码
        - 将二进制字节码转换为.class对象
        - 通过newInstance创建代理对象实例
3. InvocationHandler
```
public static class ObjectProxy<T> implements InvocationHandler {

        //InvocationHandler是通过代理类对象执行被代理类对象的方法
        private T target;

        public ObjectProxy(T target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("前置执行");
            Object result = method.invoke(target, args);
            System.out.println(result);
            System.out.println("后置执行");
            return result;
        }
    }
```
4. 生成代理对象
```
 //生成目标对象的代理类实例
        //param:classloader,目标接口,invocationHandler
        OrderService service = (OrderService) Proxy.newProxyInstance(classLoader, interfaces, proxy);
```

### 动态代理的应用
动态代理在代码界可是有非常重要的意义，我们开发用到的许多框架都使用到了这个概念。我所知道的就有：Spring AOP、Hibernate、Struts 使用到了动态代理。

- Spring AOP。Spring 最重要的一个特性是 AOP（Aspect Oriented Programming 面向切面编程），利用 Spring AOP 可以快速地实现权限校验、安全校验等公用操作。而 Spring AOP 的原理则是通过动态代理实现的，默认情况下 Spring AOP 会采用 Java 动态代理实现，而当该类没有对应接口时才会使用 CGLib 动态代理实现。

- Hibernate。Hibernate 是一个常用的 ORM 层框架，在获取数据时常用的操作有：get() 和 load() 方法，它们的区别是：get() 方法会直接获取数据，而 load() 方法则会延迟加载，等到用户真的去取数据的时候才利用代理类去读数据库。

- Struts。Struts 现在虽然因为其太多 bug 已经被抛弃，但是曾经用过 Struts 的人都知道 Struts 中的拦截器。拦截器有非常强的 AOP 特性，仔细了解之后你会发现 Struts 拦截器其实也是用动态代理实现的。

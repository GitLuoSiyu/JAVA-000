## 学习笔记

记一些对于我自己来说比较重要的课上笔记

### JVM 运行模式
JVM 有两种运行模式：

- server：设置 jvm 使 server 模式，特点是启动速度比较慢，但运行时性能和内存管理效率很高，适用于生产环境。在具有 64 位能力的 jdk 环境下将默认启用该模式，而忽略 -client 参数。

- client ：JDK1.7 之前在 32 位的 x86 机器上的默认值是 -client 选项。设置 jvm 使用 client 模式，特点是启动速度比较快，但运行时性能和内存管理效率不高，通常用于客户端应用程序或者PC应用开发和调试。

此外，我们知道 JVM 加载字节码后，可以解释执行，也可以编译成本地代码再执行，所以可以配置 JVM 对字节码的处理模式：

- Xint：在解释模式（interpreted mode）下，-Xint 标记会强制 JVM 解释执行所有的字节码，这当然会降低运行速度，通常低 10 倍或更多。
- Xcomp：-Xcomp 参数与 -Xint 正好相反，JVM 在第一次使用时会把所有的字节码编译成本地代码，从而带来最大程度的优化。
- Xmixed：-Xmixed 是混合模式，将解释模式和变异模式进行混合使用，有 JVM 自己决定，这是 JVM 的默认模式，也是推荐模式。 我们使用 java -version 可以看到 mixed mode 等信息。

### 设置堆内存

JVM 的内存设置是最重要的参数设置，也是 GC 分析和调优的重点。
```
JVM 总内存=堆+栈+非堆+堆外内存。
```

相关的参数：

- Xmx, 指定最大堆内存。 如 -Xmx4g. 这只是限制了 Heap 部分的最大值为 4g。这个内存不包括栈内存，也不包括堆外使用的内存。

- Xms, 指定堆内存空间的初始大小。 如 -Xms4g。 而且指定的内存大小，并不是操作系统实际分配的初始值，而是 GC 先规划好，用到才分配。 专用服务器上需要保持 -Xms和-Xmx一致，否则应用刚启动可能就有好几个 FullGC。当两者配置不一致时，堆内存扩容可能会导致性能抖动。

- Xmn, 等价于 -XX:NewSize，使用 G1 垃圾收集器 不应该 设置该选项，在其他的某些业务场景下可以设置。官方建议设置为 -Xmx 的 1/2 ~ 1/4。

- XX:MaxPermSize=size, 这是 JDK1.7 之前使用的。Java8 默认允许的 Meta 空间无限大，此参数无效。

- XX:MaxMetaspaceSize=size, Java8 默认不限制 Meta 空间, 一般不允许设置该选项。

XX:MaxDirectMemorySize=size，系统可以使用的最大堆外内存，这个参数跟-Dsun.nio.MaxDirectMemorySize效果相同。

- Xss, 设置每个线程栈的字节数。 例如 -Xss1m 指定线程栈为 1MB，与-XX:ThreadStackSize=1m等价。这里要特别说一下堆外内存，也就是说不在堆上的内存，我们可以通过jconsole，jvisualvm 等工具查看。

RednaxelaFX 提到：
```
一个 Java 进程里面，可以分配 native memory 的东西有很多，特别是使用第三方 native 库的程序更是如此。
```

但在这里面除了

- GC heap = Java heap + Perm Gen（JDK <= 7）
- Java thread stack = Java thread count * Xss
- other thread stack = other thread count * stack size
- CodeCache 等东西之外

还有诸如 HotSpot VM 自己的 StringTable、SymbolTable、SystemDictionary、CardTable、HandleArea、JNIHandleBlock 等许多数据结构是常驻内存的，外加诸如 JIT 编译器、GC 等在工作的时候都会额外临时分配一些 native memory，这些都是 HotSpot VM自己所分配的 native memory；在 JDK 类库实现中也有可能有些功能分配长期存活或者临时的 native memory。

然后就是各种第三方库的 native 部分分配的 native memory。

“Direct Memory”，一般来说是 Java NIO 使用的 Direct-X-Buffer（例如 DirectByteBuffer）所分配的 native memory，这个地方如果我们使用 netty 之类的框架，会产生大量的堆外内存。
```
JAVA_OPTS="-Xms28g -Xmx28g"
```

### 配置多少 xmx 合适
 配置系统或容器里可用内存的 70-80% 最好; xmx 和 xms 是不是要配置成一致的; 一般情况下，我们的服务器是专用的，就是一个机器（也可能是云主机或 docker 容器）只部署一个 Java 应用，这样的时候建议配置成一样的，好处是不会再动态去分配，如果内存不足及时知道。

### 小结
我主前端付Android，没有接触过后端，第一次正儿八经的学后端，希望能在这条路上稳稳地走下去。
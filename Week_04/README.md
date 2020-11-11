## 学习笔记

### 异常处理

在上一课时中，细心的你可能注意到了，在 synchronized 生成的字节码中，其实包含两条 monitorexit 指令，是为了保证所有的异常条件，都能够退出。
其中，Error 和 RuntimeException 是非检查型异常（Unchecked Exception），也就是不需要 catch 语句去捕获的异常；而其他异常，则需要程序员手动去处理。

### 异常表

在发生异常的时候，Java 就可以通过 Java 执行栈，来构造异常栈。回想一下第 02 课时中的栈帧，获取这个异常栈只需要遍历一下它们就可以了。
但是这种操作，比起常规操作，要昂贵的多。Java 的 Log 日志框架，通常会把所有错误信息打印到日志中，在异常非常多的情况下，会显著影响性能。
我们还是看一下上一课时生成的字节码：
```java
void doLock();
    descriptor: ()V
    flags:
    Code:
      stack=2, locals=3, args_size=1
         0: aload_0
         1: getfield      #3                  // Field lock:Ljava/lang/Object;
         4: dup
         5: astore_1
         6: monitorenter
         7: getstatic     #4                  // Field java/lang/System.out:Ljava/io/PrintStream;
        10: ldc           #8                  // String lock
        12: invokevirtual #6                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
        15: aload_1
        16: monitorexit
        17: goto          25
        20: astore_2
        21: aload_1
        22: monitorexit
        23: aload_2
        24: athrow
        25: return
      Exception table:
         from    to  target type
             7    17    20   any
            20    23    20   any
```


可以看到，编译后的字节码，带有一个叫 Exception table 的异常表，里面的每一行数据，都是一个异常处理器：
- from 指定字节码索引的开始位置
- to 指定字节码索引的结束位置
- target 异常处理的起始位置
- type 异常类型

也就是说，只要在 from 和 to 之间发生了异常，就会跳转到 target 所指定的位置。

### finally

通常我们在做一些文件读取的时候，都会在 finally 代码块中关闭流，以避免内存的溢出。关于这个场景，我们再分析一下下面这段代码的异常表。
```java
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class A {
    public void read() {
        InputStream in = null;
        try {
            in = new FileInputStream("A.java");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
```
上面的代码，捕获了一个 FileNotFoundException 异常，然后在 finally 中捕获了 IOException 异常。当我们分析字节码的时候，却发现了一个有意思的地方：IOException 足足出现了三次。
```java
Exception table:
    from    to  target type
    17    21    24   Class java/io/IOException
    2    12    32   Class java/io/FileNotFoundException
    42    46    49   Class java/io/IOException
     2    12    57   any
    32    37    57   any
    63    67    70   Class java/io/IOException
```

Java 编译器使用了一种比较傻的方式来组织 finally 的字节码，它分别在 try、catch 的正常执行路径上，复制一份 finally 代码，追加在 正常执行逻辑的后面；同时，再复制一份到其他异常执行逻辑的出口处。这也是下面这段方法不报错的原因，都可以在字节码中找到答案。
```java
//B.java
public int read() {
        try {
            int a = 1 / 0;
            return a;
        } finally {
            return 1;
        }
}
```
下面是上面程序的字节码，可以看到，异常之后，直接跳转到序号 8 了。
```java
stack=2, locals=4, args_size=1
         0: iconst_1
         1: iconst_0
         2: idiv
         3: istore_1
         4: iload_1
         5: istore_2
         6: iconst_1
         7: ireturn
         8: astore_3
         9: iconst_1
        10: ireturn
      Exception table:
         from    to  target type
             0     6     8   any
```
### 装箱拆箱

在刚开始学习 Java 语言的你，可能会被自动装箱和拆箱搞得晕头转向。Java 中有 8 种基本类型，但鉴于 Java 面向对象的特点，它们同样有着对应的 8 个包装类型，比如 int 和 Integer，包装类型的值可以为 null，很多时候，它们都能够相互赋值。
我们使用下面的代码从字节码层面上来观察一下：
```java
public class Box {
    public Integer cal() {
        Integer a = 1000;
        int b = a * 10;
        return b;
    }
}
```
上面是一段简单的代码，首先使用包装类型，构造了一个值为 1000 的数字，然后乘以 10 后返回，但是中间的计算过程，使用了普通类型 int。
```java
public java.lang.Integer read();
    descriptor: ()Ljava/lang/Integer;
    flags: ACC_PUBLIC
    Code:
      stack=2, locals=3, args_size=1
         0: sipush        1000
         3: invokestatic  #2                  // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
         6: astore_1
         7: aload_1
         8: invokevirtual #3                  // Method java/lang/Integer.intValue:()I
        11: bipush        10
        13: imul
        14: istore_2
        15: iload_2
        16: invokestatic  #2                  // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        19: areturn
```
通过观察字节码，我们发现赋值操作使用的是 Integer.valueOf 方法，在进行乘法运算的时候，调用了 Integer.intValue 方法来获取基本类型的值。在方法返回的时候，再次使用了 Integer.valueOf 方法对结果进行了包装。这就是 Java 中的自动装箱拆箱的底层实现。但这里有一个 Java 层面的陷阱问题，我们继续跟踪 Integer.valueOf 方法。
```java
@HotSpotIntrinsicCandidate
    public static Integer valueOf(int i) {
        if (i >= IntegerCache.low && i <= IntegerCache.high)
            return IntegerCache.cache[i + (-IntegerCache.low)];
        return new Integer(i);
    }
```

这个 IntegerCache，缓存了 low 和 high 之间的 Integer 对象，可以通过 -XX:AutoBoxCacheMax 来修改上限。
```java
public class BoxCacheError{
    public static void main(String[] args) {

        Integer n1 = 123;
        Integer n2 = 123;
        Integer n3 = 128;
        Integer n4 = 128;

        System.out.println(n1 == n2);
        System.out.println(n3 == n4);
    }
```
当我使用 java BoxCacheError 执行时，是 true,false；当我加上参数 java -XX:AutoBoxCacheMax=256 BoxCacheError 执行时，结果是 true,ture，原因就在于此。

### 数组访问

我们都知道，在访问一个数组长度的时候，直接使用它的属性 .length 就能获取，而在 Java 中却无法找到对于数组的定义。
比如 int[] 这种类型，通过 getClass（getClass 是 Object 类中的方法）可以获取它的具体类型是 [I。
其实，数组是 JVM 内置的一种对象类型，这个对象同样是继承的 Object 类。
下面来观察一下数组的生成和访问。
```java
public class ArrayDemo {
    int getValue() {
        int[] arr = new int[]{
                1111, 2222, 3333, 4444
        };
        return arr[2];
    }

    int getLength(int[] arr) {
        return arr.length;
    }
}
```
首先看一下 getValue 方法的字节码。
```java
int getValue();
    descriptor: ()I
    flags:
    Code:
      stack=4, locals=2, args_size=1
         0: iconst_4
         1: newarray       int
         3: dup
         4: iconst_0
         5: sipush        1111
         8: iastorae
         9: dup
        10: iconst_1
        11: sipush        2222
        14: iastore
        15: dup
        16: iconst_2
        17: sipush        3333
        20: iastore
        21: dup
        22: iconst_3
        23: sipush        4444
        26: iastore
        27: astore_1
        28: aload_1
        29: iconst_2
        30: iaload
        31: ireturn
```

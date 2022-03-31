## 1、工厂模式

实际上工厂模式存在三种实现形式，分别是 简单工厂模式、工厂方法模式、抽象工厂模式

### 1.1、简单工厂模式

> 相对来说简单工厂模式整体复杂程度不高，主要负责的是实现和创建所有实例的内部逻辑，工厂类的创建产品的方法可以直接被外界所调用，创建所需的产品对象

### 1.2、抽象工厂模式(abstract-factory-pattern)

> 提供一个创建一系列相关或者相互依赖对象的接口，不需要指定他们具体的类。也就是说客户端不需要指定产品的具体类型，创建多个产品族中的产品对象

### 1.3、工厂方法模式(factory-method-pattern)

> 工厂方法模式有叫做多态性工厂模式，指定义一个创建对象的接口，但由实现这个接口的类来决定实例化哪个类，工厂方法把类的实现推迟到了子类

在工厂方法模式中，不再由单一工厂类生产产品，而是由工厂的子类实现具体产品的创建。它主要解决的是switch过多的情况

#### 1.3.1、定义抽象工厂和产品

```java
// 抽象产品
public interface IProduct {
    void doSomething();
}
// 抽象工厂
public interface IFactory {
    IProduct makeProduct();
}
// 定义对应三款产品
// 定义三款产品对应的工厂
```

#### 1.3.2、优点和缺点

- 优点

> 1、灵活性增强了，对于产生新产品只需要多写一个相应的工厂类。
>
> 2、耦合度降低了，上层调用只需要知道产品是抽象类，不需要关心其他实现类。

- 缺点

> 1、增加了类的个数，增加了复杂度
>
> 2、增加了系统的抽象性和理解难度
>
> 3、抽象产品只能生产单一产品，此弊端可以采用抽象工厂模式来解决

## 2、单例模式(singleton-service)

> 单例模式指确保一个类在任何情况下都绝对只有一个实例，并提供一个全局访问点。

### 2.1、应用场景

> 1、需要频繁创建的一些类，使用单例可以降低系统内存的压力，减少GC次数
>
> 2、某些类创建实例时占用资源较多，或者实例化时间较长，而且经常使用
>
> 3、频繁访问数据库或者文件的对象。
>
> 4、对于一些控制硬件级别的操作，或者从系统上来讲应当是单一控制逻辑的操作，如果有多个实例，则系统就会乱套

### 2.2、常用写法

- 饿汉式

```java
public class HungryStaticSingleton {
    private static final HungryStaticSingleton hungryStaticSingleton = new HungryStaticSingleton();

    private HungryStaticSingleton() {
    }

    public static HungryStaticSingleton getInstance() {
        return hungryStaticSingleton;
    }
}
```

- 懒汉式

```java
public class LazySimpleSingleton {
    private LazySimpleSingleton() {
    }

    private static LazySimpleSingleton lazy = null;

    public static LazySimpleSingleton getInstance() {
        if (lazy == null) {
            synchronized (LazySimpleSingleton.class) {
                if (lazy == null) {
                    lazy = new LazySimpleSingleton();
                }
            }
        }
        return lazy;
    }
}
```

- 内部类

```java
public class Client {
    public static void main(String[] args) {
        Singleton.getInstance();
    }

    static class Singleton {
        private static final Singleton instance = new Singleton();

        private Singleton() {
        }

        public static Singleton getInstance() {
            return instance;
        }
    }
}
```

- 枚举

```java
public enum EnumSingleton {
    INSTANCE;
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static EnumSingleton getInstance() {
        return INSTANCE;
    }
}
```

### 2.3、优缺点

- 优点

> 1、可以保证内存只有一个实例，减少内存的开销
>
> 2、可以避免对资源的多重占用
>
> 3、单例模式设置全局访问点，可以优化和共享资源的访问

- 缺点

> 1、单例模式一般没有接口，扩展困难。如果要扩展，则除了修改原来的代码没有第二种途径，违背开闭原则
>
> 2、并发测试中，单例模式不利于代码调试。在调试过程中如果单例中的代码没有执行完，也不能模拟生成一个新的对象。
>
> 3、单例模式的功能代码通常写在一个类中，如果功能设计不合理，很容易违背单一职责原则

## 3、原型模式(prototype-service)

> 指原型实例指定创建对象的种类，并且通过复制这些原型创建新的对象，java本身的clone方法实际上属于浅克隆。

### 3.1、优缺点

- 优点

> 1、java自带的原型模式基于内存二进制流的复制，在性能上比new一个对象更加优良。
>
> 2、可以使用深克隆方式保存对象状态，使用原型模式将对象复制一份，并将其状态保存起来，简化了创建对象的过程，以便在需要的时候使用，例如状态还原，可以辅助实现撤销操作。

- 缺点

> 1、需要为每一个类都配置一个clone方法
>
> 2、clone方法位于类的内部，当对已有类进行改造的时候，需要修改代码，违背了开闭原则
>
> 3、当实现深克隆时，需要编写较为复杂的代码，而且当对象存在多重嵌套引用时，为了实现深克隆，每一层对象对应的类都必须支持深克隆，实现起来会比较麻烦。因此深克隆和浅克隆需要考虑场景

## 4、代理模式(proxy-pattern)

> 指为其他对象提供一种代理，以控制这个对象的访问

### 4.1、代理模式的主要角色

- 抽象主题角色

> 抽象主题类的主要职责是声明真实主题与代理的共同接口方法，该类可以是接口，也可以是抽象类

- 真实主题角色

> 该类也被称为代理类，该类定义了代理所表示的真实对象，是负责执行系统的真正的逻辑业务对象。

- 代理主题角色

> 也被称为代理类，其内部持有RealSubject的引用，因此具备完全对RealSubject 的代理权。客户端调用代理对象的方法，但会在代理对象前后增加一些处理代码。

### 4.2、静态代理和动态代理的区别

> 1、静态代理只能通过手动完成代理操作，如果被代理类增加了新的方法，则代理类需要同步增加，违背开闭原则
>
> 2、动态代理采用在运行时动态生成代码的方式，取消了对被代理类的扩展限制，遵循开闭原则。
>
> 3、若动态代理要对目标类的增强逻辑进行扩展，结合策略模式，只需要新增策略类便可以完成，无需修改代理类的代码。

### 4.3、优点和缺点

- 优点

> 1、代理模式能将代理对象与真实被调用目标对象分离。
>
> 2、在一定程度上降低了系统的耦合性，扩展性好。
>
> 3、可以起到保护目标对象的作用。
>
> 4、可以增强目标对象的功能。

- 缺点

> 1、代理模式会造成系统设计中类的数量增加。
>
> 2、在客户端和目标对象中增加一个代理对象，会导致处理请求的速度变慢。
>
> 3、增加了系统的复杂度。

## 5、门面模式(facade-pattern)

> 又叫做外观模式，提供了一个统一的接口用来访问子系统中的一群接口，其主要特征是定义了一个高层接口让子系统更容易使用。

### 5.1、应用场景

> 1、为一个复杂的模块或者子系统提供一个简洁的供外界访问的接口
>
> 2、希望提高子系统的独立性
>
> 3、当子系统由于不可避免的暂时原因导致可能存在bug或者性能相关的问题时，可以通过门面模式提供一个高层接口，隔离客户端和子系统的直接交互，预防代码污染。

### 5.2、优点和缺点

- 优点

> 1、简化的调用过程，不需要深入了解子系统，以防给子系统带来风险
>
> 2、减少系统依赖，松散耦合
>
> 3、更好的划分访问层次，提高了安全性
>
> 4、遵循迪米特法则

- 缺点

> 1、当增加子系统和扩展子系统行为时，可能容易带来未知风险。
>
> 2、不符合开闭原则
>
> 3、某些情况下，可能违背单一指责原则

## 6、装饰器模式(decorator-pattern)

> 又叫做包装器模式，指在不改变原有对象的基础上动态的给一个对象添加一些额外的指责，就增加功能来说装饰器模式相比生成子类更为灵活。

### 6.1、应用场景

> 1、用于扩展一个类的功能，或者给一个类添加附加职责。
>
> 2、动态的给一个对象添加功能，这些功能可以再动态的被撤销
>
> 3、需要为一批平行的兄弟类进行改装或加装功能

### 6.2、优点和缺点

- 优点

> 1、装饰器是继承的有力补充，比继承灵活，在不改变原有对象的情况下，动态的给一个对象扩展功能，即插即用。
>
> 2、通过使用不同装饰类及这些装饰类的排列组合，可以实现不同的效果
>
> 3、装饰器模式完全遵守开闭原则

- 缺点

> 1、会出现更多的代码、更多的类，增加程序的复杂性。
>
> 2、动态装饰在多层装饰时会更复杂

## 7、享元模式

> 又叫做轻量级模式，是对象池的一种实现。类似线程池，线程池可以避免不停的创建和销毁多个对象，消耗性能，享元模式提供了减少对象数量从而改善应用所需的对象结构的方式。其宗旨是共享细粒度对象，将多个对同一对象的访问集中起来，不必为每个访问者都创建一个单独的对象，以此来降低内存的消耗。

### 7.1、优点和缺点

- 优点

> 1、减少对象的创建，降低内存中对象的数量，降低系统内存，提高效率
>
> 2、减少内存之外的其他资源占用

- 缺点

> 关注内、外部状态，关注线程安全问题
>
> 使系统、程序的逻辑复杂化

## 8、组合模式(composite-pattern)

> 组合模式又叫做整体-部分模式，它的宗旨是通过将单个对象和组合对象用相同的接口进行表示，使得客户对单个对象和组合对象的使用具有一致性。

### 8.1、应用场景

> 1、希望客户端可以忽略组合对象与单个对象的差异
>
> 2、对象层次具备整体和部分，呈树形结构

### 8.2、优点和缺点

- 优点

> 1、清楚地定义各层次的复杂对象，表示对象的全部或部分层次
>
> 2、让客户端忽略层次的差异，方便对整个层次结构进行控制。
>
> 3、简化客户端代码
>
> 4、符合开闭原则

- 缺点

> 1、限制类型时会较为复杂
>
> 2、使设计变得更加抽象

## 9、适配器模式(adapter-pattern)

> 适配器模式又叫做变压器模式，它的功能是将一个类的接口变成客户端所期望的另一种接口，从而使原本因接口不匹配而导致无法在一起工作的两个类能够一起工作

### 9.1、应用场景

> 1、已经存在的类，它的方法和需求不匹配(方法结果相同或相似)的情况。
>
> 2、适配器模式是软件维护阶段，由于不同的产品、不同的厂家造成功能类似而接口不相同的情况下的解决方案

适配器模式的三种模式

> 1、目标角色：也就是我们期望的接口
>
> 2、源角色：存在于系统中，是指内容满足客户需求但接口不匹配的接口实例。
>
> 3、适配器：将 源角色转化为目标角色的类实例

### 9.2、优点和缺点

- 优点

> 1、能提高类的透明性和复用，但现有的类复用不需要改变。
>
> 2、适配器类和原角色类解藕，提高程序的扩展性。
>
> 3、在很多业务场景中符合开闭原则。

- 缺点

> 适配器编写过程需要结合业务场景全面考虑，可能会增加系统的复杂性。
>
> 增加代码阅读难度，降低代码的可读性，过多的适配器会使系统代码变得凌乱。

## 10、桥接模式(bridge-pattern)

> 又叫做桥梁模式、接口模式、柄体模式，将抽象部分与具体实现部分分离，使它们都可以独立的变化

### 10.1、应用场景

> 1、在抽象和具体实现之间需要增加更多灵活性的场景。
>
> 2、一个类存在两个（或多个）独立变化的维度，而这两个(或多个)维度都需要独立进行扩展。
>
> 3、不希望使用继承，或因为多层继承导致系统类的个数剧增。

### 10.2、优点和缺点

- 优点

> 1、分离抽象部分及具体实现部分
>
> 2、提高了系统扩展性
>
> 3、符合开闭原则
>
> 4、符合合成复用原则

- 缺点

> 1、增加了系统的理解与设计难度
>
> 2、需要正确的识别系统中的两个独立变化的维度。

## 11、委派模式(delegate-pattern)

> 委派模式是一种面向对象的设计模式，允许对象组合实现与继承相同的代码重用。它的基本作用就是负责任务的调用和分配，是一种特殊的静态代理模式，可以理解为全权代理模式，但是代理模式注重过程，而委派模式注重结果，委派模式属于行为设计模式。

### 11.1、应用场景

> 1、需要实现表现层和业务层之间的松耦合
>
> 2、需要编排多个服务之间的调用
>
> 3、需要封装一层服务查找和调用。

### 11.2、优点和缺点

- 优点

> 通过任务委派将一个大型任务细化，然后通过统一管理这些子任务的完成情况实现任务的跟进，加快任务执行的效率。

- 缺点

> 需要根据任务的复杂程度进行不同的改变，在任务比较复杂的情况下，可能需要进行多重委派，容易造成紊乱。

## 12、模板方法模式(template-pattern)

> 指定义一个操作中的算法的基础，而将一些步骤延迟到子类中，使得子类可以不改变一个算法的结构即可重定义该方法的某些特定步骤。

### 12.1、应用场景

> 1、一次性实现一个算法的不变部分，并将可变行为留给子类来实现
>
> 2、各子类中公共的行为被提取出来，集中到一个公共的父类中，从而避免代码的重复

### 12.2、优点和缺点

- 优点

> 1、利用模板方法将相同的处理逻辑代码放到抽象父类中，可以提高代码的复用性
>
> 2、将不同的算法逻辑分离到不同的子类中，通过对子类的扩展增加新的行为，提高代码的可扩展性
>
> 3、把不变的行为写在父类上，去除子类代码，提供了一个很好的代码复用平台，符合开闭原则

- 缺点

> 1、每一个抽象都需要一个字类来实现，这样导致类的数量增加
>
> 2、类数量增加，间接的增加了系统实现的复杂度
>
> 3、由于继承关系自身的缺点，如果父类添加新的抽象方法，则所有子类都要修改一遍。

## 13、策略模式(strategy-service)

> 它将定义的算法家族分别封装起来，将它们之间可以互相转化，从而让算法的变化不会影响到使用算法的用户，属于行为性设计模式

### 13.1、应用场景

> 1、针对同一类型的问题，有多种处理方式，每一种都能独立解决问题
>
> 2、需要自由切换算法的场景
>
> 3、需要屏蔽算法规则的场景

### 13.2、优点和缺点

- 优点

> 1、策略模式符合开闭原则
>
> 2、避免使用多重条件转移语句，如 if else，switch case 语句
>
> 3、使用策略模式可以提高算法的保密性和安全性

- 缺点

> 1、客户端必须知道所有的策略，并且自行决定使用哪一个策略
>
> 2、代码会产生非常多的策略类，增加了维护的难度。

## 14、责任链模式(chain-responsibility-pattern)

> 将链中的每一个节点都看作一个对象，每个节点处理的请求均不同，且内部自动维护下一个节点对象。当一个请求从链式的首端发出时，会沿着责任链预设的路径依次传递到每一个节点对象，直至被链中的某个对象处理为止。

### 14.1、应用场景

> 1、多个对象可以处理同一请求，但具体由哪个对象处理则在运行时动态决定
>
> 2、在不明确指定接收者的情况下，向多个对象中的一个提交请求
>
> 3、可动态指定一组对象处理请求。

### 14.2、优点和缺点

- 优点

> 1、将请求与处理解耦
>
> 2、请求处理者只需要关注自己感兴趣的请求进行处理即可，对于不感兴趣的请求，直接转发给下一个节点对象。
>
> 3、具备链式传递处理请求功能，请求发送者不需要知晓链路结构，只需要等待请求处理结果即可。
>
> 4、链路结构灵活，可以通过改变链路结构动态的新增或删减责任。
>
> 5、易于扩展新的处理类，符合开闭原则。

- 缺点

> 1、责任链太长或者处理时间过长，会影响整体性能
>
> 2、如果节点对象存在循环引用，则会造成死循环，导致系统崩溃

## 15、迭代器模式(iterator-pattern)

> 它提供了一种按顺序访问集合/容器对象元素的方法，而又无须暴露集合内部表示。迭代器模式可以为不同的容器提供一致的便利行为，而不用关心容器内元素的组成结构

- 四类角色

> 1、抽象迭代器(Iterator):抽象迭代器负责定义访问和遍历元素的接口
>
> 2、具体迭代器(ConcreteIterator):提供具体的元素遍历行为
>
> 3、抽象容器(IAggregate):负责定义提供具体迭代器的接口
>
> 4、具体容器(ConcreteAggregate)：创建具体迭代器

### 15.1、应用场景

> 1、访问一个集合对象的内容而无须暴露它的内部表示
>
> 2、为遍历不同的集合结构提供一个统一的访问接口。

### 15.2、优点和缺点

- 优点

> 1、多态迭代：为不同的聚合结构提供一致的遍历接口，即一个迭代接口可以访问不同的集合对象
>
> 2、简化集合对象接口：迭代器模式将集合对象本身应该提供的元素迭代接口抽取到迭代器中，使集合对象无需关心具体的迭代行为。
>
> 3、元素迭代功能多样化：每个集合对象都可以提供一个和多个不同的迭代器，使得同种元素的聚合结构可以有不同的迭代行为
>
> 4、解耦迭代与集合：迭代器模式封装了具体的迭代算法，迭代算法的变化不会影响到集合对象的架构。

- 缺点

> 1、对于比较简单的遍历，使用迭代器便利较为繁琐
>
> 2、日常开发基本不会自己写迭代器。除非自己需要定制一个自己实现的数据结构对应的迭代器

## 16、命令模式(command-pattern)

> 主要是对命令的封装，每个命令都是一个操作：请求方发出请求要执行一个一个操作；接收方收到请求，并执行操作。命令模式解耦了请求方和接收方，请求方只需请求执行命令，不用关心命令怎样被接收、怎样被操作及是否被执行等。

### 16.1、应用场景

> 1、现实语义中具有“命令”的操作（如命令菜单，Shell命令）
>
> 2、请求的调用者和接收者需要解耦，使得调用者和接收者不直接交互
>
> 3、需要抽象出等待执行的行为，比如撤销（Undo）操作和恢复（Redo）等操作
>
> 4、需要支持命令宏（命令组合操作）

### 16.2、优点和缺点

- 优点

> 1、通过引入中间件（抽象接口），解耦了命令请求与实现
>
> 2、扩展性良好，可以很容易地增加新命令
>
> 3、支持组合明令，支持命令队列
>
> 4、可以在现有命令的基础上，增加额外功能，比如日志记录，结合装饰器模式会更加灵活。

- 缺点

> 1、具体的命令类可能过多
>
> 2、命令模式的结果其实就是接收方的执行结果，但是为了以命令的形式进行架构、解耦请求与实现，引入了额外类型结构（引入了请求方与抽象命令接口），增加了理解上的困难。

## 17、状态机模式(state-machine-pattern)

> 允许对象在内部状态发生改变时改变它的行为，对象看起来好像修改了它的类。
>
> 状态模式中类的行为是由状态决定的，在不同状态下有不同的行为。其意图是让一个对象在其内部改变的时候，行为也随之改变。状态模式的核心是状态与行为的绑定、不同的状态对应不同的行为

### 17.1、应用场景

> 1、行为随状态改变而改变的场景。
>
> 2、一个操作中含有庞大的多分支结构，并且这些分支取决于对象的状态。

### 17.2、优点和缺点

- 优点

> 1、结构清晰：将状态独立成类，消除了冗余的 if else 和 switch case 语句，使得代码更加简洁
>
> 2、将状态转化显示化：通常对象内部都是使用数值类型来定义状态的，状态的切换通过赋值进行表现，不够直观；而使用状态类，当切换状态时，是以不同的类进行表示的，转换目的更加明确
>
> 3、状态类职责明确且具备扩展性。

- 缺点

> 1、类膨胀；如果一个事物具备很多状态，则会造成状态类太多
>
> 2、状态模式的结构与实现都较为复杂，如果使用不当，将导致程序结构和代码的混乱。
>
> 3、状态模式对开闭原则的支持并不友好，对于可以切换状态的状态模式，增加新的状态类需要修改那些负责状态转换的源码，否则无法切换到新增状态，而且修改某个状态类的行为也需要修改对应类的源码。

## 18、备忘录模式(memento-pattern)

> 1、备忘录模式又叫做快照模式或者令牌模式，指在不破坏封装的前提下，捕获一个对象的内部状态，并在对象之外保存这个状态。这样以后就可以将该对象恢复到原先保存的状态。

### 18.1、应用场景

> 1、需要保存历史快照的场景
>
> 2、希望在对象之外保存状态，且除了自己，其他类对象无法访问状态保存的具体内容。

### 18.2、优点和缺点

- 优点

> 1、简化了发起人实体类的职责，隔离状态存储与获取，实现了信息的封装，客户端无需关心状态的保存细节。
>
> 2、提供了状态回滚功能

- 缺点

> 主要是消耗资源，如果需要保存的状态过多，则每一次保存都会消耗很多内存

## 19、中介者模式(mediator-pattern)

> 用一个中介对象封装一系列对象交互，中介者使各对象不需要显式的相互作用，从而使其耦合松散，而且可以独立的改变他们之间的交互，属于行为型设计模式。

### 19.1、应用场景

> 1、系统中对象之间存在复杂的引用关系，产生的相互依赖关系结构混乱且难以理解。
>
> 2、交互的公共行为，如果需要改变行为，则可以增加新的中介者类

## 20、解释器模式(interpreter-pattern)



## 21、观察者模式(observer-pattern)



## 22、访问者模式(visitor-pattern)


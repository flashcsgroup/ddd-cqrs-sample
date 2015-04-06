

# General introduction #
If You are new to DDD You can quickly grasp general idea by watching this prezi:
[Domain Driven Design - A place for everything and everything in its place](http://prezi.com/woxd5xokf36g/domain-driven-design-a-place-for-everything-and-everything-in-its-place-en)

# Technical introduction #

This document describes sample code illustrating DDD techniques. The intention is to present code form the perspective of the business model developer. So we focus on business developers' mental model without digging to deep into engine details.

Idea is that business developers does not need to understand what's under the hood - he or she should rather focus on modeling and well crafted business code.

## Architecture and design visualization using Scalable Map" ##

Java (and any known language) does not support understanding complex code structures. Neither UML tools do.

Therefore, using [Prezi](http://prezi.com), we have created a "scalable map" of both big picture (architecture) and details view (class design).

  * You can watch it [here](http://prezi.com/hi2dmhfej9zu/ddd-cqrs-sample/)
  * You can "play" default road trip or pan and zoom freely using mouse
  * You can click on SVN link and go straight to the code!

## Project Structure ##
[SVN Repository](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample) contains project that is structured in the standard Maven way.

### DDD common classes ###
Package [pl.com.bottega.ddd](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Fddd) contains common DDD library (not framework).

Common DDD goodies are:
  * [application](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Fddd%2Fapplication)
    * annotation - annotations used in Application Layer artifacts
      * ApplicationEvent - tagging annotation, will be used in visual tools
      * ApplicationService - if You prefer services instead of commands and handlers. Used for tagging but also provides Spring features (this annotations is annotated by Spring annotations)
      * ApplicationStatefullComponent - for session scoped Spring beans
  * [domain](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Fddd%2Fdomain)
    * annotations - annotations for all DDD Building Blocks - some of them are just for tagging (will be used by visual analyzers) but some of them are annotated by Spring's annotations that introduces Spring based features like being a Bean. All annotations will be described in detail in [Domain Layer section](DomainDrivenDesignBusinessDeveloperMentalModel#Domain_Layer.md) of this document.
    * sharedcernel - common model used in all Bounded Contexts
    * infrastructure
      * events - Events Engine implementation, Common technical listeners, annotations for listeners
      * repo - JPA base repositories, that can be handful
      * sagas - Saga Engine implementation
  * [sagas](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Fddd%2Fsagas) - Saga API - described in later section of this document


### CqRS common classes ###
Package [pl.com.bottega.cqrs](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Fcqrs) contains common CqRS "engine".

Common CqRS goodies are described in the [II](PART.md), because they are just technical details.

### ERP product classes ###
Package [pl.com.bottega.erp](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Ferp) is The Product - Enterprise Resource Planning class system.

System modules separation at this package level:
  * crm - Client Relationship Management module
  * sagas - Sagas which contains cross module features
  * sales - Sales module - **most examples are implemented in this module**
  * shipping - Shipping module

Modules are independent set of features that can  be used separately and still being valuable.

In this sample, modules are equal to DDD Bounded Context.

### ERP module structure ###
Open [Sales](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/#main%2Fjava%2Fpl%2Fcom%2Fbottega%2Ferp%2Fsales) module, look at packages structure and follow the description:

Each module is packaged using the same application architecture style (beginning from the top):
  1. _webui_ - at the top, web client controllers
  1. two separated CqRS stacks:
    * read (presentation) model
      1. _presentation_ reading presentation data
    * write (business) model
      1. _application_ application logic (handlers or services, listeners, stateful objects)
      1. _domain_ - domain model, building blocks
      1. _infrastructure_ - technical stuff, repository implementation, technical listeners

### Technical resources ###
Folder http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/#main%2Fresources contains:
  * JDBC and logger properties
  * application service level (no web app stuff) Spring configuration

Folder http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/#main%2Fwebapp contains web application based on Spring Web MVC. Other client implementations will be provided in the next milestone (Android, Ajax, Remoting, WebService)

## Just one Maven Artifact? ##
As You can see project logically contains:
  * common DDD API
  * common DDD implementation
  * common CqRS API
  * common CqRS implementation
  * product modules API
  * product modules and cross module Sagas implementation

All this stuff is stored in one Eclipse Project - one Maven Artifact.

This decision is a trade-off made in sake of simplicity. If You are proficient Maven user, You probably split code into separated artifacts in one hour. But those of You, who are not familiar with Maven should focus on the essence of this project - sample code.


---


---


# Domain Description #
Sample is illustrated in the context of simplified fraction of the ERP system. We have chosen some ERP domains, because they are relatively well known. Everybody has some intuition about Clients, Orders, Products etc. so We don't have to stuck explaining how different domain works (financial, medical etc.)

## Bounded Contexts ##
There are three Bounded Context, that are equals to the ERP modules.
This approach:
  * helps to model that is relevant to the specific context
  * supports focus on specific Domain Expert knowledge area
  * avoids creating one big corporate model of Great Unification theory that is de facto meaningless.

Modules are separated at the level of [pl.com.bottega.erp](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Ferp) package.

### Sales ###
It's about ordering products, calculating rebates, invoicing and analyzing sale trends.
This module contains most sample code. Other modules just illustrates Event communication and are Saga participants.
### CRM ###
It's about client relationship management. Client in this module is modeled as a different artifact (another Bounded Context) than in Sales module.
### Shipping ###
It's about charging and shipping orders.

## Shared Kernel ##
Shared Kernel contains model common to all Bounded Contexts - [pl.com.bottega.ddd.domain.sharedcernel](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Fddd%2Fdomain%2Fsharedcernel) package.

## WARNING ##
ERP model is simplified in sake of educational purpose. If You are developing such a system refer to [Modeling Resources](Introduction#Modeling_resources.md), in particular:
  * Party Archetype
  * CRM Archetype
  * Product Archetype

---


---


# Application Architecture #
Looking at the [Visual Map](DomainDrivenDesignBusinessDeveloperMentalModel#Architecture_and_design_visualization_using_Scalable_Map.md) should be helpful at this moment.
## Two stacks of Layers ##
Each module is build regarding the same architecture style.
Three tier architecture:
  * Client Tier
  * Application Server Tier
  * DB Tier

Application Server Tier is designed in layered fashion.
_Remember that Tier!=Layer. Tier is a concept of separation from the technical point of view (clients, servers, databases). Layer is a concept of the logical code separation (layers at the application server)._

Layers are grouped in two stacks.
One stack is specialized in domain modeling and handling commands, while second is specialized in fast data serving and handling queries.

CqRS convention is to call one stack: Write Model  and second: Read Model. But we prefer call it **Business** and **Presentation** models because in Business model You both write and read data (read data are just not visually presented, used only in processing).

There are Clients on the top of both stacks. Right now only WebApp client is implemented.
Client Tier code is located here:
http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fwebapp

Server Tier code (Sales Module) is located here:
http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Ferp%2Fsales%2Fwebui

Looking at the Server Tier, let's focus on the [Sales Module](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Ferp%2Fsales).

application logic (handlers or services, listeners, stateful objects)
domain - domain model, building blocks
infrastructure - technical stuff, repository implementation, technical listeners

### Presentation Stack ###
Presentation (read) model is simple. It's just a thin layer specialized in reading data that need to to presented somehow.

[pl.com.bottega.erp.sales.presentation](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Ferp%2Fsales%2Fpresentation) Package contains sample:
  * Queries - Search Criteria objects that specify querying parameters.
Notice that [ProductSearchCriteria](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/presentation/ProductSearchCriteria.java) class is designed in Fluent Interface style. From the client perspective criteria hides domain model properties (name, description) under abstract text.
  * Data Transfer Objects - carries data. They are created per Use Case/User Story or per View. In complex systems they are much better transferring classes than JPA entities because they don't break encapsulation on the model level and contain optimized data that are needed in particular context.
  * Finders - simple services that perform search based on Search Criteria.

In [II](PART.md) we will discuss:
  * Technical details of designing this Stack
  * Techniques of optimization (Special JPA Syntax, pure SQL, Updating by Events, Materialized Views)

### Business Stack ###
Business stack layers are described in detail in the following sections:
[Application Layer](DomainDrivenDesignBusinessDeveloperMentalModel#Application_Layer.md)
[Domain Layer](DomainDrivenDesignBusinessDeveloperMentalModel#Domain_Layer.md)
[Infrastructure](CqRS_Part_II.md)

---

## Expressing Design using Custom Annotations ##
While reading the sample code You should notice multiple custom annotations, used to mark
  * [DDD Building Blocks](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Fddd%2Fdomain%2Fannotations)
  * [Application Layer objects](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Fddd%2Fapplication%2Fannotation)
  * [Sagas](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Fddd%2Fsagas)
  * [Events](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Fddd%2Finfrastructure%2Fevents)
  * [Commands](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/cqrs/command/Command.java)

Annotations are used to achieve three goals:
  * Tag code - visual analyzer (similar to "scalable map") will be reading them and presenting design graph
  * Some custom annotations are annotated by Spring's annotation. In effect classes annotated by custom annotations gain Spring features (transactions and security). Using custom annotations decouples and globalizes Spring features.
  * CqRS engine (Event, Sagas) rely on some custom annotations.

All custom annotations will be described in detail in later sections of this document.


---

## Inversion of Control techniques ##
Application architecture of this sample rely on Inversion of Control principle - famous Hollywood rule: "Don't call Us, we will call You".

It is important to notice that Spring can be easily removed and all of its features can be implemented on Your own.

**Spring features are encapsulated by custom annotations and interfaces.
Implementing IoC features on Your own is described in PART II.**

### Dependency Injection ###
DI is basic IoC technique. In this project DI is used only at the application level:
  * Injecting dependencies (Repositories, Services) to Command Handlers
  * Injecting dependencies (Repositories, Services) to Application Services -  If You want to use classic services approach without commands
  * Injecting dependencies (JDBC connection, Entity Manager) to repositories
  * Injecting dependencies (JDBC connection, Entity Manager) to Finders (presentation stack)

Sample injection:
```java

@CommandHandlerAnnotation
public class ChangeCustomerStatusCommandHandler implements CommandHandler<ChangeCustomerStatusCommand, Void>{
@Inject
private CustomerRepository customerRepository;
//..
}
```

We decided not to inject dependencies to Aggregates automatically. It could be done using Spring's @Configurable, but we wanted to keep example magic clear. This trade-off makes rewriting our Leaven to different platform easy (Seam, EJB, etc.)

Manual Aggregate injection is described in [Aggregate Dependency Injection](DomainDrivenDesignBusinessDeveloperMentalModel#Aggregate_Dependency_Injection.md) chapter.

### Events ###
Events are stronger than DI technique of inverting control, because they allow to:
  * abstract number of dependencies (many listeners)
  * decouple from time arrow (asynchronous)

We have decided to implement own [Event Engine](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Fddd%2Finfrastructure%2Fevents), because Spring's solution forces You to use Spring specific API.

Event engine contains:
  * two [annotations](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Fddd%2Finfrastructure%2Fevents)
    * EventListener - for listener methods
    * EventListeners - for class containing listener methods - adds Spring Comonent features (DI)
  * just two [impl classes](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Fddd%2Finfrastructure%2Fevents%2Fimpl)

So Events Engine can be easily enhanced to support asynchronous processing secured by persistent queue.

### Aspect Oriented Programming ###
AOP is the strongest IoC technique used in the sample to provide:
  * transactions
  * security

You can use Command style or classic Application Service style. Simply use [ApplicationService](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/ddd/application/annotation/ApplicationService.java) or [CommandHandler](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/cqrs/command/handler/CommandHandlerAnnotation.java) annotation - they both introduce Spring Security and Transaction Management features.

But implementing Your own "AOP" **is trivial** when using Command style. Simply edit [RunEnvironment](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/cqrs/command/RunEnvironment.java) class and add Your own features before and after running a handler.



---


---


# Application Layer #

Application logic orchestrates scenario for "domain actors", who live in lower layer. Application logic also takes care for security and transactions. Application logic can fire application events.

We will discuss all sample Application Layer artifacts focusing on the [Sales Module](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Ferp%2Fsales%2Fapplication).

## Commands/Services ##
Leaven supports two styles of implementing Application Logic.
Commands and App Services are technically totally different, but mentally can be considered as equivalent.
Commands are powerful, because it's relatively easy to introduce own "AOP" programming model in clean and no-magic way. Using Spring, you can achieve the same feature, but in a transparent, magic way. So if magic scares You or You like to have full control - choose Commands style.

### Application Services ###
At first let's look at the "service" style approach.
[PurchaseApplicationService](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/application/services/PurchaseApplicationService.java)

```java

@ApplicationService
public class PurchaseApplicationService {
@Inject
private OrderRepository orderRepository;
//...
}
```

Characteristic:
  * Repository and Factory injection
  * ApplicationEvent Publisher injection
  * SystemUser - stateful (session scoped) application object injection
  * transactions and security features supported by Spring using custom [ApplicationService](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/ddd/application/annotation/ApplicationService.java) annotation
  * Service methods realizes parts of User Story/Use Case

Typical method implementation:
  * Load Aggregates from Repositories (or create in Factories)
  * Perform business scenario (sequence of aggregate calls)
  * Eventually create Application Event
  * Save Aggregates in Repository

### Commands and CommandHandlers ###
The "command" style is an alternative to the class "service" style.
You always implement a pair - command and it's handler, for example:
  * [SubmitOrderCommand](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/application/commands/SubmitOrderCommand.java)
  * [SubmitOrderCommandHandler](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/application/commands/handlers/SubmitOrderCommandHandler.java)

or
  * [CreateOrderCommand](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/application/commands/CreateOrderCommand.java)
  * [CreateOrderCommandHandler](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/application/commands/handlers/CreateOrderCommandHandler.java)


Conceptually two classes can be considered as a [Command Design Pattern](http://en.wikipedia.org/wiki/Command_pattern). In our case, commands are sent from different Tier, so they can not contain any logic due the security and coupling reason. Therefore Command Design pattern is split into:
  * Command that just caries input data (kind of DTO)
  * CommandHandler that contains logic.

#### Command ####
Creating command is simple:
  * usually implement Serializable tagging interface, because command may be send form remote client
  * add fields that holds command parameters
  * You **can** add [@Command](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/cqrs/command/Command.java) annotation if You want to express following intentions:
    * Client is not interested in immediate result - command can be handled in an asynchronous way. This is just a suggestion for a Server - it can support asynchronous invocations. Use this mode when You want to optimize response time
    * Command is unique and sending it again is considered a mistake (e.g. nervous user clicking many times). Server stores some amount of commands (annotated as unique) that were ran, therefore it can optimize anti-flood mechanism. **This feature is just in sake of optimization. You must implement unique checking on the domain level** (ex. Order can not be accepted twice). Unique mechanism rely on equals method.
    * Unique command can specify how long it should be considered as unique.

```java

@Command
public class CreateOrderCommand {

private final List<Long> productIds;

public CreateOrderCommand(List<Long> productIds) {
this.productIds = productIds;
}

public List<Long> getProductIds() {
return productIds;
}
}
```

#### CommandHandler ####
Creating a handler for a command is also pretty straightforward.
  * implement [CommandHandler](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/cqrs/command/handler/CommandHandler.java) interface. This interface is generic, so You must specify:
    * class of the Command that is handled - CqRS engine matches Command and Handlers by this parameter
    * result type - handling command may return something to the caller. Naturally, handler that handle asynchronous commands should return java.lang.Void (not the void keyword).
  * You **can** annotate handler with [CommandHandlerAnnotation](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/cqrs/command/handler/CommandHandlerAnnotation.java) if You wan to rely on automatic  Command2Handler matching. This annotation also introduces Spring features (Dependency Injection, Security and Transactions) to the handler, so You don't need to worry about them.

Now implement logic just the same way as in classic [Application Service](DomainDrivenDesignBusinessDeveloperMentalModel#Application_Services.md): inject required dependencies and orchestrate part of the Use Case/user Story scenario in handle method based on parameters from the Command. You can also fire an Application Event.

```java

@CommandHandlerAnnotation
public class CreateOrderCommandHandler implements CommandHandler<CreateOrderCommand, Long> {

@Inject
private OrderFactory orderFactory;

@Inject
private OrderRepository orderRepository;

//..
```


---

## Application Event ##
Application Events are characteristic to the specific Use Case/User Story. They model application (not domain) behavior.

To fire such event, simply:
  * inject [ApplicationEventPublisher](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/ddd/application/ApplicationEventPublisher.java) to the Command Handler or Application Service
  * create event using any class
    * event usually carries some valuable information
    * event class **can** be annotated with [ApplicationEvent](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/ddd/application/annotation/ApplicationEvent.java) annotations - consider this as a meta-data that will be analyzed by some cools tools we plan to publish

```java

@CommandHandlerAnnotation
public class CreateOrderCommandHandler implements CommandHandler<CreateOrderCommand, Long> {

@Inject
private ApplicationEventPublisher applicationEventPublisher;

//..

@Override
public Long handle(CreateOrderCommand command) {
//..
applicationEventPublisher.publish(new ProductAddedToOrderEvent(productId, systemUser.getUserId(), 1));
//..
}
}
```

In our example application event [ProductAddedToOrderEvent](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/application/events/ProductAddedToOrderEvent.java) models important fact of the application life cycle. Let's assume that in this sample we don't want to model this act on the domain model level.

```java

@ApplicationEvent
@SuppressWarnings("serial")
public class ProductAddedToOrderEvent implements Serializable{

private Long productid;

private Long clientId;

private int quantity;

public ProductAddedToOrderEvent(Long productid, Long clientId, int quantity) {
this.productid = productid;
this.clientId = clientId;
this.quantity = quantity;
}

//just getters
}
```

Then, [one of the listeners](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/presentation/listeners/ProductEventsListener.java) stores data about this fact in "spy model" used by the marketing division to do something evil.

```java

@EventListeners
public class ProductEventsListener {

@PersistenceContext
private EntityManager entityManager;

@EventListener(aynchronous=true)
public void handle(ProductAddedToOrderEvent event){
entityManager.persist(new OrderedProduct(event.getProductid(), event.getClientId(), event.getQuantity(), new Date()));
}
}
```

---

## Listeners - Bounded Context and Context Mapper ##
Now we will discuss [Application Level Listeners](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Ferp%2Fsales%2Fapplication%2Flisteners).

One way to integrate modules (which are equal to Bounded Context in this Sample) is to call Application API of the another module.
This approach introduces coupling between Modules.

If You want to reduce coupling, You can introduce events - one of the IoC techniques.
In that case:
  * one module fires Event (rather Domain Event, but Application Event is also possible)
  * other modules can listen to this event - each module can register many listeners per event.

Introducing events results in:
  * loose coupling (which is always good)
  * possibility to react in an asynchronous way
  * potability to introduce plugin oriented architecture - if You want to create flexible platform

### Bounded Context ###
Consider class example by Udi Dahan.
There is a [CRM Module](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Ferp%2Fcrm) which contains a [Customer Aggregate](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/crm/domain/Customer.java).

CRM module is about managing clients, this Bounded Context is completely different than [sales Module](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Ferp%2Fsales).

CRM Client can have its status changed. Requirement is:
> "when client status is changed to VIP, then calculate x% of the rebate to all not-approved-yet orders"
Orders and Rebates are Sales Bounded Context concepts.
If we would perform this operation in the CRM module than we would break context boundary. CRM module would know about Sales details. Probably everyone have worked on a systems we such a module-spaghetti was developed.

### Listener ###

In this sample, we want to avoid this kind of problems. Therefore CRM Client does it's own logic, than fires Event [CustomerStatusChangedEvent](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/crm/domain/events/CustomerStatusChangedEvent.java). **This is just a statement about the fact that took place**.

```java

@Entity
@DomainAggregateRoot
public class Customer extends BaseAggregateRoot{

public enum CustomerStatus{
STANDARD, VIP, PLATINUM
}

@Enumerated(EnumType.STRING)
private CustomerStatus status;

public void changeStatus(CustomerStatus status){
if (status.equals(this.status))
return;

this.status = status;

eventPubslisher.publish(new CustomerStatusChangedEvent(getId(), status));
}
}
```

System can react in many ways on that statement. For example, Sales module can register [CustomerStatusChangedListener](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/application/listeners/CustomerStatusChangedListener.java) to perform **additional business logic**.

```java

@EventListeners
public class CustomerStatusChangedListener{

@EventListener(aynchronous=true)
public void handle(CustomerStatusChangedEvent event) {
if (event.getStatus() == CustomerStatus.VIP){
calculateReabteForAllDraftOrders(event.getCustomerId(), 10);
}
}
//..
}
```

#### Indicative mode ####
Notice that Events are expressed in an indicative model. Something has happen and we can not reject this fact. We can only perform additional processing.

This is opposite to the Commands, which are expressed in imperative mode and we can always reject a command.

#### Asynchronous Event Processing ####
Event listeners are just methods annotated with [EventListener](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/ddd/infrastructure/events/EventListener.java)

You can specify asynchronous attribute to express an intention so that this logic can be calculated later.
**Always think if additional logic must be performed immediately?** Maybe not, than You can optimize request processing time.

```java

@EventListeners
public class OrderSubmittedListener {

@EventListener(aynchronous=true)
public void handle(OrderSubmittedEvent event) {
System.out.println("Sending mail aboud order: " + event.getOrderId());
}
}
```

Notice that in case of events, listener is the artifact that express asynchronous intention. It is opposite to the Commands, where commands express this intention and handler has nothing to say in this matter.

#### Application Listener as a Context Mapper and Anti-corruption Layer ####
You can consider Application Event Listeners as a Context Mappers, because the map concepts from different Bounded Contexts.
If Your Listeners are listening to the events from the legacy systems, they can be also considered as Anti-corruption Layer artifacts.

### Implementation ###
Application Level Event Listener can be implemented in two styles:
  * Perform logic using injected dependencies
  * Create a Command and run it via injected [Run Environment](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/cqrs/command/RunEnvironment.java)


---

## Sagas ##

Events are very powerful IoC technique, but has some limitations. You can use Event when dealing with dependent facts that took place in Your system. You mentally treat each Event and it's Listeners as an independent objects with no deterministic processing sequence.

What if sequence order matters? What if some process need to be "pushed" by many events?

You need to introduce a Saga mental model to orchestrate complex processes that contains multi-events sequence.

Sagas are just mentioned here to make layer description complete. Detailed description of Saga model is presented at the end of this document.

---

## Stateful Application Objects ##
Sometimes You may need to model Application State.

Notice that:
  * state of the Domain Model should be persisted
  * state of the Client Tier is a different aspect

We are talking about Application Layer state. If You need to introduce state, think if You are sure about that. Next, think about persisting this model or using global, distributed cache. But if You decide to use Session as a storage mechanism, then You have easy support. Just annotate classes with [ApplicationStatefullComponent](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/ddd/application/annotation/ApplicationStatefullComponent.java) and You are done.

Spring will store objects of this class in Http Session. Spring also lets You inject this objects into Singleton Scoped objects using magic trick based on cunning proxy. For example, our CommandHandlers or ApplicationServices are stateless singletons in sake of memory optimization.

Sample contains following Stateful Application Components:
  * [SystemUser](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/ddd/application/SystemUser.java) - common object representing user working with the system. You can inject this objects into CommandHandlers or ApplicationServices, if You need to know who is performing operation. SystemUser is a application concept that abstract form domain entity. This object contains just ID of the domain entity, therefore concept of domain user does not leak into the whole code.
  * [ClientBasket](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/application/ClientBasket.java) is a metaphor of a shopping cart. In this sample, we assume that Shopping cart is not a domain artifact, nor client specific feature. Assumption is that this metaphor models application concept (but in some systems it may be a very important domain concept, or maybe just some feature of some minor client app).

```java

@ApplicationStatefullComponent
public class ClientBasket implements Serializable{

private List<Long> productIds = new ArrayList<Long>();

@EventListener
public void clearBasketOnSuccessfulOrderCreation(OrderCreatedEvent event) {
clearBasket();
}

public void clearBasket() {
productIds.clear();
}

public void addProduct(Long productId) {
productIds.add(productId);
}

public List<Long> getProductIds() {
return Collections.unmodifiableList(productIds);
}
}
```


---


---


# Domain Layer #
Now let's focus on the Sales Bounded Context and its [Domain Layer](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Ferp%2Fsales%2Fdomain).

We will go through all building blocks discussing design decisions and technical solutions.
## Entity ##
[Product](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/domain/Product.java) is a sample entity.

Worth to notice:
  * In this case, we don't need setters (technically JPA can work on private fields via reflection which is even faster that calling methods)
  * In general, entity contains business methods - but on the other hand do not spread all business code on entities. [Order Line](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/domain/OrderLine.java) is a sample Entity that performs business logic - calculates cost based on rebate policy
  * [DomainEntity](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/ddd/domain/annotations/DomainEntity.java) is just a tagging annotation that will be used by cool visualization tools we plan to publish

### Base class ###
You **can** consider extending [BaseEntity](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/ddd/domain/BaseEntity.java).
**If You don't want to couple Your model with Leaven classes, then simply rename and repackage this class**.

Remember that inheritance is not just about pulling code up in the hierarchy! BaseEntity class is used as a polymorphic interface (in the meaning of "api", not java keyword) in the repositories, so we are in tune with [Liskov Substitution Principle](http://en.wikipedia.org/wiki/Liskov_substitution_principle). Therefore inheritance is justified.

Features of BaseEntity:
  * Optimistic Concurrency - @Version annotation
  * Store last modification date
  * Logic delete operation - change flag. You can use Hibernate Filters to automatically filter archived JPA entities
  * equals and hashcode

---

## Aggregate ##
Sample Sales Bounded Context contains three Aggregates:
  * [Order](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/domain/Order.java)
  * [Invoice](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/domain/Invoice.java)

```java

@Entity
@Table(name = "Orders")
// table name ORDER is not valid in HSQL dialect
@DomainAggregateRoot
public class Order extends BaseAggregateRoot {
//..
}
```
### Boundary ###
**The most critical factor of DDD modeling success is designing proper boundaries of Aggregates**.

Sample Order Aggregate is quite large. It contains OrderLines for example. Large aggregate leads to problematic situations in different aspects:
  * "god classes" are hard to maintain and to test
  * large responsibility may lead to situations then many concurrent request need to modify the same Aggreagte instance.

Therefore in real system You would probably end up by modeling OrderLine as an Aggregate that hold reference to the Orderheader.
But in this sample Order model we assume that OrderLines are just inner details of the Order.

Genera, very basic rule of modeling Aggregate boundaries is to avoid traditional mental attitude: "lets find big bags for attributes". Instead lets gather all attributes (without bags for the beginning), find out which Use Cases modify which subsets of those attributes, than we have natural Aggregates boundaries.

Aggregate "shape" that is oriented on modification makes Aggregate a consistent unit of business change - just as DDD blue book preaches:)


### Encapsulation ###
Notice that:
  * Aggregates does not allow to modify all their properties via setters. It may have sense in some cases only.
  * Aggregate state is changed via business methods
  * Some business operations may be not valid in some state (ex. Order.submit()) - therefore they throw domain [errors](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Ferp%2Fsales%2Fdomain%2Ferrors).
  * Not all getters make sense.

#### Inner state protection ####
Notice that Invoice Aggregate method: getItems returns list of [InvoiceLine](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/domain/InvoiceLine.java) entities. But this list is also unidentifiable in order to protect Aggregate from being changed by outer code.
```java

@DomainAggregateRoot
@Entity
public class Invoice extends BaseAggregateRoot {
//..
@OneToMany(cascade = CascadeType.ALL)
private List<InvoiceLine> items;

public List<InvoiceLine> getItems() {
return Collections.unmodifiableList(items);
}
}
```
#### Inner model protection ####
Notice that Order Aggregate method: addProduct takes Product Entity as an argument, then wraps it with OrderLine. OrderLine is Aggregate's inner implementation detail.

```java

public void addProduct(Product product, int quantity) {
checkIfDraft();

OrderLine line = find(product);

if (line == null) {
items.add(new OrderLine(product, quantity, rebatePolicy));
}
else {
line.increaseQuantity(quantity, rebatePolicy);
}

recalculate();
}
```
### Outside Projections using Value Objects ###
In this sample we assume that Order model is unstable - we expect changes in near future. Therefore we want to reduce catastrophe area to Aggregate itself. Domain Expert is not sure about [Order Line](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/domain/OrderLine.java) model.

Therefore [OrderedProduct](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/domain/OrderedProduct.java) Value Object is introduced as an "adapter" that adopts inner aggregate model to the outer world.

Notice that method Order.getOrderedProducts transforms inner (unstable) aggregate model to the "outer interface" based on OrderedProduct Value Objects. Method return unidentifiable list because changes to this list does not make any sense.
```java

public List<OrderedProduct> getOrderedProducts() {
List<OrderedProduct> result = new ArrayList<OrderedProduct>(items.size());

for (OrderLine line : items) {
result.add(new OrderedProduct(line.getProduct().getId(), line.getProduct().getName(), line.getQuantity(),
line.getEffectiveCost(), line.getRegularCost()));
}

return Collections.unmodifiableList(result);
}
```
### JPA aspects ###
Aggregate is an entity from the JPA perspective. JPA can operate on private fields, so getters and setters are not necessary (using them makes sense only sometimes).

#### Lazy Loading ####
You may consider Eager loading of some aggregated objects. Some aggregated objects are always needed.

On the other hand, Lazy loading does not hurt with <i>n+1 select problem</i>, because in Business Model we usually load just couple on aggregates, so n is relatively small.

```java

@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
private List<OrderLine> items;
```

#### Cascade Operations ####
Cascade operations make sense to most aggregated objects, so You must always consider adding this attribute.

### Base classes ###
You **may** consider extending [BaseAggregateRoot](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/ddd/domain/BaseAggregateRoot.java) class.

**If You don't want to couple Your model with Leaven classes, then simply rename and repackage this class**.

BaseAggregateRoot is based on BaseEntity - it adds common future, an injected [DomainEventPublisher](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/ddd/domain/DomainEventPublisher.java).

### Aggregate Dependency Injection ###
Aggregates are the main work-horses of the domain. Therefore, they need to delegate some work to dependencies (Domain Services, Policies, Specifications or DomainEventPublisher).

Aggregates **should not** create dependencies by their own. This simply kills testability.

We decided to perform injection manually in Factories and Repositories (described later). Factories and Repostories are the only places where Aggregate can be born, You should not call an Aggregate constructor. Therefore, Factories and Repositories performs injection before they return an Aggregate.

Notice how[BaseAggregateRoot](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/ddd/domain/BaseAggregateRoot.java) class supports injection. setEventPubslisher can be called only once - in Repo or Factory. Calling it inside Application Layer will fail.

```java

@MappedSuperclass
public abstract class BaseAggregateRoot extends BaseEntity {

@Transient
protected DomainEventPublisher eventPubslisher;

public void setEventPubslisher(DomainEventPublisher domainEventPubslisher) {
if (this.eventPubslisher != null)
throw new IllegalStateException("Publisher is already set! Probably You have logical error in code");
this.eventPubslisher = domainEventPubslisher;
}
}
```

There are other ways to do this:
  * No setter, just custom injection annotation checked in Repo and factory - a little dirty in our opinion
  * @Configurable annotation - we do not want do use any magic in sake of architecture style portability. Besides, sometime we need to calculate (perform some business logic) to determine a Policy for example
  * Pass needed dependencies as Aggregate methods' parameters. Sometimes can be artificial, but sometimes makes sense. For example, when dependency is needed only in one method of the Aggregate. But on the other hand - as we discuss later - very specific and used once Aggregate Methods should become a Domain Service.


### Possible Modeling Extension: introducing State Design Pattern ###
State is a powerful technique for dealing with certain class of problems.

So we may want to use State when:
  * object can be in many states (in business systems state may be associated with some domain statuses)
  * object can perform may operations
  * way of performing operations depends on the current state
All above can (and in most cases probably should) be implemented using switch - yes, trivial switch. That's the easiest way.

...unless we expect following positive forces to emerge:
  * we expect introducing new states in future - so we open our design for expansion
  * we do not expect introducing many new operations - because maintaining state can be painful


Sample scenario when State Design Pattern can be introduced to implement Aggregate as a State Machine:
  * we have a DeliveryPackage aggregate
  * aggregate has some business responsibility (which is rather stable, we do not expect many new methods)
  * DeliveryPackage can be in many States - and for example we expect that the set of possible states varies, depending on deployment (our product/system is deployed t many clients, where each client has different policies about cargo statuses/states; we want to maintain common code base)


You may consider little change in the pattern design. In general State is somehow "invasive", it "pollutes" Aggregate. You can  consider designing what I call a cocoon. So You have Aggregate with basic business responsibilities. You create a cocoon that wraps Aggregate and implements all State Machine mechanic. This Mechanics  delegates to concrete States, concrete States delegates to aggregate.

So every time You want to work with Aggregate You do sth like this:

```java

public Void handle(SomeCommand command){
Aggregate a = repo.load(command.getAggregateId());
AggregateStateMachine asm = new AggregateStateMachine(a); //at this time machine sets current state by checking some statuses of the aggregate (statuses must be accessible)
asm.doStrh();//delegates to current state, stat to aggregate, eventually changes state
repo.persist(a)
}
```

This is just a suggestion for a possible solution for decoupling.


---

## Value Objects ##
[Shared Kernel](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Fddd%2Fdomain%2Fsharedcernel) contains two examples of Value Objects: Money and Percentage. Both encapsulates representation of the value they protect.
Therefore You can change money calculations to ultra-fast integers where fractions are handled with bytes shifting:)
Moreover Money encapsulates concept of the Currency. You can also add concept of currency ration date.
### Immutability ###
VOs are immutable, so  You can pass them to another Aggregate without worrying about Side Effects. For example, prices for OrderedProducts are used by InvoicingService to generate Invoice. Life is simple with immutability.

```java

public Money multiplyBy(BigDecimal multiplier) {
return new Money(value.multiply(multiplier), currencyCode);
}
```

### Aggregate Inner State Projection ###
[OrderedProduct](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/domain/OrderedProduct.java) Value Object is introduced as an "adapter" that adopts inner aggregate model to the outer world, because we assume that OrderLine model is unstable and will change in the future.


### Make explicit what is implicit ###
VOs are little classes that make a difference.
Without VOs Your code suffers from Primitive Obsession Code Smell.
### Increase expression power ###
Value objects are small artifacts, but extremely increase power of domain expression. They enrich Ubiquitous Languages and help understand details of the model.
### JPA aspects ###
Using VOs is very "cheap" from the perspective of JPA. You don't need any additional tables. Simple map them as @Embededable classes and use in Entitues/Aggregates ad @Embeded.

If You have more than one embedded VO in Entity, You must use additional annotations:

```java

@Embedded
@AttributeOverrides({
@AttributeOverride(name = "value", column = @Column(name = "net_value")),
@AttributeOverride(name = "currencyCode", column = @Column(name = "net_currencyCode")) })
private Money net;

@Embedded
@AttributeOverrides({
@AttributeOverride(name = "value", column = @Column(name = "gros_value")),
@AttributeOverride(name = "currencyCode", column = @Column(name = "gros_currencyCode")) })
private Money gros;
```

Alternatively, You can provide special Naming Strategy in Hibenrate.


---

## Factory ##
Sample contains two factories: [OrderFactory](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/domain/OrderFactory.java) and InvoiceFactory.

```java

@DomainFactory
public class OrderFactory {

@Inject
private RebatePolicyFactory rebatePolicyFactory;
@Inject
private InjectorHelper injector;

public Order crateOrder(Client client) throws OrderCreationException {
checkIfclientCanPerformPurchase(client);

Order order = new Order(client, Money.ZERO, OrderStatus.DRAFT);
injector.injectDependencies(order);

RebatePolicy rebatePolicy = rebatePolicyFactory.createRebatePolicy();
order.setRebatePolicy(rebatePolicy);

addGratis(order, client);

return order;
}

//...
}
```

Factories are annotated with [DomainFactory](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/ddd/domain/annotations/DomainFactory.java) annotation. This annotation introduces Spring features - factory becomes a Spring bean, therefore You can inject dependencies into the factory and factory can be injected into CommandHandlers.

Now we will discuss responsibility scope of DDD Factories.

### Validation ###
Factory is responsible for providing valid Aggregates. Invalid Aggregate can not exist in memory. OrderFactory is responsible for validating
"components" that build Order - in this sample it checks Client and eventually throws [Domain Error](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/domain/errors/OrderCreationException.java).
===Injecting Factories into Command Handlers/Application Services
Factories are need to create Aggregates, therefore You will inject them into Command handlers or other places. It is possible because factory is a Spring Bean
```java

@CommandHandlerAnnotation
public class CreateOrderCommandHandler implements CommandHandler<CreateOrderCommand, Long> {

@Inject
private OrderFactory orderFactory;

//..
}
```
### Injecting into Factory ###
Factory may also need some dependencies,  for example Repositories to be able to check something.
```java

@DomainFactory
public class OrderFactory {

@Inject
private RebatePolicyFactory rebatePolicyFactory;
@Inject
private InjectorHelper injector;
```
For example, OrderFactory adds gratis products to newly created order. Knowledge about gratis comes from DomainService or Repository.

Another example is RebatePolictFactory in OrderFactory to determine rebate.

### Dependency Injection Responsibility ###
Aggregates needs dependencies. We decided that Factories and Repositories are responsible for providing them.

In sample code OrderRepository is responsible for injecting RebatePolicy.

```java

//..
public Order crateOrder(Client client) throws OrderCreationException {
//..
Order order = new Order(client, Money.ZERO, OrderStatus.DRAFT);
injector.injectDependencies(order);

RebatePolicy rebatePolicy = rebatePolicyFactory.createRebatePolicy();
order.setRebatePolicy(rebatePolicy);

//..

return order;
}

//...
}
```

There are some very common dependencies, like DomainEventsPublisher, which are injected by special [InjectorHelper](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/ddd/domain/support/InjectorHelper.java).

### Part of business logic (Assembling objects) ###

Factory is responsible for some domain logic:
  * calculates dependencies - Rebate Policy
  * add gratis product
Therefore it is a First Class Citizen in DDD Building Blocks World.

Read great [post](http://misko.hevery.com/2008/08/21/where-have-all-the-singletons-gone) by Miško Hevery that explains three faces of the coupling:
  * collaboration
  * construction
  * call

Factories lighten application logic layer by taking construction coupling.


### Increase testability ###

If You construct Aggregate dependencies in factories and then inject them to the Aggregate, You gain another feature - higher testability. Thanks to factories, Aggregates don't have to use new operator (except inner data structures). This means You can easily mock dependencies when unit testing.


---

## Repository ##

Project contains few sample Repositories, which are implemented in the [Infrastructure Layer](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Ferp%2Fsales%2Finfrastructure%2Frepositories%2Fjpa).

Repository is an abstraction of the Aggregate/Entity persistence.
```java

@DomainRepository
public interface OrderRepository {

public void persist(Order order);

public Order save(Order order);

public Order load(Long orderId);

}
```
Therefore Repository has domain interface and technical implementation.
```java

@DomainRepositoryImpl
public class JpaOrderRepository extends GenericJpaRepositoryForBaseEntity<Order> implements OrderRepository {
//..
}
```


In general, Aggregate parts may origin form different sources.


Repository manages aggregate, which means it must load and save. Don't pollute repositories with dozens of search method - that's the responsibility of the second stack - a Presentation Stack.

Naturally, repository will contain business search methods, like: find draft Orders of the given Client, because we want to apply rebate for them.

### Responsibilities ###

In general, Repository is also responsible for stuff that Factory does: a Dependency Injection.

### Repository is not a DAO ###
Repo is not just about CRUD. We wanted to break mental association with Data Access Object pattern - which is considered as a CRUD operations container.

Reasons to expose DAO difference:
Repo load vs DAO read:
  * Repo may perform additional logic: injection, assembling based on  some rules
  * Repo is not just about reading data - in general Repo may read chunks of domain aggregate from different sources (not only DB) and assemble them. Rare but You may see system where Repo talks do few DAOs
  * Using Event Sourcing we are actually reading events at than loading them to the Aggregate - so aggregate is loaded from Events.

Repo perists vs DAO create
  * create is concept from "DB-oriented" system, where created means "stored in db".
  * in DDD Aggregate begins it's life in a Factory (which creates it), and persisting is a step of the process - db is just a technical detail

Actually it can be considered having just one method: save which creates or updates aggregate - just like Hiberbate's saveorupdate.

### Base Classes ###

There are base classes for both Entity and Aggregate repositories in [common DDD goodies](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Fddd%2Finfrastructure%2Frepo%2Fjpa).

Implementation is based on the famous [Generic DAO](http://www.ibm.com/developerworks/java/library/j-genericdao/index.html) by Per Mellqvist.


---

## Domain Services ##
Sometimes it is not natural to just stick some method to the Aggregate.
Moreover, we don't want to our Aggregates become a God Classes, like 8k lines of code monster that knows everything and knows everyone.

There is a [InvoicingService](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/domain/InvoicingService.java) sample Domain Service that handles creating Invoices.

```java

@DomainService
public class InvoicingService {

@Inject
private ProductRepository productRepository;

public Invoice issuance(Order order, TaxPolicy taxPolicy){
//..
}
}
```
We certainly don't want to add this feature to the Order Aggregate.

Domain Service is annotated by [DomainService](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/ddd/domain/annotations/DomainService.java) annotation that introduces Spring Bean features. Therefore our Domain Service can be injected and we can inject dependencies to the service (ex. Repositories).

As we can see, InvoicingService looks at the Order Aggregate through a ValueObject "projection" mentioned earlier.

### Responsibility Driven Design Transformers ###
Great book [Responsibility Driven Design](http://www.amazon.com/Object-Design-Roles-Responsibilities-Collaborations/dp/0201379430) by Rebecca Wirfs-Brock describes standard Building Blocks - more abstract than Evans' DDD BB.

InvoicingService can be considered as a Transformer role class, that transforms Orders to Invoices.

### When to introduce: simple rule of the thumb ###

Simple rule of the thumb for beginners. You probably wont miss much, if create Domain Services instead of Aggregate methods for operations that are used on one Use Case/user Story.

It it a procedural way of thinking, but keeps Aggregates coherent and loosely coupled.

That's just a rule for the newbies, so it's intuition that tells when to break it.


---

## Policy ##

Technically Policy is just an old, good [Strategy Design Pattern](http://en.wikipedia.org/wiki/Strategy_design_pattern).

In our sample project, we have two behaviors that vary a lot:
  * [Rebate Counting](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/domain/RebatePolicy.java) - depends on client, product, whole order, history etc
  * [Tax Counting](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/domain/TaxPolicy.java) - depends on client's country, seller country etc

### Make explicit what is implicit ###
We want to express those very important domain concepts in the model and treat them as First Class Citizens.

### Supple Design ###
Introducing policy opens design for further extension. It also encapsulates complex logic in separated places:
  * Rebate counting resides in specific [implementations](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Ferp%2Fsales%2Fdomain%2Fpolicies%2Frebate)
  * Rebate choosing based on logged user resides in [RebatePolicyFactory](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/domain/RebatePolicyFactory.java)
  * Calling rebate is managed by Order Aggregate and delegated to the Order Lines

### Decorating technique ###
Suppose we have VIP rebate and Winter rebate. What if VIP buys in winter? Suppose our system composes rebates. Should we introduce a third class to the model?

Sure not! All You need to do is to introduce a [Decorator Design Pattern](http://en.wikipedia.org/wiki/Decorator_design_pattern).

[Sample decorators](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Ferp%2Fsales%2Fdomain%2Fpolicies%2Frebate%2Fdecorators).

Sample Policy decoration in case System User has a VIP status:
```java

@DomainFactory
public class RebatePolicyFactory {

@Inject
private SystemUser systemUser;

public RebatePolicy createRebatePolicy() {
RebatePolicy rebatePolicy = new StandardRebate(10, 1);

if (isVip(systemUser)){
rebatePolicy = new VipRebate(rebatePolicy, new Money(1000.0), new Money(100));
}
return rebatePolicy;
}
//..
}
```

### Functional style ###

Strategy design pattern is semantically similar to the functional style programming. Concept is the same: an operation is as First Class Citizen. In OO language, FCC is an objects. So we simply pack operations into objects with only one method which is a signal to the object saying "execute yourself":

```java

@DomainPolicy
public interface RebatePolicy {

public Money calculateRebate(Product product, int quantity, Money regularCost);

}
```

### Injecting ###

In our example, Order Policy is injected to the order Aggregate by the Order Repository and Order Factory.

```java

@Entity
@Table(name = "Orders")
@DomainAggregateRoot
public class Order extends BaseAggregateRoot {

@Transient
private RebatePolicy rebatePolicy;

//..

public void setRebatePolicy(RebatePolicy rebatePolicy) {
if (this.rebatePolicy != null)
throw new IllegalStateException("Policy is already set! Probably You have logical error in code");
this.rebatePolicy = rebatePolicy;
}
}
```

Another example:
Tax Policy is passed to the Invoicing Service as a method parameter.

```java

@DomainService
public class InvoicingService {

@Inject
private ProductRepository productRepository;

public Invoice issuance(Order order, TaxPolicy taxPolicy){
//..
}
```

---


## Specification ##
[SubmitOrderCommandHandler](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/application/commands/handlers/SubmitOrderCommandHandler.java) attempts to submit Order and generate Invoice.

But there are complex constrains, that must be met before Order can be submitted. If you struggle to model constrains that are build by many criterion which are arranged in logic tree, then You may consider using a [Specification Pattern](http://en.wikipedia.org/wiki/Specification_pattern).

Sample contains [Order Specifications](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Ferp%2Fsales%2Fdomain%2Fspecification%2Forder) based on the [library generic specification](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Fddd%2Fdomain%2Fsharedcernel%2Fspecification).

### Supple Design in Runtime ###

Power of the specification is that it can be assembled late in the runtime based on the actual context.

Method SubmitOrderCommandHandler.generateSpecification can be refactored to the factory.

```java

private Specification<Order> generateSpecification(SystemUser systemUser) {
Specification<Order> specification = new ConjunctionSpecification<Order> (
new DestinationSpecification(Locale.CHINA),//.not(), // - do not send to China
new ItemsCountSpecification(100),//max 100 items
new DebtorSpecification()//not debts or max 1000  => debtors can buy for max 1000
.or(new TotalCostSpecification(new Money(1000.0))));


//vip can buy some nice stuff
if (! isVip(systemUser)){
specification = specification.and(new RestrictedProductsSpecification());
}

return specification;
}
```
### Further extension ###

If needed, You may extend Specification so that it collects errors.


---

## Domain Events ##
In one of the previous chapters, we have discussed Application Events. Technically Domain Events are the same. Difference is one the mental level - Domain Events model facts that takes place in domain model.

[BaseAggregateRoot](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/ddd/domain/BaseAggregateRoot.java) contains [DomainEventPublisher](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/ddd/domain/DomainEventPublisher.java) object which is injected via Repo or Factory.

Firing event:
  * inject DomainEventPublisher
  * create objects of any class that contains valuable information
  * call publish on publisher

Sample event publishing: submit method of [Order](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/domain/Order.java) Aggregate.

```java

public void submit() {
checkIfDraft();

status = OrderStatus.SUBMITTED;
submitDate = new Timestamp(System.currentTimeMillis());

eventPubslisher.publish(new OrderSubmittedEvent(getId()));
}
```

Listening events:
  * annotate listener method with [EventListener](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/ddd/infrastructure/events/EventListener.java)
    * Event listener may express intention that it is not interested in immediate execution - asynchronous attribute
  * annotate class containing listener method with [EventListeners](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/ddd/infrastructure/events/EventListeners.java) - that will register Spring bean and introduce Dependency Injection support

```java

@EventListeners
public class OrderSubmittedListener {

@EventListener(aynchronous=true)
public void handle(OrderSubmittedEvent event) {
System.out.println("Sending mail aboud order: " + event.getOrderId());
}
}
```

Now we will discuss motivations that promotes using Domain Events
### Decoupling ###
Events introduce Bounded Contexts decoupling. Context does not know about each other.

Events introduces Technical decoupling. Domain Building blocks does not know about technical listeners (ex. mail/sms senders, facebook publishers;)

Sample: [OrderSubmittedListener](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/infrastructure/events/listeners/domain/OrderSubmittedListener.java) that fakes sending mail.
### Optimization ###
Some listeners may be executed in asynchronous way which reduces request processing time.

Sample: [OrderSubmittedListener](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/infrastructure/events/listeners/domain/OrderSubmittedListener.java) that fakes sending mail can be annotated to work in asynchronous mode.
### Plugin oriented architecture ###
Listeners may introduce new features without the need to touch core code.
### Updating read model ###
Domain Events can be used to update [Presentation Model](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Ferp%2Fsales%2Fpresentation).

You can use this technique when presentation model is implemented as a separate, dedicated model (not Views). You can introduce many presentation model (each per specific case) that are updated by the same Event - just add dedicated updating Listener per model.

In [this sample](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/presentation/listeners/ProductEventsListener.java) Listener works on Application Event, but idea is the same.

### Event Sourcing ###
Domain Events may be stored as a behavioral model. Loading Aggregate loads all its events and executes them on a given aggregate.

Stored events can be "projected" to many "perspectives".
In this case Presentation model must be updated via updating listeners discussed in previous section.

### Complex Event Processing ###
You can introduce Event Oriented approach when system/organization need to process event streams and react on event patterns that emerge in time/quantity window.

---


---

# Saga #
Great Saga introduction by Udi Dahan: [Saga Persistence and Event-Driven Architectures](http://www.udidahan.com/2009/04/20/saga-persistence-and-event-driven-architectures/)
## Complex process orchestration ##
In general use Saga when You need to orchestrate process that is pushed by many events. Using Saga You can model partial state change of the bigger process - state change triggered by events.
## Sample Scenario ##
In the [Order example](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Ferp%2Fsales%2Fsaga), the saga pattern is used to mark the order as archived after the shipment delivery. Usage of saga eliminates direct call from Shipment Aggregate Root to Order Aggregate Root and prevents from execution the archive process in wrong order.

Also the simple usage of event architecture, would sometimes need to extend the information scope in the event (new handlers could expect Id different than shipment Id). Solution based on saga pattern eliminates changes in event information scope.
## Persistent multi-listener ##

Saga has persistent state and listen to many events.

[Saga Engine](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Fddd%2Finfrastructure%2Fsagas) is described in details in [II](PART.md). General idea is:
  * Engine catches event
  * Engines find Loaders that are interested in this event
  * Engine asks found Loaders to load/create persistent Mementos
  * Engine feeds relevant Sagas with Mementos
  * Engine calls Saga event handlers
  * Sagas change their state (Memento)
  * Mementos are persisted
  * Sagas may be done

Above scenario is a technical point of view, but it is good to understand it at this level. Business Developer does not have to implement technical steps, they are provided by the Engine. Business

Developers focus on the [Saga API](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample%2Fsrc%2Fmain%2Fjava%2Fpl%2Fcom%2Fbottega%2Fddd%2Fsagas) and following domain artifacts:

### Logic ###
Create class that implements Saga logic. In this class we focus only on business logic, we don't have to think about engine.
Sample:

Extends base class and use API Annotation.

Each saga listener method must be annotated.

Some (maybe all) methods may try to finish this saga.

TODO

### Data - Memento ###

Saga data is simple [Memento Design Pattern](http://en.wikipedia.org/wiki/Memento_pattern).

Idea is that Saga class is stateless, state is delegated to the memento.

TODO

### Loader ###

Saga loader focuses on funding right memento for Saga Logic based on event. In general finding Memento may rely on different parameters of different events. For each event instance, there may be many matching finders, therefore many mementos, so one event instance can push many sagas.

TODO

## Saga makes explicit what is implicit ##
Saga models very complex processes. Modeling them, instead of trying to pretend that they are not so complex, is essential for chaos management in the model and code.
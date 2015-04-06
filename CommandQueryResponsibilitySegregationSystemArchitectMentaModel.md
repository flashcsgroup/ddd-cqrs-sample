
# Introduction #
This document describes sample code illustrating CqRS techniques. Intention is to present code form the perspective of an (application/system/general) architect. So we focus on architects' mental model digging deep into engine details.

The idea is that an architect needs to understand what's under the hood - he or she should feel with engine technical code like fish in the water and freely change it.

CqRS beginners can grasp general idea watching this presentation: [CqRS - evolution of the layered architecture](http://prezi.com/pvfwd4xscqxy/command-query-responsibility-segregation-evolution-of-the-layered-architecture/)

## Architecture and design visualization using Scalable Map ##

Java (and any known language) does not support understanding complex code structures. Neither UML tools do.

Therefore, using [Prezi](http://prezi.com), we have created a "scalable map" of both big picture (architecture) and details view (class design).

  * You can watch it [here](http://prezi.com/hi2dmhfej9zu/ddd-cqrs-sample/)
  * You can "play" default road trip or pan and zoom freely using mouse
  * You can click on SVN link and go straight to the code!



---


---


# Portable architecture - technical independence #
Application architecture is described in [Part I: DDD - Application Architecture](DomainDrivenDesignBusinessDeveloperMentalModel#Application_Architecture.md).

Portable architecture idea is described in [Philosophy chapter](Philosophy#Portable_architecture_-_technical_independence.md).

Possibility to switch from Spring to other container or light and simple home made solution is described in this document in section [Run Environment - leaven for additional features](CommandQueryResponsibilitySegregationSystemArchitectMentaModel#Leaven_for_additional_features.md).

Possibility to implement Your own Events is described in this document in section [Events Engine](CommandQueryResponsibilitySegregationSystemArchitectMentaModel#Events_Engine.md).

Possibility to implement Your own Saga is described in this document in section [Saga Engine](CommandQueryResponsibilitySegregationSystemArchitectMentaModel#Saga_Engine.md).



---


---


# Architecture decisions #
## Business (Write) Model ##
Application Layer responsibility from the Business Developer perspective is described in [DDD Part](DomainDrivenDesignBusinessDeveloperMentalModel#Application_Layer.md).

When implementing Business Stack, Application Layer You can choose between: classic service style and command+handler style.


### Classic: Service - AOP techniques ###
Sample [PurchaseApplicationService](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/application/services/PurchaseApplicationService.java) is annotated with @ApplicationService annotation.

@ApplicationService annotation is annotated with Spring annotations.

```java

@Service
@Transactional
//@Secured
public @interface ApplicationService {
String value() default "";
}
```

As a result, all Application Services gain Spring features
  * Dependency Injection
  * Services can be injected into upper layer components
  * Transactions and Security provided via transparent AOP mechanisms.

### Commands and handlers ###
Sample command is described in [DDD Part](DomainDrivenDesignBusinessDeveloperMentalModel#Commands_and.md).



### Synergy of Commands and AOP ###
We have decided to mix CommandHandler approach with Spring's AOP goodies.

Each CommandHandler is annotated with [CommandHandlerAnnotation](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/cqrs/command/handler/CommandHandlerAnnotation.java) annotation.
```java

@Component
@Transactional
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandHandlerAnnotation {
}
```

CommandHandlers are automatically matched with incoming Commands by generic type of the Handler.

```java

public interface CommandHandler<C, R> {
public R handle(C command);
}
```

for example this handler:
```java

@CommandHandlerAnnotation
public class SubmitOrderCommandHandler implements CommandHandler<SubmitOrderCommand, Void> {
//..
}
```
is matched to handle this command:
```java

@Command(unique=true)
public class SubmitOrderCommand {
//..
}
```

CommandHandlerAnnotation is annotated with Spring annotations. Therefore executing any CommandHandler results in running all Spring AOP engine that supports Transactions and Security. You can also inject dependencies to each handler.

If You would like to get rid of Spring and provide Your own implementations of this features refer to [Run Environment - leaven for additional features](CommandQueryResponsibilitySegregationSystemArchitectMentaModel#Leaven_for_additional_features.md) chapter.

---

## Presentation (Read) Model ##
Presentation Stack responsibility from the Business Developer perspective is described in [DDD part](DomainDrivenDesignBusinessDeveloperMentalModel#Presentation_Stack.md).

### Performance problems with JPA ###
> TODO
  * n+1 Select problem
  * fetching unnecessary data (reporting, summary)
  * transactional behavior and dirty checking is not used when querying for read-only
  * need to encapsulate domain model (remote clients)
  * no support for any other representation other than the JPA model itself (i.e. DTOs)
    * loading JPA entities and repacking to DTO is simply silly:)
### Techniques of flattening Business Model ###
#### JPA: SELECT NEW (not recommended) ####
  * since we are using JPA for write model it might be very tempting to read from the DB using JPA as well
  * jpql "select new ...Dto(...)" works, but only on very simple data
  * selecting and entities and translating it to DTOs is also possible and might seem to get the job done, but ...
  * ... a lot of entities details in the write model are hidden from the outside world for a reason, adding the means to read those details may require you to break encapsulation or try dirty hack and neither is recommended
  * it can also cause performance issues
[see the whole JpaOrderFinder code](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/presentation/impl/JpaOrderFinder.java)
```java

@Finder
public class JpaOrderFinder implements OrderFinder {

@PersistenceContext
private EntityManager entityManager;

@SuppressWarnings("unchecked")
@Override
public List<ClientOrderListItemDto> findCurrentClientsOrders() {
Query query = entityManager
.createQuery("select new pl.com.bottega.erp.sales.presentation.ClientOrderListItemDto("
+ "o.id, o.totalCost, o.submitDate, o.status) from Order o where ...");
return query.getResultList();
}
...
}
```
#### Native SQL ####
  * sql selects are very convenient as they work on the same data source
  * this can be good as
    * you don't need to keep a separate model
    * data is always up to date
    * it's pretty robust (faster than JPA and queries can be further optimized by the DBA)
  * this can also be bad
    * not extremely scalable as you rely on a single DB
    * reading can cause write model latency
[see the whole SqlProductFinder code](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/presentation/impl/SqlProductFinder.java)
```java

@Finder
public class SqlProductFinder implements ProductFinder {

...

@Override
public List<ProductListItemDto> findProducts(ProductSearchCriteria searchCriteria) {
StringBuilder query = new StringBuilder();
Map<String, Object> parameters = new HashMap<String, Object>();
query.append("SELECT id, name, value, currencyCode FROM Product");
query.append(createWhereClause(searchCriteria, parameters));
query.append(createOrderClause(searchCriteria));
return jdbcTemplate.query(query.toString(), parameters, new ProductListItemRowMapper());
}
...
}
```
#### SQL views ####
  * this is similar to native SQL but has several improvements
  * views are another layer of abstraction over data (which has to be maintained)
  * minor changes in write model don't cause you to rewrite native SQL queries (only the views definitions)
  * its possible to use ORM tools like myBatis or Hibernate(stateless session)
  * materialized views can add some extra performance (if the vendor supports it)

#### Separate model updated by events ####
  * unlike the other techniques this approach is almost a paradigm shift from the traditional N-layered architecture
  * best scalability
  * ...
Dedicated model updated by Domain Events from the Business Developer perspective is described in [DDD Part](DomainDrivenDesignBusinessDeveloperMentalModel#Updating_read_model.md)

### Optimization: First think what You query for, tan model it ###
TODO

#### Shredding ####

#### Clustering ####

#### NoSQL usage ####

#### Cloud ####

---

## Event Sourcing ##
### When to Use ###
  * Need to store Behavioral model and "project" it in many perspectives
  * Complex Event Processing with Artificial Neural Networks (events stream as learning vector)
  * Need to "travel in time"
### Why in this context we decided to not use it ###
  * ERP is well described by archetypes
  * Relative small amount events per aggregate
  * Need to check model constrains - relational DB does it out of the box


---


---


# Technical aspects of implementation #
## Command Handlers ##
### Finding Handler ###

Matching engine is implemented in [SpringHandlersProvider](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/cqrs/command/handler/spring/SpringHandlersProvider.java) class.

Idea is quite simple:
  * Each CommandHandler is annotated with @CommandHandlerAnnotation what makes it a Spring Bean
  * Our provider object reacts on Spring internal event that is fired after Spring context refresh - onApplicationEvent method
  * Provider scans Spring Context looking for CommandHandlers
  * For each found Hander we simply check it's generic type
  * Provider registers in inner HashMap Type of the command and the Spring bean name (name of the handler)
  * When asked about handler for given command - getHandler method
    * return Spring bean

As You can see the mechanism is really simple.
You can implement Your own CommandHandler provider using any container You like (or even using home made XML, Annotations or just hardcoded map;)

### Security and transactions ###
Command + CommandHandler matching sample is described in [Synergy of Commands and AOP](CommandQueryResponsibilitySegregationSystemArchitectMentaModel#Synergy_of_Commands_and_AOP.md) chapter.

Each CommandHandler is annotated with [@CommandHandlerAnnotation](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/cqrs/command/handler/CommandHandlerAnnotation.java).

```java

@Component
@Transactional
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandHandlerAnnotation {
}
```

CommandHandlerAnnotation is annotated with Spring's annotations:
  * @Component - what makes it a Spring Bean. You can inject dependencies to the handler
  * @Transactional - transactions are automatically managed by Spring's Transaction Manger according to System/Application Exception convention (refer to the Spring documentation).
  * You can add Spring's @Secured annotation to introduce security aspect


---


## Server Gate ##
[Gate](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/cqrs/command/Gate.java) class is the first point of access to the Application Layer. It's responsibility is to dispatch Command.

It should be called by:
  * web app controllers that send Commands
  * all clients from Client Tier that send Commands

Gate itself is a Spring Component, so it can be injected into web app controllers.

In standard case Gate simply delegates command handling to the [RunEnvironment](CommandQueryResponsibilitySegregationSystemArchitectMentaModel#Run_Environment.md).

### Asynchronous mode ###
Commands **can** be annotated with [Command](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/cqrs/command/Command.java) annotation.

You can specify **asynchronous** attribute of this annotation.
```java

@Command(asynchronous=true)
public class MyCommand {
//..
}
```
In this case Gate does not delegate Command to the [RunEnvironment](CommandQueryResponsibilitySegregationSystemArchitectMentaModel#Run_Environment.md). Gate sends command to the queue and returns immediately to the client.

Responsibility of the queue is to send asynchronously command to the technical Listener, that calls [RunEnvironment](CommandQueryResponsibilitySegregationSystemArchitectMentaModel#Run_Environment.md) at the end.

**This feature is not implemented Yet in this Leaven, but idea is straightforward.**

### Optimization by detecting duplicates ###
As mentioned in the previous section, Commands **can** be annotated with [Command](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/cqrs/command/Command.java) annotation.

You can specify **unique** attribute of this annotation.
```java

@Command(unique=true)
public class MyCommand {
//..
}
```
You **can** also specify uniqueStorageTimeout property.

Sample code: [SubmitOrderCommand](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sales/application/commands/SubmitOrderCommand.java) class.
Sample scenario: nervous user keeps clicking Approve Order button and client app does not block it.

This is the way of expressing the following intention:
> Sending the same command again (eventually in given time window) is a mistake, please ignore this. Command is the same if equals method says so (so You must implement if if specifying unique=true).

This is just a tip for a server to use it's internal [GateHistory](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/cqrs/command/GateHistory.java).
Therefore server does not even have to struggle with Command Handling.

**WARNING**
This is just an optimization technique and You can not rely on it. Simply GateHistory could be filled to max and lost it's old entries.
You always have to perform checking in Domain Logic of this operation can be executed.


---

## Run Environment ##
[RunEnvironment](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/cqrs/command/impl/RunEnvironment.java) class is the actual place that runs Commands. Actually it matches CommandHandler to the Command using [provider](CommandQueryResponsibilitySegregationSystemArchitectMentaModel#Finding_Handler.md) and runs Commandhandlers.

You should use this class in the following situations:
  * in the [Gate](CommandQueryResponsibilitySegregationSystemArchitectMentaModel#Server_Gate.md)
  * in the Queue technical Listener/MessageDrivenBean that handles asynchronous commands
  * when executing commands in Application Listeners described in [DDD Part](DomainDrivenDesignBusinessDeveloperMentalModel#Listeners_-_Bounded_Context_and_Context_Mapper.md)

RunEnvironment impl is quite trivial.
It simply:
  1. finds proper Handler using injected HandlersProvider
  1. runs found Handler

### Leaven for additional features ###
Simplicity drives form Spring goodies that cares about everything (Security, Transactions).

But You can get rid of Spring simply adding technical code before and after
```java

Object result = handler.handle(command);
```

You can add:
  * transaction management (begin, end, rollback)
  * authentication and authorization
  * command execution logging
  * storing commands
  * command execution profiling
  * etc
All above must be done only in this one place.

You can also implement Your own HandlersProvider.

---


## Events Engine ##
### How does it work and why so simple:) ###
### Asynchronous Mode ###


---


## Saga Engine ##
  * Single Saga consists of 3 java classes: Saga instance (business code only), Saga Manager (persistence management), Saga data (memento which is persisted instead of the saga itself)
### Also so simple? ###
  * The most important point about the saga class is that it only contains business code
  * It's a Spring managed bean so you can use injection (usually other domain building blocks like repos and factories) and AOP if needed
  * Saga can listen to multiple events and any of those events can move the saga
  * The basic idea is similar to [the one of Udi Dahan](http://www.udidahan.com/2009/04/20/saga-persistence-and-event-driven-architectures/) for [the whole saga code check our svn](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sagas/OrderShipmentStatusTrackerSaga.java)
```java

@Saga
public class OrderShipmentStatusTrackerSaga extends SagaInstance<OrderShipmentStatusTrackerData> {

@Inject
private OrderRepository orderRepository;

@SagaAction
public void handleOrderCreated(OrderCreatedEvent event) {
data.setOrderId(event.getOrderId());
completeIfPossible();
}

@SagaAction
public void handleOrderSubmitted(OrderSubmittedEvent event) {
data.setOrderId(event.getOrderId());
// do some business
completeIfPossible();
}

@SagaAction
public void orderShipped(OrderShippedEvent event) {
data.setOrderId(event.getOrderId());
data.setShipmentId(event.getShipmentId());
completeIfPossible();
}

@SagaAction
public void shipmentDelivered(ShipmentDeliveredEvent event) {
data.setShipmentId(event.getShipmentId());
data.setShipmentReceived(true);
completeIfPossible();
}

private void completeIfPossible() {
if (data.getOrderId() != null && data.getShipmentId() != null && data.getShipmentReceived()) {
Order shippedOrder = orderRepository.load(data.getOrderId());
shippedOrder.archive();
orderRepository.save(shippedOrder);
}
}
}

```
### API ###
  * saga also requires a manager to manage its persistence
  * this is a clear separation of concerns (thanks to it we don't pollute saga code with infrastructure or application details)
  * it might be possible to manager saga loading in a generic way without creating a manager for each saga, but it actually gets pretty complicated if multiple aggregates are involved and mapping from event to a saga instance which this event relates to gets non-trivial
  * this is an example of a saga loader for [the whole source code check svn](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sagas/OrderShipmentStatusTrackerSagaManager.java)
```java

@Component
public class OrderShipmentStatusTrackerSagaManager implements
SagaManager<OrderShipmentStatusTrackerSaga, OrderShipmentStatusTrackerData> {

@PersistenceContext
private EntityManager entityManager;

@LoadSaga
public OrderShipmentStatusTrackerData loadSaga(OrderShippedEvent event) {
return findByOrderId(event.getOrderId());
}

@LoadSaga
public OrderShipmentStatusTrackerData loadSaga(ShipmentDeliveredEvent event) {
return findByShipmentId(event.getShipmentId());
}
// methods from SagaManager interface called by saga engine

@Override
public void removeSaga(OrderShipmentStatusTrackerSaga saga) {
OrderShipmentStatusTrackerData sagaData = entityManager.merge(saga.getData());
entityManager.remove(sagaData);
}

@Override
public OrderShipmentStatusTrackerData createNewSagaData() {
OrderShipmentStatusTrackerData sagaData = new OrderShipmentStatusTrackerData();
entityManager.persist(sagaData);
return sagaData;
}
```
### Persistent Memento ###
  * Since saga is a Spring managed component it isn't persistent
  * Instead of persisting saga itself a memento is created and tracked
  * The technology of persisting a memento matches the one in its manager (and the saga class is not aware of it)
  * This is a snippet of [saga memento from our svn](http://code.google.com/p/ddd-cqrs-sample/source/browse/trunk/ddd_cqrs-sample/src/main/java/pl/com/bottega/erp/sagas/OrderShipmentStatusTrackerSagaLoader.java)
```java

@Entity
public class OrderShipmentStatusTrackerData {

@Id
@GeneratedValue
private Long id;

private Long orderId;

private Long shipmentId;

private Boolean shipmentReceived = false;

// ... getters and setters
}
```
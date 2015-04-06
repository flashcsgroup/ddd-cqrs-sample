# Main goals #
## Primary goals ##
### Present sample implementation of all DDD Building Blocks and techniques ###
Project contains implementation of:
  * basic building blocks: Entity, Aggregate, Value Object, Domain Service, Factory and Repository
  * supple design building blocks: Policy, Specification
  * advanced building blocks: Application and Domain Event, Sagas
  * DDD techniques: Bounded Context, Anti-corruption Layer, Aggregate Boundary

Project covers many questionable aspects like BB injections. No technical compromises, **real** world problems and solutions instead of **hello** world play.

### Illustrating pragmatic approach to the CqRS architecture ###
  * Clean, "portable architecture" without magic tricks, that can be implemented in any technology
  * Provide **well crafted** code, ready to assimilate in **production**

## Secondary goals ##
  * present development process techniques: Behavior Driven Development
  * present ready to use and easy to adopt tools and best practices

In this projects We focus on technical challenges. This project won't teach You how to model, however You can learn some best practices and patterns.

Check [modeling resources](http://code.google.com/p/ddd-cqrs-sample/wiki/Introduction#Modeling_resources) to find more about modeling.

# Leaven idea #
Leaven ([leavening agent](http://en.wikipedia.org/wiki/Leavening_agent)) - something that You use to make a bread - a good one.

### Why general Application Framework is a bad idea ###
Frameworks are good with dealing with specific problem (persistence, security etc). But when creating an Application Framework You always focus on certain context or specific class of the problem. In real life projects (not hello world) this leads to the grotesque situation when framework author keep trying to increase abstraction level, while framework users keep digging to the base mechanism to achieve specific goal.

Of course working in **concrete context** You can build Your own Application Framework based on this leaven.

Greg Young said:
> Java is a DSL to convert XML to Exceptions

So we want to avoid a situation when all You know is an API and some code under the hood does magic - in real lifer actually does not:)

### Abstraction Distractions ###
Having some free time watch great presentation [Abstraction Distractions](http://nealford.com/downloads/conferences/Abstraction_Distractions(Neal_Ford).pdf) by Neal Ford.

So the idea is not to be distracted by too abstract concepts of the framework. Just focus on the domain and really simple engine code. Code that can be easily understood and changed when needed (instead of messing with XML and hacking wrong assumptions of framework authors)

### Just focus on the right problems ###
Having more free time read Focus book ([free version](http://focusmanifesto.com)).

# Noninvasive philosophy #
Our goal is to present a business developer programming model (way of thinking about class-level design) that is free of any platform-specific solutions. Business developer should focus on analysis and domain modeling - engine does technical stuff.

There are just few cases when You need to implement platform specific interfaces. But it's not critical, You can always change them.
There are some base classes You can extend, but they are introduced for the sake of coding ergonomic. You can modify them freely.

# Portable architecture - technical independence #
Project uses Spring and JPA (Hibernate as an implementation provider).

Spring is used to provide:
  * Transactions management
  * Security
  * Dependency Injection
Based on our experience since 2004 (Spring version one) Spring does it well.

**But overall architecture is not influenced by Spring.**

You can get rid of Spring easily and implement mentioned features on Your own or by using third party solutions.
There is one place, a RunEnvironment class where You can do this very easily by Yourself and we will discuss this scenario in details in next part.

Business model storage is based on JPA. But again - it can be done anyway You like.

# You are the Architect and owner of the code #
The main idea is that an Architect (lead developer) should understand deeply all technical classes and then modify them freely.
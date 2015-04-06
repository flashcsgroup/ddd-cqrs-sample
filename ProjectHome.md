# Version 2 is almost ready #
Project is moved to Github
https://github.com/BottegaIT/ddd-leaven-v2

# Another DDD and CqRS Sample? #

Primary goals of this project are the following:
  * presenting sample implementation of **all** DDD Building Blocks and techniques - no technical compromises, **real world** problems and solutions
  * presenting **pragmatic** approach to the CqRS architecture - a kind of "portable architecture" that can be implemented in any technology
  * providing **well crafted** code, ready to be utilized in **production**

Secondary goals:
  * presenting development process techniques: Behavior Driven Development
  * presenting ready ready to use and easy to adopt tools and best practices

In this projects we focus on technical challenges. This project won't teach you how to model using DDD, however you can learn some best practices and patterns.

# Project "map" #
A "map" that presents system and application architecture (links leads to the source code).
http://prezi.com/hi2dmhfej9zu/ddd-cqrs-sample/


# Java version repository #
Java Source code has been moved to https://github.com/BottegaIT/ddd-cqrs-sample

# .NET Version #
You can find .NET Sources here: http://cqrssample.codeplex.com/

# More than just a sample, but definitely not another framework #
Don't get us wrong, frameworks are great - but in certain contexts.

Our intention is to provide a **leaven** ([leavening agent](http://en.wikipedia.org/wiki/Leavening_agent)) - something that you use to make a bread - a good one.

### You are the Architect! ###
So you take our leaven, understand it deeply and modify to fit your context.

You don't need to couple your code with the leaven code. You can, but don't have to extend our classes. Better approach is to change, rename and repackage leaven classes:.

Leaven is really simple and small. We achieved this by developing straightforward code without unnecessary abstraction-distraction like XML, and inner accidental complexity typical for frameowrks.

If You want to change something then go straight to the code and do it instead of reading this documentation. **You are the Architect!**

# Noninvasive philosophy #
Our goal is to prepare a business developer programming model (way of thinking about class-level design) that is free of any platform-specific solutions.

Business developer should **focus on analysis and domain modeling** - engine does technical stuff.

# Portable architecture - technical independence #
Although the implementation is based on Spring and JPA we managed to avoid any special approach or programming model. Therefore our architecture is **portable** which means You can implement this "style" using any Java framework or platform (Seam, EJB, etc).

---



# Content of the Leaven - Roadmap #
### Current milestone (M1) ###
  * All DDD Building Blocks: Entity, Value Object, Aggregate, Domain Service, Policy, Specification, Repository, Factory
  * Technical aspects of implementing all BB: Dependency Injection, Lazy Loading, Cascade Operations
  * Some advanced DDD techniques: Bounded Context, Anti-corruption Layer, Events and Sagas
  * CqRS technical solutions: asynchronous Commands handling, 3 approaches to implement Read Model. Event Sourcing as a domain persistence model is not implemented in this milestone.
  * Spring technical code: Events, Sagas, Transactions, Security
  * JPA technical code: @Embedded VOs, Query optimisation
  * BDD examples - for complete development lifecycle
  * BDD Support (Spring & Maven integration with JBehave & Selenium)
### M2 ###
  * More clients: Android (in occasionally connected architecture), AJAX, WebService, Remoting (SWT)
  * More optimisation techniques for Read Model
  * Cloud environment
### M3 ###
  * Event Sourcing as another (behavioral) persistence model
  * Tools for architecture visualization and project structure validation
  * Eclipse tools for scaffolding: Building Blocks creator, BDD assist


---


# How to start #
  1. Read the [Wiki](http://code.google.com/p/ddd-cqrs-sample/wiki/TableOfContents)
    * understand the sample domain
    * understand technical design, decisions that were made, their context and trade-offs,
  1. Analyze the source code
    * [SVN repository - Java (Spring and EJB 3.1)](http://code.google.com/p/ddd-cqrs-sample/source/browse/#svn%2Ftrunk%2Fddd_cqrs-sample)
    * [SVN repository - .NET](http://cqrssample.codeplex.com/)
    * simultaneously look at the ["scalable map"](http://prezi.com/hi2dmhfej9zu/ddd-cqrs-sample/) that presents both: big picture of the architecture and details of implementation - that should help reading leaven code.
    * notice that "map" contains clickable links to source code in repo
  1. [Download](http://code.google.com/p/ddd-cqrs-sample/source/checkout) Source Code, modify it to your context - let leaven grow in warm environment
  1. Live long and prosper:)


# Acknowledgments #
Special thanks for great people driven by passion:
  * Eric Evans - for opening our minds
  * Greg Young - for great talks and great class (worth years of learning) in Cracow
  * Udi Dahan - for pragmatic approach and clear examples
  * Antonio Vivaldi - for Four Seasons:)
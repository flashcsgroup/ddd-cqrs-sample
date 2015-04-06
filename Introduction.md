# Motivation #
Providing non trivial, real life DDD examples and clarifying Java implementation details.

# Pragmatic approach #
Details are making a difference, so we try to present different solutions for different contexts.
We try to avoid so called "technical onanism" and focus on techniques that introduce a value.

# DDD and CqRS resources #
We assume that You are familiar with DDD and CqRS. That's the reason You are looking for implementation examples. That's why we are not trying to manifold theoretical content.

If You need to gain more knowledge, we recommend:
  * [Domain-Driven Design: Tackling Complexity in the Heart of Software](http://www.amazon.com/Domain-Driven-Design-Tackling-Complexity-Software/dp/0321125215/) - read this at least three times. There is no shortcut way, so don't waste Your time reading any "quickly" version or trying to learn from blogs because the "blue book" contains lots of context. And as all non trivial techniques - they work only in certain context
  * [DDD Community](http://domaindrivendesign.org)
  * [CqRS](http://cqrsinfo.com)
    * especially this 6.5h [video](http://cqrsinfo.com/video/) - it's worth Your time!
  * [fast introduction by Martin Fowler](http://martinfowler.com/bliki/CQRS.html)

# Modeling resources #
The goal of this project is not to teach You how to model. In this project we focus on the  implementation challenges. Introducing too sophisticated model would blur and complicate our goal. That's why we compromised domain model (but still putting high emphasis on general OO techniques).

If You are looking for real "ERP problem class" modeling check the following resources:
  * [Enterprise Patterns and MDA](http://www.amazon.com/Enterprise-Patterns-MDA-Building-Archetype/dp/032111230X)
  * [Analysis Patterns: Reusable Object Models](http://www.amazon.com/exec/obidos/ASIN/0201895420)

# Getting started with the code #
## checking out and building ##
  * checkout from the Google Code SVN (see http://code.google.com/p/ddd-cqrs-sample/source/checkout)
  * the project is built using Maven (tested on version 2.2.x) and should work right away when imported in your favorite IDE, e.g. Eclipse (with m2eclipse plugin), Netbeans (with the official Maven plugin) or IntelliJ IDEA
  * application should work on many application servers (tested on Jetty and Tomcat)
  * this sample doesn't require configuring any additional configuration, e.g. the data source (it uses an embedded one)
  * if you just want to see if the app builds and runs correctly (and have Maven accessible from your command line) you can run the application with:
> > mvn org.mortbay.jetty:maven-jetty-plugin:run-war
  * and navigate to http://localhost:8080/cqrs-web in your browser

## reading ##
  * these are two articles that will help you get the high level overview of the project
    * CommandQueryResponsibilitySegregationSystemArchitectMentaModel (an architect point of view)
    * DomainDrivenDesignBusinessDeveloperMentalModel (a business code developer point of view)

## contributing ##
  * for discussion and posting your suggestion please use our group at http://groups.google.com/group/ddd-cqrs-sample
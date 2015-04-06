# WIKI IS UNDER CONSTRUCTION #

We are working on the suggested learning path that is meant to flatten learning curve.


# Learning Path #

  1. [Introduction](Introduction.md)
    1. [Motivation](Introduction#Motivation.md)
    1. [Pragmatic approach](Introduction#Pragmatic_approach.md)
    1. [DDD and CqRS resources](Introduction#DDD_and_CqRS_resources.md)
    1. [Modeling resources](Introduction#Modeling_resources.md)
  1. [Philosophy](Philosophy.md)
    1. [Main goals](Philosophy#Main_Goals.md)
    1. [Leaven idea](Philosophy#Leaven_idea.md)
    1. [Noninvasive philosophy](Philosophy#Noninvasive_philosophy.md)
    1. [Portable architecture - technical independence](Philosophy#Portable_architecture_-_technical_independence.md)
    1. [You are the Architect and owner of the code](Philosophy#You_are_the_Architect_and_owner_of_the_code.md)
  1. [PART I: Domain Driven Design](DomainDrivenDesignBusinessDeveloperMentalModel.md) - **Business Developer mental model**
    1. [General introduction](DomainDrivenDesignBusinessDeveloperMentalModel#General_introduction.md)
    1. [Technical introduction](DomainDrivenDesignBusinessDeveloperMentalModel#Technical_introduction.md)
      1. [Architecture and design visualization using "scaleable map"](DomainDrivenDesignBusinessDeveloperMentalModel#Technical_introduction.md)
      1. [Project Structure](DomainDrivenDesignBusinessDeveloperMentalModel#Project_Structure.md)
      1. [Just one Maven Artifact?](DomainDrivenDesignBusinessDeveloperMentalModel#Just_one_Maven_Artifact?.md)
    1. [Domain Description](DomainDrivenDesignBusinessDeveloperMentalModel#Domain_Description.md)
      1. [Bounded Contexts](DomainDrivenDesignBusinessDeveloperMentalModel#Bounded_Contexts.md)
      1. [Shared Kernel](DomainDrivenDesignBusinessDeveloperMentalModel#Shared_Kernel.md)
      1. [WARNING](DomainDrivenDesignBusinessDeveloperMentalModel#WARNING.md)
    1. [Application Architecture](DomainDrivenDesignBusinessDeveloperMentalModel#Application_Architecture.md)
      1. [Two stacks of Layers](DomainDrivenDesignBusinessDeveloperMentalModel#Two_stacks_of_Layers.md)
      1. [Expressing Design using Custom Annotations](DomainDrivenDesignBusinessDeveloperMentalModel#Expressing_Design_using_Custom_Annotations.md)
      1. [Inversion of Control techniques](DomainDrivenDesignBusinessDeveloperMentalModel#Inversion_of_Control_techniques.md)
    1. [Application Layer](DomainDrivenDesignBusinessDeveloperMentalModel#Application_Layer.md)
      1. [Commands/Services](DomainDrivenDesignBusinessDeveloperMentalModel#Commands/Services.md)
      1. [Application Event](DomainDrivenDesignBusinessDeveloperMentalModel#Application_Event.md)
      1. [Listeners - Bounded Context and Context Mapper](DomainDrivenDesignBusinessDeveloperMentalModel#Listeners_-_Bounded_Context_and_Context_Mapper.md)
      1. [Sagas](DomainDrivenDesignBusinessDeveloperMentalModel#Sagas.md)
      1. [Statefull Application Objects](DomainDrivenDesignBusinessDeveloperMentalModel#Statefull_Application_Objects.md)
    1. [Domain Layer](DomainDrivenDesignBusinessDeveloperMentalModel#Domain_Layer.md)
      1. [Entity](DomainDrivenDesignBusinessDeveloperMentalModel#Entity.md)
      1. [Aggregate](DomainDrivenDesignBusinessDeveloperMentalModel#Aggregate.md)
      1. [Value Objects](DomainDrivenDesignBusinessDeveloperMentalModel#Value_Objects.md)
      1. [Factory](DomainDrivenDesignBusinessDeveloperMentalModel#Factory.md)
      1. [Repository](DomainDrivenDesignBusinessDeveloperMentalModel#Repository.md)
      1. [Domain Services](DomainDrivenDesignBusinessDeveloperMentalModel#Domain_Services.md)
      1. [Policy](DomainDrivenDesignBusinessDeveloperMentalModel#Policy.md)
      1. [Specification](DomainDrivenDesignBusinessDeveloperMentalModel#Specification.md)
      1. [Domain Events](DomainDrivenDesignBusinessDeveloperMentalModel#Domain_Events.md)
    1. [Saga](DomainDrivenDesignBusinessDeveloperMentalModel#Saga.md)
      1. [Complex process orchestration](DomainDrivenDesignBusinessDeveloperMentalModel#Complex_process_orchestration.md)
      1. [Persistent multi-listener](DomainDrivenDesignBusinessDeveloperMentalModel#Persistent_multi-listener.md)
      1. [Saga makes explicit what is implicit](DomainDrivenDesignBusinessDeveloperMentalModel#Saga_makes_explicit_what_is_implicit.md)
  1. [Part II: Command-query Responsibility Segregation](CommandQueryResponsibilitySegregationSystemArchitectMentaModel.md) - **System Architect mental model**
    1. Portable architecture - technical independence
    1. Architecture decisions
      * Business (Write) Model
        * Classic: Service - AOP techniques
        * Commands and handlers
        * Synergy: Commands and AOP
      * Presentation (Read) Model
        * Performance problems with JPA
        * Techniques of flattening Business Model
        * Optimization: First think what You query for, then model it
      * Event Sourcing
        * When to Use
        * Why in this context we decided to not use it
    1. Technical aspects of implementation
      * Command Handlers
        * Finding Handler
        * Security and transactions
      * Server Gate
        * Asynchronous mode
        * Optimization by detecting duplicates
      * Run Environment
        * Leaven for additional features
      * Events Engine
        * How does it work and why so simple:)
        * Asynchronous Mode
      * Saga Engine
        * Also so simple?
        * API
        * Persistent Memento
  1. PART III: Behavior Driven Development
    1. Resources
    1. Sample
    1. Support
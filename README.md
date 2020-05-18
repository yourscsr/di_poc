Introduction:

This document describes how to design and software component which is used to create the beans by using dependency injection. The pupose of this component is to server the user request to create/fetch the objects of particular type either in singleton or prototype scope.

Requirements:

The system must be designed and implemented on the following requirements:

1. Using Java 1.8+.
2. Using Maven build tool.
3. No deependency on external libraries.
4. Using only Junit library in test scope.

Design considerations:

1. Using singleton pattern for creating ApplicationContext.
2. ApplicationContext should provide API to register different bean types for different scopes.
3. ApplicationContext should provide API to fetch bean instances with specific bean type.
4. Extensive unit testing to cover all the functionality.
5. API documentation

Limitations:

1. Currently it is only possible to create beans with scope singleton and protype.
2. There is no seggregation of different exception types for different problems.


Getting started:

1. Downnload from github and build the compoent with the following command:

git clone <git-hub-repo-utl>
cd dependencyinjection-poc
mvn clean install

This will compile, run all the unit tests and publish the artifact to the local maven repository.

Usage guide:

1. Download the 0.1-beta release from maven central repository using the below dependency module in your pom.xml

<dependency>
	<groupId>com.di.poc</groupId>
	<artifactId>dependencyinjection-poc</artifactId>
	<version>0.1-beta</version>
	<scope>compile</scope>
</dependency>


TODO:

1. Make the prototype bean creation aware of singleton beans for dependency injection while creating prototype beans.
2. Support of other bean scopes like session/request for web aware applications with the new MvcApplicationContext.
2. Seggregation of exceptions to different exceptional cases:
-- BeanCreationException - When failed to resolve/crete the bean of particular type when requested with getBean() method.
-- BeanUnsupportedException - Only beans that are registerd can be lookedup in the ApplicationContext? Open point TBC.
-- BeanAmbiguityMultipleAutowiredConstructorsException - When resolving the bean which has multiple constructors autowired.
-- BeanCreationFailedWithArgsException - When the singleton/prototype beans are registered with aruguments list(Object[]) which does not match with existing constructor arguments size.
-- BeanCreationFailedWithArgsTypeMissmatchException -  When the singleton/prototype beans are registered with aruguments list(Object[]) which does not match with existing constructor arguments types.

OpenPoints:
1. Do we need to raise BeanNotRegisteredException if the requested bean type is not registered as singleton/prototype bean in ApplicationContext?
2. Creation of singleton beans on demand with getBean() method or create on initialize() of ApplicationContext?

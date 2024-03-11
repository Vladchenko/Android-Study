example4 dagger demo.
All classes are provided through Dagger component.
Dagger module has only bindings to respective implementations, without them, dagger doesn't know how to provide an implementations.
Comparing to an example3, in this example, datasources are provided using common interface. Because of that, one has to use
@Named annotation, since dagger has to know which implementation for LoginDataSource implementation to use.

For now, interactor is injected into activity. Wrong approach, but will do for now, a point here is to learn dagger and not make a clean architecture.
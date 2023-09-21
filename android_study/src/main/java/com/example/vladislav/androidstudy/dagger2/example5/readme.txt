example5 dagger demo.
Here we get a dependencies from a component's dependencies section.
Component is getting some dependency from other feature. In real application, this feature should be located in another gradle modules (api/impl),
but for study purposes only, we put it just in a separate package.
This dependency has to be provided in its own dagger component.
example6 should put dependencies to a separate module.
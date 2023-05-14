Multiple simultaneous files downloading, using Retrofit and Coroutines.

- Cons: - It has downloading and saving in one class (fixing it, is not required in this example)
        - SOLID breaks (fixing it, is not required in this example)
        - Retrofit's baseUrl limits a location for downloading only to one server

TODO
    - Move this example to coroutines package
    - Add some result listener saying downloading is complete
    Implement display of a downloading files like
        Name
        Progress bar (Current downloaded %)
    First, using only recyclerView then add a fragment, then add viewmodel.
    Try replacing current network layer with Retrofit
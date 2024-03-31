Receiving and displaying phone contacts. Each contact is clickable and able to be called.

Android OS has its own contacts ContentProvider, so we should only use ContentResolver to get access to it.

Dagger has a context provided in application class for to create content resolver.

In clean architecture, one has to convert data model to domain/presentation model. But here one
doesn't need that, so it is omitted in purpose. There is also no business logic present, thus no
interactor present.

ContactsProvider is created to move android classes from ContactsRepository to presentation layer.
Not sure if name fits it well.

It is not mandatory to move permission requesting to a separate class (ContactsPermissionDelegate),
one can do it in activity.

! Not clear how to put conditions
 - "contactsList.distinctBy { it.phoneNumber }"
 - if (name != phoneNumber)
 into contentResolver.query
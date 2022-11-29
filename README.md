

# @Deprecated
##### Modern Android has moved to Compose UI, although Compose UI could be mixed with traditional ViewGroups and Fragments UI, I prefer 
##### to have a separate library for just Compose. For a similar architecture pattern using State Management + Compose UI check bellow link:
[State Management free of UI + Compose](https://github.com/pablichjenkov/state-tree)

 ## Coordinators
  The Coordinators Pattern aims to resolve a common and hard to scale problems in an application 
  --**App Navigation**--.
  
  Popular **MVC** derived architectures resolve the problem of separating View from Model but 
  they don't directly resolve the **Navigation** issue. In architectures like **MVP** or **MVVM** 
  we end up writting Navigation logic in our Presenters or ViewModels, this practice makes harder 
  to reuse our presenters because they need to know where they come from and where to go next. This 
  is clear not a responsibility of a Presenter or a ViewModel is some one else job.
  
  The Coordinator architecture is built with the navigation problem in mind. Emphasizing the fact 
  that Navigation should not be triggered from View events but from Business Logic events.
  
  
 ##### Architecture Overview
  Every Coordinator has a set of children Coordinators that enter **on stage** accordingly with the 
  app navigation flow. This nested structure form a **Coordinator Tree** that lives in the 
  underlying **Activity** and start with an initial Coordinator called root Coordinator. 
  Coordinators enter **on stage** once their **start()** method gets called. While **on stage** a 
  Coordinator listen to input events and react accordingly. A good Coordinator implementation has 
  an internal **State Machine** that handles request from a message queue and deliver responses to 
  such request through output pipes. Ideally they should work similar to the Actor model, the idea 
  is that a Coordinator behaves like an Actor.
 
 ### Coordinator Lifecycle
 A Coordinator Tree is unique per Activity and the first node is called the **Root Coordinator**. 
 This root Coordinator will be created when the hosting Activity is created and will persist 
 configuration changes using the **OrientationPersister** class which internally uses the **Android 
 ViewModel** from components architecture.
 
 
 **Coordinators should not know about Android Framework classes**, a Coordinator has dependencies 
 and it must be provided by its parent Coordinator before calling **start()**. Coordinators are 
 agnostic to any method of injecting the dependencies into them. Any technique like *Dagger*, 
 *Service Provider* or *State Lifting* can be used, just need to consider that Coordinators are 
 immutable to configuration changes, so every time a dependency is updated, the new instance 
 has to be propagated through the whole Coordinator Tree.
 
 *As an example, a Coordinator that presents UI elements in the screen would have somehow a 
 dependency of the current Activity, since an Activity is the owner of the **View Tree** where 
 Views are inserted.
 You can use a **ScreenCoordinator** to abstract the handling of the Activity FragmentManager 
 or Root ViewGroup if you don't plan to use Fragments. You can then pass this  ScreenCoordinator 
 implementation to Children Coordinators and they will have access to the underlying **Activity View 
 Tree**.*
 
 
 ##### Activity Lifecycle
 As a handy tool, the **Activity Lifecycle Events** are forwarded through the Coordinator Tree for 
 free. This process tells a Coordinator what's going on in the Hosting Activity. Very useful if 
 your Coordinator has to deal with **OnActivityResult(...)** and **onBackButtonPressed()**.
 
 **Yes the back button event is handled at the Coordinator level, and it's just another input event,
  no more messy navigation logic depending on the FragmentBackStack.**
 
 
 ##### Configuration Changes
 Every time rotation happens the **Activity View Tree** is destroyed and recreated but the 
 corresponding **CoordinatorTree** is persisted. After the View Tree is recreated, each View/Fragment 
 needs to be bound to the corresponding Coordinator that originate it. 
 This implementation uses an interface called **CoordinatorBindableView,** that is implemented by 
 Fragments, Views or ViewGroups. Basically a CoordinatorBindableView interface is a setter for a 
 **coordinatorId** of the original Coordinator that pushed it into the screen/ViewTree.
 
 This **coordinatorId** will be used to fetch the corresponding coordinator from the 
 Coordinator Tree once the View/Fragment is recreated by the system. 
 The process normally works like this:
 
 1. Before pushing a View/Fragment to the View Tree a Coordinator sets its id to the View/Fragment 
 and pushes it into to the Screen.
 
 2. As soon as the View/Fragment is resumed it fetches its corresponding Coordinator from the 
 Coordinators Tree and binds to it. The binding is bidirectional, it means the View/Fragment has a 
 reference to its Coordinator and the Coordinator has a reference to the View/Fragment as well.
 
 3. In case of rotation the View/Fragment will be destroyed, at this point the View/Fragment must 
  unsubscribe from its Coordinator to avoid any resource leakage. 
  
 4. Once the View/Fragment is recreated it will go to **step 2**.
 
 
 Extending from **CoordinatorFragment** or **CoordinatorDialogFragment** or 
 **CoordinatorViewGroup** will save you time implementing it on your own, however, in case you 
 have already another ancestor in your Fragment/ViewGroup hierarchy you can use 
 **CoordinatorFragmentBinder** or **CoordinatorViewGroupBinder** as delegates. Check the internal 
 implementation of **CoordinatorFragment** or **CoordinatorDialogFragment** or 
 **CoordinatorViewGroup** and use it as reference.
 
 
 ##### Coordinator On Stage 
 The method **Coordinator.onStart()** indicates the Coordinator that it has entered on stage.
 ```kotlin
     override fun start() {
         if (stage == Stage.Idle) {
             stage = Stage.LoginInternal
             showLoginScreen()
         }
     }
 ```
 It is the time to consult a web service, render data into the screen, perform business logic or
 perhaps starting another child Coordinator.
 Upon completion your Coordinator will indicate its parent that it is done, then the parent will 
 decide what to do next according to the navigation logic of your app. Communication between 
 Parent and Child Coordinator is up to the developer, you can choose a direct Listener Delegate 
 pattern or do it in a more reactive way where the parent is an Observer of a Child Coordinator 
 Observable pipe of events. See the samples.
 
 
 ### Build times, testability and Share-ability
 A Coordinator is build and tested independently, as long as it receives the appropriate dependencies
 it will just work. It should not complain about and Android Framework class most of the times.
 That's why is important to depend only on abstractions and not on concretions. It should only care
 about transitioning to the right next Stage like a good **Stage-Machine** does.
 As an example you can re-use an Authorization Coordinator across different projects.
 
 See the sample apps for most use cases. Start by playing with simple Coordinators by 
 extending **Coordinator** class and then create composite Coordinators by extending 
 **CompoundCoordinator** class.
 

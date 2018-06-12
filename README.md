
 ## Reactive Workflow
 The Reactive Workflow Pattern is basically the State Machine Pattern. Every Workflow or 
 **Flow**(for shorter) has a set of children Flows that enter **on stage** depending on certain
 actions received from external inputs. This nested structure form a **Flow Tree,**somehow
 equivalent to use **Nested Stores** in Redux architecture.
 
 ### Flow Lifecycle
 A Flow Tree is unique per Activity and the first node is called the **Root Flow.** This root Flow
 will be created when the hosting Activity is created and will persist configuration changes.
 If your Activity extends FlowActivity override the method bellow.
 *Otherwise, if having other inheritance ancestor already, check the FlowActivity.kt file to see
 how integrate it*.
 
 ```kotlin
    override fun onCreateRootFlow(): Flow<StateContext, *> {
        return OnboardindFlow(Constants.ONBOARDING_FLOW_ID)
    }
 ```
 
 **Flows should not know about Android Framework classes,** a Flow has dependencies and it must be provided
 by its parent Flow. Flows are agnostic to any method of injecting the dependencies into them. Any
 technique like *Dagger*, *Service Provider* or *State Lifting* can be used, just need to consider
 that Flows are immutable to configuration changes, so every time a dependency is updated, the new
 instance has to be propagated through the whole Flow Tree.
 
 *As an example, a Flow that presents UI elements in the screen would have somehow a dependency of
 the current Activity, since an Activity is the owner of the **View Tree** where Views are inserted.
 You could use a Navigator or a ScreenManager that helps with the task of transitioning these Views.
 The way it works is that this Navigator is provided to all those Flows that present Views.
 Now when the device rotates a new Activity instance and **View Tree** are created, invalidating 
 the old Activity which our Navigator has reference to. Since the Flow Tree is persisted across
 rotation conserving all its State/Stage, the old Navigator instance becomes invalid now. Then it
 means that if we want to keep presenting Views into the new Activity we need to update the
 Navigator instance and propagate it all the way down.*
 
 When the above scenario happens the a callback method on the new Activity instance is called. Make
 sure you return the updated StateContext(*more on that later*).
 ```kotlin

    override fun onProvideStateContext(): StateContext {

        val stateContext = StateContext()

        // Register a Navigator Service
        stateContext.registerState(NavigatorService(
                supportFragmentManager,
                findViewById(R.id.introActivityViewContainer)))

        return stateContext
    }

 ```
 
 Then the method **onStateContextUpdate(stateContext: StateContext)** will be called in every Flow
 of the Tree in a Depth First Search manner.
 ```kotlin

    override fun onStateContextUpdate(stateContext: StateContext) {
        val clazz: Class<NavigatorService> = NavigatorService::class.java
        val navigatorService: NavigatorService? = stateContext.getState(clazz, Constants.DEFAULT_NAVIGATOR_SERVICE_ID)
        val navigator: Navigator = navigatorService?.getNavigator()
    }
 ```
 As a handy tool, the **Activity Lifecycle Events** are forwarded to the Flow Tree. This will 
 guide your Flow on what's going on in the Hosting Activity. Very useful since our Flows normally
 deal with **OnActivityResult(...)** and **onBackButtonPressed()**.
 
 **Yes the back button event is handled at the Flow level, and it's just another input event, no
 more messy navigation logic depending on the FragmentBackStack.**
 
 
 Another concept in the architecture is the **FlowBindableView,** this is an interface that would be
 implemented by Fragments, Views or ViewGroups. Basically a FlowBindableView contains the **flowId**
 of the Flow that pushed it into the screen/ViewTree. Every time rotation happens the Activity
 View Tree is destroyed and recreated. When destroying the Views unsubscribe from its Flow and save
 the flowId in *onSaveInstanceState()*. Then when the View Tree gets re-created the view uses the
 **flowId** to find its corresponding Flow in the Tree and resubscribe to it.
 
 Extending from **FlowFragment** or **FlowDialogFragment** or **FlowViewGroup** will save you time
 implementing it on your own, however, in case you have already another ancestor in your
 Fragment/ViewGroup inheritance you can use **FlowFragmentBinder** or **FlowViewGroupBinder** as
 delegates to do so, in fact the 3 classes a fore mentioned use them.
 
 
 ### On Stage Flow
 The method **Flow.onStart()** indicates the Flow that has entered on stage. It is called after the
 StateContext has been provided so your dependencies are injected at this point, you are ready to go
 and do whatever you want in your Flow.
 ```kotlin
     override fun start() {
         if (stage == Stage.Idle) {
             stage = Stage.LoginInternal
             showLoginScreen()
         }
     }
 ```
 It is the time to consul a web service, render data into the screen, perform business logic or
 perhaps starting another child Flow, who cares.
 Upon completion your Flow will indicate its parent that it is done, then the parent will decide
 what to do next according to the navigation logic of your app. Communication between Parent and
 Child Flow is up to you, you can choose a Listener interface per Flow or do it in a more reactive
 way where the parent is an Observer of the Child Flow subjects, who cares.
 
 
 ### State Context
 The State Context is the component you pass traversing the Flow Tree to update Flow dependencies.
 A Flow Tree is parametrized to a StateContext type. Is up to the developer what method to use, the
 provided samples use a Service Provider mechanism inspired by the InheritedWidget model of Flutter
 Framework. 
 The StateContext can hold or be a DaggerObjectGraph that gets passed down and consumers get
 injected from it.
 
 
 ### Build times and Share-ability
 A Flow is build and be tested independently, as long as it receives the appropriate dependencies
 it will just work. It should not complain about and Android Framework class or anything like that.
 That's what is important to depend only on abstractions and not on concretions. It should only care
 about transitioning to the right next Stage like a good **Stage-Machine** does.
 A Flow can also be shared from one project to another, ensure that the foreign StateContext
 provides the right dependencies for the shared Flow and that's it.
 As an example you can re-use a Login Flow across different projects.
 
 See the sample apps for most use cases. Start by playing with simple Flows by extending **Flow**
 and then create composite Flows by extending **CompoundFlow**.

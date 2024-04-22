### Macao SDK
A set of prefabricated components and platform API plug-ins to speedup the Compose Multiplatform Application development process. 
The toolkit contains a variety of customizable navigation components such as `Drawer`, `BottomNavigation`, `Pager`, `Panel`,
`Topbar`, `Stack` and many more. It also includes some predefined screens flows such as `Authentication`, `Settings`, `Commerce` and some `Data Collection` screens. All them customizable from the server application.
<BR/>
On the plug-in side it already has multiplatform implementations for `GlobalBackPressDispatcher`, `PlatformLifecycle`, `Firebase`, `Notifications` and many more to come.
<BR/>
Code is 99% reusable and available in all platforms.

*-One image is worth a thousand words*

<img width="700" alt="macao-sdk-arch" src="https://github.com/pablichjenkov/macao-sdk/assets/5303301/2f3b0d28-c3ab-42cd-9de0-094d373503c7">

<img width="700" alt="macao-component-diagram" src="https://github.com/pablichjenkov/macao-sdk/assets/5303301/fa58c951-0fc7-470d-b2a0-9a534a405c34">

#### How to use it?
Check the [Developers Guide](/component-toolkit/README.md) in the component-toolkit module. Soon the macao-sdk-di-koin module will have its own guide.

**Artifacts are published on Maven Central:**

```kotlin
sourceSets {
    commonMain.dependencies {
        implementation("io.github.pablichjenkov:component-toolkit:0.6.2")
    }
}
```

#### Contributions
We welcome contributions from the community! If you have ideas for new features, bug fixes, or improvements, please open 
an issue or submit a pull request.

### Built with component-toolkit
#### Component Toolkit Demo App

This is the App used to demo and test while developing components.

<table border="1">
 <tr>
    <td><b style="font-size:30">Android</b></td>
    <td><b style="font-size:30">iOS</b></td>
 </tr>
 <tr>
    <td><img title="Android components demo" src="https://user-images.githubusercontent.com/5303301/225816832-682d3620-6218-4d60-b742-4a692761abee.gif" alt="android-component-demo" width="300"></td>
    <td><img title="iOS components demo" src="https://user-images.githubusercontent.com/5303301/225282413-fb433cc2-416f-4d98-ad29-cb676030a0ec.gif" alt="ios-component-demo" width="300"></td>
 </tr>
</table>
<table border="1">
<tr>
    <td><b style="font-size:30px">Deep Linking</b></td>
 </tr>
 <tr>
    <td><img title="Desktop components demo" src="https://github.com/pablichjenkov/component-toolkit/assets/5303301/f9f998e4-41dd-44ac-a5d0-dbbac7c0a002" alt="desktop-deep-link-demo" width="800"></td>
 </tr>
</table>
<table border="1">
<tr>
    <td><b style="font-size:30px">Deep Linking + Request/Result between Components</b></td>
 </tr>
 <tr>
    <td><img title="Desktop deep link result passing demo" src="https://github.com/pablichjenkov/component-toolkit/assets/5303301/601556cd-42c2-4491-95fc-30e5a139d3ac" alt="desktop-deep-link-demo" width="800"></td>
 </tr>
</table>
<table border="1">
<tr>
    <td><b style="font-size:30px">Adaptive Layout</b></td>
 </tr>
 <tr>
    <td><img title="Desktop components demo" src="https://user-images.githubusercontent.com/5303301/225287289-d1870ba7-1a09-4570-a041-934746a35c11.gif" alt="desktop-adaptive-component-demo" width="800"></td>
 </tr>
</table>
<table border="1">
 <tr>
    <td><b style="font-size:30px">Web Browser</b></td>
 </tr>
 <tr>
    <td><img title="Web components demo" src="https://user-images.githubusercontent.com/5303301/214518301-88398770-a508-45f2-b411-520155f4f7e9.jpg" alt="web-component-demo" width="800"></td>
 </tr>
</table>

<BR>

#### Hotel Booking App

[Hotel Booking](https://github.com/pablichjenkov/amadeus-hotel-app) An App that consumes Amadeus Self Sevice API.
The Amadeus Self Sevice API is the company's public service to test the Hotel and Flight booking in their platform.

<table style="border:1px solid black;">
 <tr>
    <td style="border:1px solid black;"><b style="font-size:30px">Android</b></td>
    <td style="border:1px solid black;"><b style="font-size:30px">iOS</b></td>
 </tr>
 <tr>
    <td style="border:1px solid black;"><img title="Hotel Booking Android" src="https://github.com/pablichjenkov/amadeus-hotel-app/assets/5303301/d795c512-a15d-4d92-81cb-f1452eace104"></td>
    <td style="border:1px solid black;"><img title="Hotel Booking iOS" src="https://github.com/pablichjenkov/amadeus-hotel-app/assets/5303301/97eeb489-8f92-401a-aadf-e6ec068668f8"></td>
 </tr>
</table>
    <table style="border:1px solid black;">
 <tr>
    <td style="border:1px solid black;"><b style="font-size:30px">Web</b></td>
 </tr>
 <tr>
    <td style="border:1px solid black;"><img title="Hotel Booking Web" src="https://github.com/pablichjenkov/amadeus-hotel-app/assets/5303301/77412db5-4030-4a41-b51f-07dd0a60e3c1"></td>
 </tr>
</table>

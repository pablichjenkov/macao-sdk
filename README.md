### Component Toolkit <under development 🚧👷>
A set of prefabricated compose-multiplatform components to speedup the App development process. The module contains a variety of navigation components as drawer, tabbar, topbar, bottombar, pager, stack, panel and many more. Code is 99% reusable in each platform. Check demos bellow.

##### How to use it?
Just add bellow dependency in the **commonMain** target of the module that plans to consume the toolkit. For sample usage code, check the `shared` module in this same project, or check the links to the Apps in the next session.

See readme inside component-toolkit module for code snippets.
<BR>
<BR>

**Component-toolkit is published on Maven Central:**

```kotlin
val commonMain by getting {
    dependencies {
        implementation("io.github.pablichjenkov:component-toolkit:0.4.5")
    }
}
```

### Built with component-toolkit
#### Component Toolkit Demo App

This is the App I use to test while developing the components.

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
    <td><b style="font-size:30px">Deep Link</b></td>
 </tr>
 <tr>
    <td><img title="Desktop components demo" src="https://github.com/pablichjenkov/component-toolkit/assets/5303301/f9f998e4-41dd-44ac-a5d0-dbbac7c0a002" alt="desktop-deep-link-demo" width="800"></td>
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

[Hotel Booking](https://github.com/pablichjenkov/amadeus-hotel-app) An App that consumes Amadeus hotel booking public API.

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

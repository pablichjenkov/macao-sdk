import SwiftUI
import ComponentDemoKt

struct ComposeView : UIViewControllerRepresentable {

    var iosBridge: IosBridge

    func makeUIViewController(context: Context) -> UIViewController {
        // let drawerComponent = BindingsKt.buildDrawerComponent()
        let adaptiveComponent = BindingsKt.buildAdaptableSizeComponent()
        // let pagerComponent = BindingsKt.buildPagerComponent()
        
        
        let test = IosBridge2(
            test: NSURL.fileURL(withPath: "https://google.com")
        )
        
        let mainViewController = BindingsKt.buildDemoViewController(
            rootComponent: adaptiveComponent,
            iosBridge: iosBridge,
            iosBridge2: test,
            onBackPress: {
                exit(0)
            }
        )

        return mainViewController
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {

    var iosBridge: IosBridge

    var body: some View {
        ComposeView(iosBridge: iosBridge)
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
                //.ignoresSafeArea(.all, edges: .bottom) // If preferred to handle this in compose land

    }
}

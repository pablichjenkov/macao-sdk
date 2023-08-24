import SwiftUI
import ComponentDemoKt

struct ComposeView : UIViewControllerRepresentable {

    var iosBridge: IosBridge

    func makeUIViewController(context: Context) -> UIViewController {
        // let drawerComponent = BindingsKt.buildDrawerComponent()
         let adaptiveComponent = BindingsKt.buildAdaptableSizeComponent()
        // let pagerComponent = BindingsKt.buildPagerComponent()
        
        
        let test = IosBridge2(
            test: NSURL.fileURL(withPath: "dcrcd")
        )
        
        let mainViewController = BindingsKt.ComponentRenderer(
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
                //.ignoresSafeArea(.all, edges: .bottom) // If prefered to handle this in compose land

    }
}

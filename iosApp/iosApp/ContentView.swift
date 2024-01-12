import SwiftUI
import ComponentDemoKt

struct ComposeView : UIViewControllerRepresentable {

    var iosBridge: IosBridge

    func makeUIViewController(context: Context) -> UIViewController {
        
        let mainViewController = BindingsKt.buildKoinDemoViewController(
            iosBridge: iosBridge,
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

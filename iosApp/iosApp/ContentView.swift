import SwiftUI
import ComponentDemoKt

struct ComposeView : UIViewControllerRepresentable {

    let iosBridge: IosBridge

    func makeUIViewController(context: Context) -> UIViewController {
        
        return BindingsKt.buildKoinDemoViewController(
            iosBridge: iosBridge,
            onBackPress: {
                exit(0)
            }
        )
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

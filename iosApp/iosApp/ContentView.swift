import SwiftUI
import ComponentDemoKt

struct ContentView: View {
    
    let iosBridge: IosBridge
    
    var body: some View {
        ComposeView(iosBridge: iosBridge)
        // Compose has own keyboard handler
        .ignoresSafeArea(.keyboard)
        // If preferred to handle this in compose land
        //.ignoresSafeArea(.all, edges: .bottom)
    }
}

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
    
    func updateUIViewController(
        _ uiViewController: UIViewController,
        context: Context
    ) {}
    
}

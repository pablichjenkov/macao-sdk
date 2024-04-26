import SwiftUI
import MacaoSdkDemoKt

struct ContentView: View {
    
    var iosBridge: IosBridge
    
    var body: some View {
        ComposeViewController(iosBridge: iosBridge)
            // Compose has own keyboard handler
            .ignoresSafeArea(.keyboard)
            // If preferred to handle this in compose land
            //.ignoresSafeArea(.all, edges: .bottom)
    }
}

struct ComposeViewController : UIViewControllerRepresentable {

    var iosBridge: IosBridge

    func makeUIViewController(context: Context) -> UIViewController {
        
        return MacaoSdkDemoKt.buildDemoViewController(iosBridge: iosBridge)
    }
    
    func updateUIViewController(
        _ uiViewController: UIViewController,
        context: Context
    ) {}
}

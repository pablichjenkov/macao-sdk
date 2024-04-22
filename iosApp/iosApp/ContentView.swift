import SwiftUI
import ComponentDemoKt

struct ContentView: View {
    
    var body: some View {
        ComposeViewController()
            // Compose has own keyboard handler
            .ignoresSafeArea(.keyboard)
            // If preferred to handle this in compose land
            //.ignoresSafeArea(.all, edges: .bottom)
    }
}

struct ComposeViewController : UIViewControllerRepresentable {
    
    func makeUIViewController(context: Context) -> UIViewController {
        
        return BindingsKt.buildDemoViewController()
    }
    
    func updateUIViewController(
        _ uiViewController: UIViewController,
        context: Context
    ) {}
}

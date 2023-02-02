package com.pablichj.incubator.uistate3.node.navigation

import com.pablichj.incubator.uistate3.node.Component

object DefaultPathMatcher : IPathMatcher {

    override fun traverseWithChildMatchAction(
        path: Path,
        currentNodeSubPath: SubPath,
        deepLinkComponents: List<Component>,
        onChildMatchAction: (path: Path, matchingComponent: Component) -> DeepLinkResult
    ): DeepLinkResult {
        // Check that current Path route matches current parent node
        val currentRoute = path.getCurrentSubPath().route
        if (currentRoute != currentNodeSubPath.route) {
            return DeepLinkResult.Error(
                """
                Node::deepLink(). This node with route(${currentNodeSubPath.route}) did not match
                any child in parent node with absolute path: ${path.getCurrentPathAsString()}
            """.trimIndent()
            )
        }

        if (path.isAllMatches()) {
            return DeepLinkResult.Success
        }

        // Check at least a child matches any of the child subPath routes
        val childSubPathToMatch = path.getNextSubPath()

        val matchingNode = deepLinkComponents
            .filterNot {
                println("Checking link: ${it.subPath.route}")
                it.subPath == SubPath.Empty
            }
            .firstOrNull { it.subPath.route == childSubPathToMatch.route }

        println("One above has to match: ${childSubPathToMatch.route}")

        return if (matchingNode != null) {
            onChildMatchAction(path.moveToNextSubPath(), matchingNode)
        } else {
            DeepLinkResult.Error(
                """
                       PathSolver::deepLink(). Requested subPath(${childSubPathToMatch.route}) did 
                       not match any child in Parent node with absolute Path:
                       (${path.getCurrentPathAsString()}/*no_match*))
                    """.trimIndent()
            )
        }
    }

}

interface IPathMatcher {
    fun traverseWithChildMatchAction(
        path: Path,
        currentNodeSubPath: SubPath,
        deepLinkComponents: List<Component>,
        onChildMatchAction: (path: Path, matchingComponent: Component) -> DeepLinkResult
    ): DeepLinkResult
}

sealed class DeepLinkResult {
    object Success : DeepLinkResult()
    data class Error(val errorMsg: String) : DeepLinkResult()
}
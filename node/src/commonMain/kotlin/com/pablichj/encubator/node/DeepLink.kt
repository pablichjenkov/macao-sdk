package com.pablichj.encubator.node

class Path() {

    constructor(route: String) : this() {
        appendSubPath(SubPath(route))
    }

    constructor(subPath: SubPath) : this() {
        appendSubPath(subPath)
    }

    constructor(path: Path) : this() {
        appendPath(path)
    }

    private val subPathList = ArrayList<SubPath>()
    private var currentSubPathIndex = 0

    fun appendSubPath(route: String): Path {
        subPathList.add(SubPath(route))
        return this
    }

    fun appendSubPath(subPath: SubPath): Path {
        subPathList.add(subPath)
        return this
    }

    fun appendPath(path: Path): Path {
        subPathList.addAll(path.subPathList)
        return this
    }

    fun getCurrentSubPath(): SubPath {
        return subPathList[currentSubPathIndex]
    }

    fun getNextSubPath(): SubPath {
        return subPathList[nextSubPathIndex()]
    }

    fun moveToNextSubPath(): Path {
        val nextSubPathIndex = nextSubPathIndex()
        if (nextSubPathIndex < subPathList.size) {
            currentSubPathIndex = nextSubPathIndex
        }
        return this
    }

    fun getCurrentPathAsString(): String {
        return subPathList
            .take(currentSubPathIndex + 1)
            .fold(StringBuilder()) { stringBuilder, subPath ->
                stringBuilder.append("/").append(subPath.route)
            }.toString()
    }

    fun getPathUntil(subPath: SubPath): String {
        val stringBuilder = StringBuilder()
        subPathList
            .takeWhile { it != subPath }
            .forEach {
                stringBuilder.append("/").append(it.route)
            }
        return stringBuilder.toString()
    }

    fun isAllMatches() = subPathList.size - 1 == currentSubPathIndex

    private fun nextSubPathIndex(): Int = currentSubPathIndex + 1

    fun moveToStart(): Path {
        currentSubPathIndex = 0
        return this
    }
}

data class SubPath(val route: String) {
    companion object {
        val Root = SubPath("Root")
        val Empty = SubPath("Empty")
    }
}

object PathMatcher {

    fun traverseWithChildMatchAction(
        path: Path,
        parentSubPath: SubPath,
        deepLinkNodes: List<Node>,
        onChildMatchAction: (path: Path, matchingNode: Node) -> DeepLinkResult
    ): DeepLinkResult {
        // Check that current Path route matches current parent node
        val currentRoute = path.getCurrentSubPath().route
        if (currentRoute != parentSubPath.route) {
            return DeepLinkResult.Error(
                """
                Node::deepLink(). This node with route(${currentRoute} did not match
                |any child in parent node with absolute path: ${path.getCurrentPathAsString()}
            """.trimIndent()
            )
        }

        if (path.isAllMatches()) {
            return DeepLinkResult.Success
        }

        // Check at least a child matches any of the child subPath routes
        val childSubPathToMatch = path.getNextSubPath()

        val matchingNode = deepLinkNodes
            .filterNot {
                it.context.subPath == SubPath.Empty
            }
            .firstOrNull { it.context.subPath.route == childSubPathToMatch.route }

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

interface DeepLinkable {
    fun checkDeepLinkMatch(path: Path): DeepLinkResult
    fun navigateUpToDeepLink(path: Path): DeepLinkResult
}

sealed class DeepLinkResult {
    object Success : DeepLinkResult()
    data class Error(val errorMsg: String) : DeepLinkResult()
}
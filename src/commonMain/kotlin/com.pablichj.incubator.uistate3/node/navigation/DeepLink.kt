package com.pablichj.incubator.uistate3.node.navigation

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

    override fun toString(): String {
        val stringBuilder = subPathList.fold(
            StringBuilder()
        ) { sb, subPath ->
            sb.append(subPath.route)
            sb.append("/")
        }
        return stringBuilder.toString()
    }
}

data class SubPath(val route: String) {
    companion object {
        val Root = SubPath("Root")
        val Empty = SubPath("Empty")
    }
}
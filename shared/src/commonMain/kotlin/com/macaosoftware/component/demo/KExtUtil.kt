package com.macaosoftware.component.demo

inline fun <T> T?.ifNotNull(block: (T) -> Unit): T? {
    if (this != null) {
        block(this)
    }
    return this
}

inline fun <T> T.elseIfNull(block: () -> Unit) {
    if (this == null) { block() }
}

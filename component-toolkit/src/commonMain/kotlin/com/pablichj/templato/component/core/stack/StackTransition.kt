package com.pablichj.templato.component.core.stack

import com.pablichj.templato.component.core.Component

sealed interface StackTransition<T : Component> {
    class In<T : Component>(val newTop: T) : StackTransition<T>
    class InOut<T : Component>(val newTop: T, val oldTop: T) : StackTransition<T>
    class Out<T : Component>(val oldTop: T) : StackTransition<T>
    class InvalidPushEqualTop<T : Component> : StackTransition<T>
    class InvalidPopEmptyStack<T : Component> : StackTransition<T>
}

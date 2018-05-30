package com.intervalintl.workflow


abstract class ViewFlow<I: Any, O: Flow.Event> : Flow<I, O> {

    protected lateinit var viewBox: ViewBox

    constructor(id: String) : super(id)

    constructor(id: String, children: MutableList<Flow<*, *>>) : super(id, children)


    //public abstract <VM : BaseViewModel> VM provideViewModel(CoordinatedFragment coordinatedFragment)

    fun bind() = 0



}
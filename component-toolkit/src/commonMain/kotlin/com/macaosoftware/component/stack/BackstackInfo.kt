package com.macaosoftware.component.stack

class BackstackInfo {
    /*
    * When we pop the last element of the stack, we don't remove it from the stack
    * because doing so creates a bad visual effect. Instead, we leave the element in
    * the stack but set this flag to true to indicate that the element in the top
    * is the leftover of the previous navigation and is safe to be replaced if needed.
    * */
    var isTopComponentStaled: Boolean = false
}
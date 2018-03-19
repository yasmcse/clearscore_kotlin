package com.example.yasirnazir.clearscore.models

/**
 * Created by yasirnazir on 3/14/18.
 */

class CoachingSummary {
    var selected: String? = null

    var numberOfTodoItems: String? = null

    var activeChat: String? = null

    var activeTodo: String? = null

    var numberOfCompletedTodoItems: String? = null

    override fun toString(): String {
        return "ClassPojo [selected = $selected, numberOfTodoItems = $numberOfTodoItems, activeChat = $activeChat, activeTodo = $activeTodo, numberOfCompletedTodoItems = $numberOfCompletedTodoItems]"
    }
}

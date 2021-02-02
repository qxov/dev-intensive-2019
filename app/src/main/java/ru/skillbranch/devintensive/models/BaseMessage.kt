package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils
import java.util.Date

abstract class BaseMessage(
        val id: String,
        val from: User?,
        val chat: Chat,
        val isIncoming: Boolean = false,
        val date: Date = Date()
) {
    abstract fun formatMessage(): String

    companion object AbstractFactory {
        fun makeMessage(from: User?, chat: Chat, date: Date = Date(), type: String = "text",
                        payload: Any? = null, isIncoming: Boolean = false) : BaseMessage {
            return when(type) {
                "text" -> TextMessage(Utils.generateId(), from, chat, isIncoming, date, payload as String)
                else -> ImageMessage(Utils.generateId(), from, chat, isIncoming, date, payload as String)
            }
        }
    }
}

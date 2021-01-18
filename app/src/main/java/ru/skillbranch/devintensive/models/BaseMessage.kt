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

/*
и абстрактный метод formatMessage() - возвращает строку содержащюю

информацию о id сообщения, имени получателя/отправителя,
виде сообщения ("получил/отправил") и типе сообщения ("сообщение"/"изображение")

Реализуй паттерн AbstractFactory с методом makeMessage(from, chat, date, type, payload, isIncoming = false) принимающий в качесте аргументов пользователя создавшего сообщение, чат к которому относится сообщение, дата сообщения и его тип ("text/image"), полезную нагрузку
Пример:
BaseMessage.makeMessage(user, chat, date, "any text message", "text") //Василий отправил сообщение "any text message" только что
BaseMessage.makeMessage(user, chat, date, "https://anyurl.com", "image",true) //Василий получил изображение "https://anyurl.com" 2 часа назад
*/
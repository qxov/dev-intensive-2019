package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils
import ru.skillbranch.devintensive.utils.Utils.parseFullName
import java.util.Date

data class User(
        val id: String,
        var firstName: String?,
        var lastName: String?,
        var avatar: String?,
        var rating: Int = 0,
        var respect: Int = 0,
        var lastVisit: Date? = Date(),
        var isOnline: Boolean = false
) {
    companion object Factory {
       fun makeUser(fullName: String? = null): User {
            val (firstName: String?, lastName: String?) = parseFullName(fullName)

            return User(Utils.generateId(), firstName, lastName, "avatars:empty")
        }
    }

    class Builder() {
        private var userId: String? = null
        private var userFirstName: String? = null
        private var userLastName: String? = null
        private var userAvatar: String? = null
        private var userRating: Int = 0
        private var userRespect: Int = 0
        private var userLastVisit: Date? = Date()
        private var userIsOnline: Boolean = false

        private var isLocked = false

        private fun checkState() {
            if (isLocked)
                throw IllegalStateException("Попытка изменить параметры после построения объекта")
        }

        fun id(userId: String): Builder {
            checkState()
            this.userId = userId
            return this
        }

        fun firstName(userFirstName: String): Builder {
            checkState()
            this.userFirstName = userFirstName
            return this
        }

        fun lastName(userLastName: String): Builder {
            checkState()
            this.userLastName = userLastName
            return this
        }

        fun avatar(userAvatar: String): Builder {
            checkState()
            this.userAvatar = userAvatar
            return this
        }

        fun rating(userRating: Int): Builder {
            checkState()
            this.userRating = userRating
            return this
        }

        fun respect(userRespect: Int): Builder {
            checkState()
            this.userRespect = userRespect
            return this
        }

        fun lastVisit(userLastVisit: Date): Builder {
            checkState()
            this.userLastVisit = userLastVisit
            return this
        }

        fun isOnline(userIsOnline: Boolean): Builder {
            checkState()
            this.userIsOnline = userIsOnline
            return this
        }

        fun build(): User {
            isLocked = true
            if (userId == null)
                userId = Utils.generateId()

            return User(userId!!, userFirstName, userLastName, userAvatar, userRating, userRespect,
                    userLastVisit, userIsOnline)
        }

    }
}
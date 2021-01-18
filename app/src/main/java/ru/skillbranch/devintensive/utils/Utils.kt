package ru.skillbranch.devintensive.utils

import java.util.*

object Utils {
    private var lastGeneratedId : Int = -1
    fun generateId() : String = "UtilsId[${++lastGeneratedId}]"

    fun parseFullName(fullName: String?): Pair<String?, String?> {
        var trimmedFullName = fullName?.trim()
        if (trimmedFullName?.length == 0) {
            trimmedFullName = null
        }

        val parts: List<String>? = trimmedFullName?.trim()?.split(" ")
        val firstName: String? = parts?.getOrNull(0)
        val lastName: String? = parts?.getOrNull(1)

        return Pair(firstName, lastName)
    }

    private val translitMap: Map<Char, String>

    init {
        val rus = "абвгдеёзийклмнопрстуфхцыэАБВГДЕЁЗИЙКЛМНОПРСТУФХЦЫЭ"
        val eng = "abvgdeeziiklmnoprstufhcieABVGDEEZIIKLMNOPRSTUFHCIE"

        translitMap = mutableMapOf('ь' to "", 'Ь' to "", 'ъ' to "", 'Ъ' to "")
        translitMap += 'ж' to "zh"
        translitMap += 'ч' to "ch"
        translitMap += 'ш' to "sh"
        translitMap += 'щ' to "sh'"
        translitMap += 'ю' to "yu"
        translitMap += 'я' to "ya"

        translitMap += 'Ж' to "Zh"
        translitMap += 'Ч' to "Ch"
        translitMap += 'Ш' to "Ss"
        translitMap += 'Щ' to "Sh'"
        translitMap += 'Ю' to "Yu"
        translitMap += 'Я' to "Ya"

        for (i in rus.indices) {
            translitMap += rus[i] to "${eng[i]}"
        }
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val buffer: StringBuilder = StringBuilder()

        for (ch in payload) {
            if (ch == ' ') {
                buffer.append(divider)
            } else {
                buffer.append(translitMap.get(ch) ?: "$ch")
            }
        }

        return buffer.toString()
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val firstChars: String = ("${firstName?.trim()?.getOrNull(0) ?: ' '}" +
                "${lastName?.trim()?.getOrNull(0) ?: ' '}").trim().toUpperCase(Locale.US)

        return if (firstChars.length > 0) firstChars else null
    }
}
package ru.skillbranch.devintensive.extensions

fun String.truncate(len : Int = 16) : String {
    val trimmed = this.trimEnd()

    return if (trimmed.length <= len) trimmed else "${trimmed.substring(0,len).trimEnd()}..."
}

fun String.stripHtml() : String {
    fun killSpaces(a : String) = Regex("""[ ]+""").replace(a, " ").trim()
    fun killTags(a : String) = Regex("""(<[^>]*>)""").replace(a," ")

    return killSpaces(killTags(this))
}
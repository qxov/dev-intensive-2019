package ru.skillbranch.devintensive.models

class Bender(
    var status: Status = Status.NORMAL,
    var question: Question = Question.NAME
) {
    var tryCnt : Int = 0

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
            override fun answerTip(answer: String): Pair<Boolean, String> {
                return if (answer.isNotEmpty() && answer[0].isUpperCase()) {
                    Pair(true, "")
                } else {
                    Pair(false, "Имя должно начинаться с заглавной буквы")
                }
            }
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
            override fun answerTip(answer: String): Pair<Boolean, String> {
                return if (answer.isNotEmpty() && answer[0].isLowerCase()) {
                    Pair(true, "")
                } else {
                    Pair(false, "Профессия должна начинаться со строчной буквы")
                }
            }
        },

        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "iron", "wood", "metal")) {
            override fun nextQuestion(): Question = BDAY
            override fun answerTip(answer: String): Pair<Boolean, String> {
                return if (answer.isNotEmpty() && !answer.contains(Regex("[0-9]"))) {
                    Pair(true, "")
                } else {
                    Pair(false, "Материал не должен содержать цифр")
                }
            }
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
            override fun answerTip(answer: String): Pair<Boolean, String> {
                return if (answer.isNotEmpty() && !answer.contains(Regex("[^0-9]"))) {
                    Pair(true, "")
                } else {
                    Pair(false, "Год моего рождения должен содержать только цифры")
                }
            }
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
            override fun answerTip(answer: String): Pair<Boolean, String> {
                return if (answer.length == 7 && !answer.contains(Regex("[^0-9]"))) {
                    Pair(true, "")
                } else {
                    Pair(false, "Серийный номер содержит только цифры, и их 7")
                }
            }
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
            override fun answerTip(answer: String): Pair<Boolean, String> = Pair(true, "")
        };

        abstract fun nextQuestion(): Question
        abstract fun answerTip(answer: String): Pair<Boolean, String>
    }

    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        val (valid, tip) = question.answerTip(answer)

        if (!valid) {
            return Pair("$tip\n${question.question}", status.color)
        }

        if (question == Question.IDLE)
            return Pair(question.question, status.color)

        return if (question.answers.contains(answer.toLowerCase())) {
            question = question.nextQuestion()
            //tryCnt = 0
            Pair("Отлично - ты справился\n${question.question}", status.color)
        } else {
            tryCnt++
            if(tryCnt <= 3) {
                status = status.nextStatus()
                Pair("Это неправильный ответ\n${question.question}", status.color)
            } else {
                status = Status.NORMAL
                question = Question.NAME
                tryCnt = 0
                Pair("Это неправильный ответ. Давай все по новой\n${question.question}", status.color)
            }
        }
    }
}

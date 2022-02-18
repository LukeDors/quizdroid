package edu.washington.quizdroid


interface TopicRepository {
    fun getTopics(): List<Topic>
    fun getTopicString(index: Int): Topic
    fun addTopic(topic: Topic)
}

data class Topic(val title: String, var description: String, var questions: List<Quiz>)

data class Quiz(val question: String, val options: List<String>, val answer: Int)
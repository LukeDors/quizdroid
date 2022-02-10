package edu.washington.quizdroid

import android.util.Log
import org.json.JSONArray
import java.io.File
import java.io.FileInputStream

class Data : TopicRepository {
    private val topicList: MutableList<Topic> = mutableListOf()

    init{
        val file = File("/sdcard/questions.json")
        if(file.exists()) {
            val inputStream = FileInputStream(file)
            Log.i("file", "we found the file")
            val text = inputStream.readBytes().toString(Charsets.UTF_8)
            val data = JSONArray(text)
            val physicsData = data.getJSONObject(0)
            val mathData = data.getJSONObject(1)
            val marvelData = data.getJSONObject(2)
            val physicsQ = physicsData.getJSONArray("questions")
            val physicsQuizzes = arrayListOf<Quiz>()
            for (i in (0..4)) {
                val question = physicsQ.getJSONObject(i)
                val options = question.getJSONArray("answers")
                val answers = arrayListOf<String>()
                for (j in (0..3)) {
                    answers.add(options[j].toString())
                }
                physicsQuizzes.add(Quiz(question.getString("text"), answers, question.getInt("answer")))
            }
            val mathQ = mathData.getJSONArray("questions")
            val mathQuizzes = arrayListOf<Quiz>()
            for (i in (0..4)) {
                val question = mathQ.getJSONObject(i)
                val options = question.getJSONArray("answers")
                val answers = arrayListOf<String>()
                for (j in (0..3)) {
                    answers.add(options[j].toString())
                }
                mathQuizzes.add(Quiz(question.getString("text"), answers, question.getInt("answer")))
            }
            val marvelQ = physicsData.getJSONArray("questions")
            val marvelQuizzes = arrayListOf<Quiz>()
            for (i in (0..4)) {
                val question = marvelQ.getJSONObject(i)
                val options = question.getJSONArray("answers")
                val answers = arrayListOf<String>()
                for (j in (0..3)) {
                    answers.add(options[j].toString())
                }
                marvelQuizzes.add(Quiz(question.getString("text"), answers, question.getInt("answer")))
            }
        val physics = Topic(
            physicsData.getString("title"),
            physicsData.getString("desc"),
            physicsQuizzes
        )
        addTopic(physics)
        val math = Topic(
            mathData.getString("title"),
            mathData.getString("desc"),
            mathQuizzes
        )
        addTopic(math)
        val marvel = Topic(
            marvelData.getString("title"),
            marvelData.getString("desc"),
            marvelQuizzes
        )
        addTopic(marvel)
        }
//        val physicsQuizzes = listOf(
//            Quiz("What is the SI Unit for mass?", listOf("Pounds", "Weight", "Kilograms", "None of the Above"), 2),
//            Quiz("What physicist is famous for their cat?", listOf("Albert Einstein", "Erwin Schrodinger", "Isaac Newton", "None of the Above"), 1),
//            Quiz("What are the names for Isaac Newton\\'s three most famous physics laws?", listOf("Laws of Gravity", "Laws of Motion", "Laws of Inertia", "None of the Above"), 2),
//            Quiz("What is the SI Unit for heat?", listOf("Joules", "Degrees", "Watts", "None of the Above"), 0),
//            Quiz("Approximately how long does it take for light to travel to the Earth?", listOf("3 Minutes", "8 Minutes", "1 Hour", "None of the Above"), 1)
//        )
//        val physics = Topic(
//            "Physics",
//            "This set of 5 questions will test your basic physics knowledge, high school level and below",
//            physicsQuizzes
//        )
//        val mathQuizzes = listOf(
//            Quiz("What is the sum (in degrees) of the interior angles of a triangle?", listOf("180", "270", "360", "None of the Above"), 0),
//            Quiz("How many sides does a nonagon have?", listOf("7", "9", "12", "None of the Above"), 1),
//            Quiz("Which two values give distance when multiplied together?", listOf("Time and Mass", "Rate and Time", "Rate and Velocity", "None of the Above"), 1),
//            Quiz("Which of these would give the angle on a given right triangle after dividing the length of the side opposite the angle and the hypotenuse?", listOf("Sin", "Cos", "Tan", "None of the Above"), 0),
//            Quiz("What is the commonly used name for base two?", listOf("Base 2", "Tertiary", "Binary", "None of the Above"), 2)
//        )
//        val math = Topic(
//            "Math",
//            "This set of 5 questions will test your math skills, from algebra 2 and below",
//            mathQuizzes
//        )
//        val marvelQuizzes = listOf(
//            Quiz("What member of the X-men famously has bones made of adamantium?", listOf("Wolverine", "Colossus", "Cyclops", "None of the Above"), 0),
//            Quiz("Which of these were a member of the Avengers?", listOf("Storm", "Hawkeye", "Jean Grey", "None of the Above"), 1),
//            Quiz("Who was the most common villain who fought the Fantastic Four?", listOf("Dr. Strange", "Dr. Doom", "Thanos", "None of the Above"), 1),
//            Quiz("Which of these heroes is the oldest?", listOf("Captain America", "Tony Stark", "Professor X", "None of the Above"), 0),
//            Quiz("What was the name of Peter Parker\\'s late uncle?", listOf("Uncle Pete", "Uncle Sam", "Uncle Ben", "None of the Above"), 2)
//        )
//        val marvel = Topic(
//            "Marvel Superheroes",
//            "This set of 5 questions about Marvel superheroes will help you find out if you are a TRUE comic book nerd",
//            marvelQuizzes
//        )
    }

    override fun getTopics() : List<Topic> {
        return topicList
    }

    override fun getTopicString(index: Int) : Topic {
        return topicList[index]
    }

    override fun addTopic(topic: Topic) {
        topicList.add(topic)
    }
}
package com.example.finalprojectwithanimation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import kotlin.random.Random

/**
Fragment for Running the Quiz Game:
 */
class GameFragment : Fragment() {

    //Facts ArrayList:
    private val factsList = ArrayList<Fact>()
    //List of the Fact indexes:
    private val randomIndexes = mutableListOf<Int>()
    private var currentFactIndex = 0

    //XML stuff:
    private lateinit var factTextView: TextView
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var questionNumber: TextView

    //Keep track of the User's progress:
    private var totalCorrect: Int = 0
    private val improvementList = ArrayList<Fact>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        //locate the buttons and labels:
        factTextView = view.findViewById(R.id.factTextView)
        trueButton = view.findViewById(R.id.true_option)
        falseButton = view.findViewById(R.id.false_option)
        progressBar = view.findViewById(R.id.progressBar)
        questionNumber = view.findViewById(R.id.questionNumber)
        //check answer is a method which checks whether the use has answered the question correctly or not:
        trueButton.setOnClickListener { checkAnswer(true) }
        falseButton.setOnClickListener { checkAnswer(false) }


        //calling the methods:
        addFacts()
        generateRandomIndexes()
        showNextFact()

        return view
    }

    //generate the 10 random fact questions:
    private fun generateRandomIndexes() {
        val totalFacts = factsList.size
        val usedIndexes = mutableSetOf<Int>()//set can not contain repeated numbers
        //generate indexes until 10 different indexes appear:
        while (randomIndexes.size < 10) {
            val randomIndex = Random.nextInt(totalFacts)
            if (!usedIndexes.contains(randomIndex)) {
                randomIndexes.add(randomIndex)
                usedIndexes.add(randomIndex)
            }
        }
    }

    private fun addFacts() {
        //facts:
        factsList.add(Fact("Badgers can eat several hundred earthworms a night","Yes, badgers can eat several hundred earthworms a night!", true))
        factsList.add(Fact("Bats can live for up to 30 years","Yes, bats can live for up to 30 years!", true))
        factsList.add(Fact("Only the female Bees can sting","Yes, only the female Bees can sting!", true))
        factsList.add(Fact("Beetles are the smallest group of insects","No, Beetles are the largest group of insects with over 350,000 species.", false))
        factsList.add(Fact("Butterflies can see ultraviolet light.", "Yes, butterflies can see ultraviolet light.", true))
        factsList.add(Fact("Cats have nine lives.", "No, cats only have one life like any other animal.", false))
        factsList.add(Fact("Caterpillars can eat their own weight in food every day.", "Yes, caterpillars have voracious appetites and can consume a lot of food relative to their size.", true))
        factsList.add(Fact("Cockroaches can survive a nuclear explosion.","No, this is a myth. Cockroaches are resistant to radiation, but they would not survive a nuclear blast.", false))

        factsList.add(Fact("Cow's milk is naturally blue.", "No, cow's milk is not naturally blue. It is white in color.", false))
        factsList.add(Fact("Crabs have teeth in their stomachs.", "Yes, crabs have teeth in their stomachs to help them grind food.", true))
        factsList.add(Fact("Crows can recognize human faces.", "Yes, crows have been shown to be able to recognize individual human faces.", true))
        factsList.add(Fact("Deer are colorblind.", "No, deer can see colors, although their color vision is not as sharp as humans.", false))
        factsList.add(Fact("Dogs sweat through their tongues.", "No, dogs primarily sweat through their paw pads.", false))
        factsList.add(Fact("Donkeys can live up to 50 years.", "Yes, donkeys have a long lifespan and can live for several decades.", true))
        factsList.add(Fact("Dragonflies have a lifespan of just 24 hours.", "No, while some dragonflies have short lifespans, they typically live for several weeks or months.", false))
        factsList.add(Fact("Ducks can sleep with one eye open.", "Yes, ducks can sleep with one eye open to watch for predators.", true))

        factsList.add(Fact("Eagles can fly at speeds of up to 200 miles per hour.", "No, while eagles are fast flyers, they do not reach speeds of 200 miles per hour.", false))
        factsList.add(Fact("Flies can taste with their feet.", "Yes, flies can taste with their feet, which helps them identify potential food sources.", true))
        factsList.add(Fact("Foxes are nocturnal animals.", "No, foxes are crepuscular, meaning they are most active during dawn and dusk.", false))

        factsList.add(Fact("Goats have rectangular pupils.", "Yes, goats have rectangular pupils, which provide them with a wide field of vision.", true))
        factsList.add(Fact("Geese mate for life.", "Yes, geese are known for forming strong pair bonds and often mate for life.", true))

        factsList.add(Fact("Grasshoppers can jump up to 20 times their body length.", "Yes, grasshoppers are powerful jumpers and can cover significant distances with their jumps.", true))
        factsList.add(Fact("Hamsters are rodents.", "Yes, hamsters belong to the rodent family and are small mammals with large cheek pouches.", true))
        factsList.add(Fact("Hares are born with fur and open eyes.", "Yes, hares are precolonial animals, meaning they are born relatively mature.", true))
        factsList.add(Fact("Hedgehogs are immune to snake venom.", "No, while hedgehogs have some resistance to certain snake venom, they are not immune.", false))
        factsList.add(Fact("Horses can sleep while standing up.", "Yes, horses can lock their knees to sleep while standing up.", true))
        factsList.add(Fact("Ladybugs are poisonous.", "No, ladybugs are not poisonous to humans, although they may emit a foul odor as a defense mechanism.", false))
        factsList.add(Fact("Lobsters can regenerate lost limbs.", "Yes, lobsters have the ability to regenerate lost limbs, although the process is slow.", true))
        factsList.add(Fact("Mosquitoes are attracted to the color blue.", "No, mosquitoes are more attracted to dark colors like black and red.", false))
        factsList.add(Fact("Moths are more active during the day than at night.", "No, moths are primarily nocturnal and are more active at night.", false))
        factsList.add(Fact("Mice have poor eyesight.", "Yes, mice have relatively poor eyesight, but they have keen senses of hearing and smell.", true))
        factsList.add(Fact("Otters hold hands while sleeping to avoid drifting apart.", "Yes, otters often hold hands while sleeping to stay together in groups.", true))
        factsList.add(Fact("Owls can rotate their heads 360 degrees.", "No, while owls have flexible necks, they cannot rotate their heads a full 360 degrees.", false))
        factsList.add(Fact("Parrots can mimic human speech.", "Yes, parrots are known for their ability to mimic human speech and other sounds.", true))
        factsList.add(Fact("Pigs are among the cleanest animals.", "No, pigs are actually quite dirty animals and often roll in mud to stay cool.", false))

        factsList.add(Fact("Pigeons can recognize themselves in a mirror.", "Yes, pigeons have been shown to demonstrate self-recognition in mirror tests.", true))
        factsList.add(Fact("Raccoons wash their food before eating.", "Yes, raccoons are known to dunk their food in water before eating it.", true))
        factsList.add(Fact("Rats can't vomit.", "Yes, rats are unable to vomit due to the structure of their digestive system.", true))
        factsList.add(Fact("Sheep have excellent memories.", "No, sheep have relatively poor memories and are often perceived as forgetful animals.", false))
        factsList.add(Fact("Snakes can dislocate their jaws to swallow large prey.", "Yes, snakes have flexible jaws that allow them to swallow prey much larger than their head.", true))
        factsList.add(Fact("Sparrows are monogamous birds.", "Yes, sparrows often mate for life and form monogamous pairs.", true))
        factsList.add(Fact("Squirrels hibernate during the winter.", "No, while squirrels may become less active during winter, they do not hibernate in the same way as some other animals.", false))
        factsList.add(Fact("Swans are native to South America.", "No, swans are native to Europe and Asia, not South America.", false))
        factsList.add(Fact("Turkeys can run at speeds of up to 25 miles per hour.", "Yes, turkeys are fast runners and can reach speeds of up to 25 miles per hour.", true))
        factsList.add(Fact("Turtles can breathe through their skin.", "Yes, turtles have the ability to absorb oxygen through their skin, in addition to breathing through their lungs.", true))
        factsList.add(Fact("Woodpeckers can peck up to 20 times per second.", "Yes, woodpeckers are known for their rapid pecking, which can reach speeds of up to 20 times per second.", true))
    }

    //Display the facts:
    private fun showNextFact() {
        if (currentFactIndex < randomIndexes.size) {
            val factIndex = randomIndexes[currentFactIndex]
            val fact = factsList[factIndex]
            factTextView.text = fact.text//display the new fact
            progressBar.progress = currentFactIndex + 1
            //Current Question number:
            questionNumber.text="Question ${currentFactIndex + 1}"
            currentFactIndex++
        } else {
            //All questions answered:
            //Navigate to OutcomeFragment with the incorrect facts and score:
            val outcomeFragment = OutcomeFragment.newInstance(improvementList, totalCorrect)
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout, outcomeFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    //Check if the user has answered the question correctly or not:
    private fun checkAnswer(userAnswer: Boolean) {
        val factIndex = randomIndexes[currentFactIndex - 1]
        val fact = factsList[factIndex]
        //Correct answer:
        if (fact.isTrue == userAnswer) {
            totalCorrect +=1
        }
        //Wrong answer:
        else {
            improvementList.add(fact)//add fact
        }
        //Show the next fact
        showNextFact()
    }
}
package com.olvera.unscramble.ui.game

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.olvera.unscramble.R
import com.olvera.unscramble.databinding.FragmentGameBinding
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * A simple [Fragment] subclass.
 * Fragment where the game is played, contains the game logic
 */
class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding

    // Create a ViewModel the first time the fragment is created
    // If the fragment is re-created, it receives the same GameViewModel instance created by the
    //First fragment
    private val viewModel: GameViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGameBinding.inflate(inflater, container, false)
        Log.d("GameFragment", "GameFragment created/re-created!")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup a click listener for the submit and skip buttons
        binding.apply {
            submit.setOnClickListener { onSubmitWord() }
            skip.setOnClickListener { onSkipWord() }
        }

        // Update the UI
        updateNextWordOnScreen()
        binding.apply {
            score.text = getString(R.string.score, 0)
            wordCount.text = getString(R.string.word_count, 0, MAX_NO_OF_WORDS)
        }


    }

    override fun onDetach() {
        super.onDetach()
        Log.d("GameFragment", "GameFragment destroyed!")
    }

    /**
     * Checks the user's word, and updates the score accordingly
     * Displays the next scrambled word.
     */
    private fun onSubmitWord() {

    }

    /**
     * Skips the current word without changing the score
     * increases the word count
     */
    private fun onSkipWord() {

    }

    /**
     * Gets a random word for the list of words and shuffles the letters in it
     */

    private fun getNextScrambledWord(): String {
        val tempWord = allWordsList.random().toCharArray()
        tempWord.shuffle()
        return String(tempWord)
    }


    /**
     * Re-initializes the data in the ViewModel and updates the views with the new data, to
     * restart the game.
     */
    private fun restartGame() {
        setErrorTextField(false)
        updateNextWordOnScreen()
    }

    /**
     * Exits the game
     */
    private fun exitGame() {
        activity?.finish()
    }

    /**
     * Sets and resets the text field error status
     */
    private fun setErrorTextField(error: Boolean) {
        if (error){
            binding.textField.isErrorEnabled = true
            binding.textField.error = getString(R.string.try_again)
        } else {
            binding.textField.isErrorEnabled = false
            binding.textInputEditText.text = null
        }
    }

    /**
     * Displays the next scrambled word on screen
     */
    private fun updateNextWordOnScreen(){
        binding.textViewUnscrambledWord.text = viewModel.currentScrambleWord
    }

    /**
     * Creates and Shows an AlertDialog with the final score
     */
    private fun showFinalScoreDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.congratulations)
            .setMessage(getString(R.string.you_scored, viewModel.score))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.exit)) {_,_ ->
                exitGame()
            }
            .setPositiveButton(getString(R.string.play_again)) {_, _ ->
                restartGame()
            }
            .show()
    }
}
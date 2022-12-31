package app.codinguyy.stateflowexamples

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import app.codinguyy.stateflowexamples.databinding.FragmentFirstBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.system.measureTimeMillis

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private val viewModel by viewModel<FirstFragmentViewModel>()
    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.stateFlow.collectLatest {
                binding.textviewThree.text = it
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.sharedFlow.collectLatest {
                binding.textviewThree.text = it
            }
        }

        /**
         * Coroutine - Stateflow is hot flow - When the fragment is recreated it holds the new value
         * emit the value when the activity is recreated, even its the same value
         */
        binding.buttonThree.setOnClickListener {
            viewModel.triggerStateflow()
        }

        /**
         * Coroutine - Flow is cold flow - When the fragment is recreated it doesnt emit the value
         *
         */
        binding.buttonFour.setOnClickListener {
            lifecycleScope.launch {
                viewModel.triggerFlow().collectLatest {
                    binding.textviewThree.text = it
                }
            }
        }

        /**
         * Coroutine - SharedFlow - sends a one time event, which is not reemitted by screenrotation
         */

        binding.buttonFive.setOnClickListener {
            viewModel.triggerSharedFlow()
        }

        CoroutineScope(Dispatchers.IO).launch {
            val time = measureTimeMillis {
                val answer1 = async {
                    networkCall1()
                }
                val answer2 = async {
                    networkCall2()
                }

                Log.d("Answer", "${answer1.await()} ")
                Log.d("Answer", " ${answer2.await()} ")
            }
            Log.d("test", "time is $time ms")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Couroutine suspend await
     */
    suspend fun networkCall1(): String {
        delay(3500L)
        return "Answer 1"
    }

    suspend fun networkCall2(): String {
        delay(3000L)
        return "Answer 2"
    }
}

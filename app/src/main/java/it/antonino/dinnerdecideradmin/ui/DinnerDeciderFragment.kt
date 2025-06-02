package it.antonino.dinnerdecideradmin.ui

import android.content.Context
import android.hardware.input.InputManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import it.antonino.dinnerdecideradmin.databinding.DinnerdeciderFragmentBinding
import it.antonino.dinnerdecideradmin.model.Food
import it.antonino.dinnerdecideradmin.model.FoodListAdapter
import it.antonino.dinnerdecideradmin.viewmodel.DinnerDeciderAdminViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class DinnerDeciderFragment: Fragment() {

    private lateinit var binding: DinnerdeciderFragmentBinding
    private var foodList: ArrayList<String> = arrayListOf()
    private lateinit var itemsAdapter: FoodListAdapter
    private var foodArray: ArrayList<Food> = arrayListOf()
    private val viewModel: DinnerDeciderAdminViewModel by activityViewModel<DinnerDeciderAdminViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DinnerdeciderFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.requestFocus()

        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        viewModel.getAll().observe(viewLifecycleOwner) {
            if (it?.isJsonNull == false) {
                foodArray = parseFoodList(it)
                foodList = getFoodName(foodArray)
                itemsAdapter = FoodListAdapter(requireContext(), foodList)
                binding.foodList.adapter = itemsAdapter
            }
        }

        binding.insertFoodBtn.setOnClickListener {
            if (binding.insertFoodEditTxt.text.isBlank())
                Toast.makeText(requireContext(), "Please insert a food", Toast.LENGTH_SHORT).show()
            else {
                val food = Food(
                    binding.insertFoodEditTxt.text.toString(),
                    0
                )
                viewModel.insertFood(
                    food
                ).observe(viewLifecycleOwner) {
                    binding.insertFoodEditTxt.text = null
                    if (it?.isJsonNull == false) {
                        Toast.makeText(requireContext(), "Food inserted..", Toast.LENGTH_SHORT)
                            .show()
                        foodArray.add(food)
                        foodList.add(food.name)
                        itemsAdapter = FoodListAdapter(requireContext(), foodList)
                        binding.foodList.adapter = itemsAdapter
                    } else
                        Toast.makeText(
                            requireContext(),
                            "Ops.. a problem occured",
                            Toast.LENGTH_SHORT
                        ).show()
                }
            }
        }

        binding.decideFoodBtn.setOnClickListener {
            if (itemsAdapter.selectedPosition != -1) {
                viewModel.voteFood(foodArray.get(itemsAdapter.selectedPosition))
                    .observe(viewLifecycleOwner) {
                        if (it == 200) {
                            binding.insertFoodBtn.isEnabled = false
                            binding.decideFoodBtn.text = "Reset"
                            binding.showVotedFoodTxt.visibility = View.VISIBLE
                            binding.showVotedFoodTxt.setOnClickListener {
                                viewModel.getVoted().observe(viewLifecycleOwner) {
                                    if (it?.isJsonNull == false) {
                                        binding.showVotedFoodTxt.text = it?.get("name")?.asString
                                        binding.decideFoodBtn.setOnClickListener {
                                            viewModel.reset().observe(viewLifecycleOwner) {
                                                if (it == 200) {
                                                    binding.decideFoodBtn.isEnabled = false
                                                } else {
                                                    Toast.makeText(requireContext(), "Cannot reset foods counter...", Toast.LENGTH_LONG).show()
                                                }
                                            }
                                        }
                                    } else {
                                        Toast.makeText(requireContext(), "Ops.. a problem occurred !", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(requireContext(), "Ops.. a problem occurred !", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(requireContext(), "Please select a Food !!", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun parseFoodList(jsonArray: JsonArray): ArrayList<Food> {
        val gson = Gson()
        val listType = object : TypeToken<ArrayList<Food>>() {}.type
        return gson.fromJson(jsonArray, listType)
    }

    private fun getFoodName(foodList: ArrayList<Food>): ArrayList<String> {
        val ret: ArrayList<String> = arrayListOf()
        foodList.forEach {
            ret.add(it.name)
        }
        return ret
    }

}
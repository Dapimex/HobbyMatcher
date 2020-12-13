package com.madness.hobbymatcher.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.madness.hobbymatcher.HobbyMatcherApplication
import com.madness.hobbymatcher.R
import com.madness.hobbymatcher.networking.ActivityService
import com.madness.hobbymatcher.networking.response.Activity
import kotlinx.android.synthetic.main.fragment_add_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AddActivityFragment : Fragment() {

    @Inject
    lateinit var activityService: ActivityService

    private val frontSdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    private val backSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (HobbyMatcherApplication.APPLICATION as HobbyMatcherApplication).appComponent.inject(this)
        return inflater.inflate(R.layout.fragment_add_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addActivityTypeRadioGroup.check(R.id.addActivityMovieRadioButton)
        addActivitySaveButton.setOnClickListener(saveButtonClickListener)
    }

    private val saveButtonClickListener = { _: View ->
        if (validateFields()) {
            sendDataToApi()
        }
    }

    private fun validateFields(): Boolean {
        var valid = true
        if (addActivityNameEditText.text.isEmpty()) {
            addActivityNameEditText.error = getText(R.string.empty_field)
            valid = false
        }
        if (addActivityStartTimeEditText.text.isEmpty()) {
            addActivityStartTimeEditText.error = getText(R.string.empty_field)
            valid = false
        }
        if (addActivityPlaceEditText.text.isEmpty()) {
            addActivityPlaceEditText.error = getText(R.string.empty_field)
            valid = false
        }
        if (!addActivityStartTimeEditText.text.matches(Regex("^([0-3][0-9])/([0-1][0-9])/([0-9]{4})\\s([0-1][0-9]|[2][0-3]):([0-5][0-9])\$"))) {
            addActivityStartTimeEditText.error = getText(R.string.incorrect_date)
            valid = false
        }
        return valid
    }

    private fun sendDataToApi() {
        val type = when(addActivityTypeRadioGroup.checkedRadioButtonId) {
            R.id.addActivityMovieRadioButton -> "MOVIE"
            R.id.addActivityTabletopRadioButton -> "TABLETOP"
            R.id.addActivityWalkRadioButton -> "WALKING"
            R.id.addActivityOtherRadioButton -> "OTHER"
            else -> "OTHER"
        }

        val time = frontSdf.parse(addActivityStartTimeEditText.text.toString())
        val startTime = backSdf.format(time!!)
        val activity = Activity(
            name = addActivityNameEditText.text.toString(),
            description = addActivityDescriptionEditText.text.toString(),
            startTime = startTime,
            type = type,
            duration = addActivityDurationSpinner.selectedItem.toString(),
            location = addActivityPlaceEditText.text.toString(),
            isPublic = addActivityPublicCheckBox.isChecked
        )
        activityService.createActivity(activity).enqueue(object: Callback<Activity> {
            override fun onFailure(call: Call<Activity>, t: Throwable) {
                Toast.makeText(context, "Failed to upload activity: ${t.message}", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Activity>, response: Response<Activity>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Successfully created activity", Toast.LENGTH_SHORT).show()
                    view?.findNavController()?.navigate(R.id.action_menu_item_add_activity_to_menu_item_profile)
                } else {
                    Toast.makeText(context, "Failed to upload activity: $response", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}
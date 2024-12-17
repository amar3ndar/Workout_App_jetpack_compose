package com.example.workout_app_jetpack_compose

object Constants {

    //Workouts exercises
    fun workoutExerciseList():ArrayList<ExerciseModel>{
        val exerciseList = ArrayList<ExerciseModel>()

        val jumpingJack = ExerciseModel(1,"Jumping Jacks",R.drawable.ic_jumping_jacks,false,false)
        exerciseList.add(jumpingJack)

        val exerciseTwo = ExerciseModel(2,"Lunges",R.drawable.ic_lunge,false,false)
        exerciseList.add(exerciseTwo)

        val exerciseThree = ExerciseModel(3,"Plank",R.drawable.ic_plank,false,false)
        exerciseList.add(exerciseThree)

        val pushUps = ExerciseModel(4,"Push-ups",R.drawable.ic_push_up,false,false)
        exerciseList.add(pushUps)

        val pushUpsAndRotation = ExerciseModel(5,"Push-ups and Rotation",R.drawable.ic_push_up_and_rotation,false,false)
        exerciseList.add(pushUpsAndRotation)

        val sidePlank = ExerciseModel(6,"Side Plank",R.drawable.ic_side_plank,false,false)
        exerciseList.add(sidePlank)

        val squat = ExerciseModel(7,"Squat",R.drawable.ic_squat,false,false)
        exerciseList.add(squat)

        val stepUpOntoChair = ExerciseModel(8,"Step up onto chair",R.drawable.ic_step_up_onto_chair,false,false)
        exerciseList.add(stepUpOntoChair)

        val tricepsDipOnChair = ExerciseModel(9,"Triceps dip on chair",R.drawable.ic_triceps_dip_on_chair,false,false)
        exerciseList.add(tricepsDipOnChair)

        val wallSit = ExerciseModel(10,"Wall sit",R.drawable.ic_wall_sit,false,false)
        exerciseList.add(wallSit)

        val abdominalCrunch = ExerciseModel(11,"Abdominal Crunches",R.drawable.ic_abdominal_crunch,false,false)
        exerciseList.add(abdominalCrunch)

        val highKneesRunning = ExerciseModel(12,"High knee running in place",R.drawable.ic_high_knees_running_in_place,false,false)
        exerciseList.add(highKneesRunning)

        val cobraStretch = ExerciseModel(13,"Cobra Stretch",R.drawable.ic_bhujangasana,false,false)
        exerciseList.add(cobraStretch)

        return exerciseList
    }

    //Exercise for height increase
    fun yogaExerciseList(): ArrayList<ExerciseModel> {
        val exerciseList = ArrayList<ExerciseModel>()

        val jogging = ExerciseModel(14,"Jogging",R.drawable.ic_jogging,false,false)
        exerciseList.add(jogging)

        val jumpSquats = ExerciseModel(15,"Jump Squats",R.drawable.ic_jump_squat,false,false)
        exerciseList.add(jumpSquats)

        val paschimottanasana =
            ExerciseModel(16, "Paschimottanasana", R.drawable.ic_paschimotanasan, false, false)
        exerciseList.add(paschimottanasana)

        val halasana = ExerciseModel(17, "Halasana", R.drawable.ic_halasana, false, false)
        exerciseList.add(halasana)

        val chakrasana = ExerciseModel(18, "Chakrasana", R.drawable.ic_chakrasana, false, false)
        exerciseList.add(chakrasana)

        val tadasana = ExerciseModel(19, "Tadasana", R.drawable.ic_tadasana, false, false)
        exerciseList.add(tadasana)

        val pullUps = ExerciseModel(20, "Pull-ups", R.drawable.ic_pull_ups, false, false)
        exerciseList.add(pullUps)

        val bhujangasana =
            ExerciseModel(21, "Bhujangasana", R.drawable.ic_bhujangasana, false, false)
        exerciseList.add(bhujangasana)

        val catPose = ExerciseModel(22,"Cat Pose",R.drawable.ic_catpose, false, false)
        exerciseList.add(catPose)

        val balasana = ExerciseModel(23,"Balasana",R.drawable.ic_balasana,false,false)
        exerciseList.add(balasana)


        return exerciseList
    }


}
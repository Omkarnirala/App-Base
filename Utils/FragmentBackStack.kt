
//To Back-Stack Two Fragment
//e.g:  Fragment A - B - C - D now if we want to go back to screen From D to B 
//without breaking the flow then use below code.
// now the flow will be like when user press the back button: D - B - A.
val navController = findNavController()
val backStackCount = navController.backQueue.size
val desiredBackStackCount = backStackCount - 2
while (navController.backQueue.size > desiredBackStackCount) {
     navController.popBackStack()
}


//To Back-Stack One Fragment
val navController = findNavController()
val backStackCount = navController.backQueue.size
val desiredBackStackCount = backStackCount - 1
while (navController.backQueue.size > desiredBackStackCount) {
    navController.popBackStack()
}

//OR
findNavController().navigateUp()


//For Home Fragment
findNavController().popBackStack(R.id.newHomeFragment, false)


//use below for phone back button (Device Back button)
requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
     override fun handleOnBackPressed() {
	//use your own method on back pressed
        onBackPressed()
     }
})
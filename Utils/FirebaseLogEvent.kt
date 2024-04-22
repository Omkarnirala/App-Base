//Initilize
private lateinit var firebaseAnalytics: FirebaseAnalytics


//In initView Method
firebaseAnalytics = Firebase.analytics
firebaseAnalytics?.logEvent(FirebaseEvent.LAUNCHER_ACTIVITY) {
     param(FirebaseAnalytics.Param.SCREEN_NAME, mTag)
}


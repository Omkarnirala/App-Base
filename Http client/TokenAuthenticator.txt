/**
 * Authenticator to generate new auth token to requests.
 * This will invoke when we get 401 status code.
 * */
class TokenAuthenticator @Inject constructor(
    context: Context,
    private val tokenApi: ApiServices,
) : Authenticator {

    private val appContext = context.applicationContext
    private val prefHelper = PrefHelper(appContext)

    override fun authenticate(route: Route?, response: Response): Request? {

//        val accessToken = prefHelper.getString(Constant.PREF_TOKEN)

        return runBlocking {
            val responseNewToken = tokenApi.refreshToken("Bearer ${prefHelper.getString(Constant.PREF_TOKEN)}").execute()
            return@runBlocking when {
                responseNewToken.code() != 200 -> {
                    //Todo Authentication has been Expired now send the user to Login Screen
                    /**AuthManager().authExpiredAndGoLogin(AndroidApplication().getContext())*/
                    //                            val newAuthenticationRequest = originalRequest.newBuilder().build()
                    //                            chain.proceed(newAuthenticationRequest)
                    val error = responseNewToken.errorBody()?.string().toString()
                    val errorResponse: ErrorResponse = Gson().fromJson(error, ErrorResponse::class.java)
                    Log.i("TAG", "intercept: "+responseNewToken.body().toString()+"___"+responseNewToken.message()+ "___"+responseNewToken.code())
                    Log.i("TAG", "intercept__2: "+responseNewToken.raw())
                    /* Handler(Looper.getMainLooper()).post {
                                         Toast.makeText(appContext, "Your Session Has been Expired", Toast.LENGTH_LONG).show()
                                     }
                //                            prefHelper.clear()
                                     val intent = Intent(appContext, LoginActivity::class.java)
                                     intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                     appContext.startActivity(intent)*/
                    return@runBlocking null
                }
                else -> {
                    responseNewToken.body()?.token.let {
                        prefHelper.clearValue(Constant.PREF_TOKEN)
                        prefHelper.put(Constant.PREF_TOKEN, it!!)
                    }
        //                    val newAuthenticationRequest = originalRequest.newBuilder()
        //                        .addHeader("Authorization", "Bearer " + responseNewToken.body()?.token)
        //                        .build()
        //                    chain.proceed(newAuthenticationRequest)

                    response.request.newBuilder()
                        .addHeader("Authorization", "Bearer " + responseNewToken.body()?.token)
                        .build()
                }
            }


  /*          if (updatedToken != null) {
                response.request.newBuilder()
                    .header(
                        "Authorization",
                        "Bearer $updatedToken}"
                    )
                    .build()
            } else
                return@runBlocking null*/
        }
    }

    private suspend fun getUpdatedToken(): String {
        var newToken: String = ""
        val authTokenResponse = tokenApi.refreshToken(newToken).enqueue(object : Callback<RefreshToken> {
            override fun onResponse(
                call: Call<RefreshToken>,
                response: retrofit2.Response<RefreshToken>,
            ) {
                Log.d("TAG", "getUpdatedToken: " + response.body())
                if (response.code()==200){
                    newToken = response.body()!!.token
                    prefHelper.clear()
                    prefHelper.put(Constant.PREF_TOKEN, newToken)
                }
                if (response.code()==403){
                    newToken = "Forbidden"
                }

            }

            override fun onFailure(call: Call<RefreshToken>, t: Throwable) {
                Log.d("TAG", "getUpdatedToken: " + t.message)
            }

        })
        return newToken
    }

}

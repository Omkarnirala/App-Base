class RemoteDataSource {

    companion object {
        private const val BASE_URL = BuildConfig.BASE_URL
    }

    fun <Api : Any> buildApi(
        api: Class<Api>,
        context: Context
    ): Api {
        //val authenticator = TokenAuthenticator(context, buildTokenApi())
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }

    /**
     * Initialize OkhttpClient with our interceptor
     */
    private fun okhttpClient(context: Context): OkHttpClient {
        //ADD DISPATCHER WITH MAX REQUEST TO 1
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            //.addInterceptor(NewAuthInterceptor(context,buildTokenApi()))
            .addInterceptor(logging)
            //.dispatcher(dispatcher)
            .authenticator(TokenAuthenticator(context,buildTokenApi()))
            .addInterceptor(AuthInterceptor(context))
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private fun buildTokenApi(): ApiServices {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServices::class.java)
    }
}
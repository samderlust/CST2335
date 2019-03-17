package com.samderlust.androidlabs

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonReader
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_weather_forecast.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.FileOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL

class WeatherForecast : AppCompatActivity() {

    val API_KEY = "78713c8484cfdd065a271381a93bfaea"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_forecast)

        pBarWeather.visibility = View.VISIBLE

        ForecastQuery().execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=$API_KEY&mode=json&units=metric")
    }

    fun fileExistance(fname: String): Boolean{
        val file = baseContext.getFileStreamPath(fname)
        return file.exists()
    }

    inner class ForecastQuery: AsyncTask<String, Int, String>(){

        override fun doInBackground(vararg urls: String?): String {
            var url = urls[0]
            var input: InputStream
            var reader: BufferedReader
            var data1 =""
            var data2 = ""

            try {
                input = URL(url).openStream()
                reader = BufferedReader(InputStreamReader(input))
                data1= reader.readLine()
                publishProgress(25)

            }catch (e: Exception){
                e.printStackTrace()
            }

            url = "http://api.openweathermap.org/data/2.5/uvi?appid=$API_KEY&lat=45.348945&lon=-75.759389"
            try {
                input = URL(url).openStream()
                reader = BufferedReader(InputStreamReader(input))
                data2= reader.readLine()
                publishProgress(50)

            }catch (e: Exception){
                e.printStackTrace()
            }

            publishProgress(100)

            return "{data1: $data1, data2: $data2}"
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            pBarWeather.progress = values[0]!!
        }



        @SuppressLint("SetTextI18n")
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            try {
                val data1 = JSONObject(result).get("data1") as JSONObject
                val main = data1.get("main") as JSONObject
                val wind = data1.get("wind") as JSONObject
                val weather = data1.get("weather") as JSONArray
                val icon =  (weather[0] as JSONObject).get("icon").toString()

                val data2 = JSONObject(result).get("data2") as JSONObject
                val uv = data2.get("value")


                val iconUrl = "http://openweathermap.org/img/w/$icon.png"
                println(iconUrl)

                currentTemp.text = "Current: ${main.get("temp")}"

                minTemp.text = "Min: " + main.get("temp_min").toString()
                maxTemp.text = "Max: " + main.get("temp_max").toString()
                windSpeed.text = "Windspeed: " + wind.get("speed").toString()

                uvIndex.text = "UV index: $uv"

                if (fileExistance("$icon.png")){
                    try {
                        val fis = openFileInput("$icon.png")
                        val img = BitmapFactory.decodeStream(fis)

                        weatherImg.setImageBitmap(img)
                    }catch (e : Exception){
                        e.printStackTrace()
                    }
                    println("##### file name: $icon  is stored")
                }else{
                    DownloadImage(weatherImg, icon).execute(iconUrl)
                    println("#### DOWN LOAD")
                }


            }catch (e: JSONException){
                e.printStackTrace()
            }
            pBarWeather.visibility = View.INVISIBLE


        }
    }

    inner class DownloadImage(val imgV: ImageView, private val icon: String): AsyncTask<String,Int,Bitmap>() {
        override fun doInBackground(vararg urls: String?): Bitmap {
            val urlOfImage = urls[0]
            var image: Bitmap? = null
            try {
                var input = URL(urlOfImage).openStream() as InputStream
                image = BitmapFactory.decodeStream(input)

                var outStream = openFileOutput("$icon.png", Context.MODE_PRIVATE)
                image.compress(Bitmap.CompressFormat.PNG, 80, outStream)
                outStream.flush()
                outStream.close()

            } catch (e: Exception) {
                e.printStackTrace()
            }
            publishProgress(100)

            return image as Bitmap
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            pBarWeather.progress = values[0]!!
        }


        override fun onPostExecute(result: Bitmap) {
            imgV.setImageBitmap(result)
            pBarWeather.visibility = View.INVISIBLE

        }

}}


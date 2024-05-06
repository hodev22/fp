package com.example.myapplicationrealone

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar : Toolbar = findViewById(R.id.toolbar)
        toolbar.title = getString(R.string.app_name)
        setSupportActionBar(toolbar)

        //API 요청 수행 함수
        requestApiData()
    }

    private fun requestApiData() {
        // 백그라운드 스레드에서 API 요청
        Thread {
            try {
                val apiResponse = fetchDataFromApi()
                // API 응답을 처리하는 코드 작성
                // 예를 들어, 응답 데이터를 파싱하고 UI 업데이트를 수행할 수 있습니다.
                runOnUiThread {
                    // UI 업데이트 작업 수행
                }
            } catch (e: Exception) {
                // 예외 처리
                e.printStackTrace()
            }
        }.start()
    }

    private fun fetchDataFromApi(): String {
        // 네트워크 요청을 통해 API 데이터 가져오는 코드 작성
        // 요청할 URL 설정
        val apiUrl = "http://apis.data.go.kr/1360000/HealthWthrIdxServiceV3/getOakPollenRiskIdxV3"
        val url = URL(apiUrl)

        // HttpURLConnection을 사용하여 네트워크 요청 수행
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        // 응답 데이터를 읽어오기
        val inputStream = connection.inputStream
        val bufferedReader = BufferedReader(InputStreamReader(inputStream)) //입력 스트림을 읽을 BufferedReader를 생성
        val response = StringBuilder() //서버 응답을 저장할 StringBuilder를 생성
        var inputLine: String? // BufferedReader를 통해 한 줄씩 읽어와 response에 추가
        while (bufferedReader.readLine().also { inputLine = it } != null) {
            response.append(inputLine)
        }
        bufferedReader.close() // 작업이 끝나면 BufferedReader를 닫습
        return response.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // 메뉴를 포함하는 FrameLayout의 레퍼런스 가져오기
        val menuContainer = findViewById<FrameLayout>(R.id.menu_container)

        // 인플레이트된 메뉴를 FrameLayout에 추가
        val overflowMenu = PopupMenu(this, menuContainer)
        overflowMenu.menuInflater.inflate(R.menu.menu, overflowMenu.menu)
        overflowMenu.setOnMenuItemClickListener { item ->
            // 메뉴 아이템을 클릭할 때 수행할 작업을 여기에 추가
            true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // MenuItem의 ID에 따라 분기
        return when (item.itemId) {
            //R.id.notification의 경우
            R.id.notification -> {
                //"알림"을 나타내는 Toast 표시
                Toast.makeText(this, "알림", Toast.LENGTH_SHORT).show()
                true
            }
            // R.id.navi_menu_more의 경우
            R.id.navi_menu_more -> {
                // "더보기"를 나타내는 Toast 표시
                Toast.makeText(this, "더보기", Toast.LENGTH_SHORT).show()
                true
            }

            else -> {
                //둘다 아니면 onOptionsItemSelected 메서드를 호출해 기본 동작 처리
                super.onOptionsItemSelected(item)
            }
        }
    }

    object ApiExplorer {
        @Throws(IOException::class)
        @JvmStatic
        fun main(args: Array<String?>?) {
            //url 주소
            val urlBuilder =
                StringBuilder("http://apis.data.go.kr/1360000/HealthWthrIdxServiceV3/getOakPollenRiskIdxV3")

            //서비스 키
            urlBuilder.append(
                "?" + URLEncoder.encode("ServiceKey", "UTF-8")
                    .toString() + "=sTccaeq7DXkVfkJLSv9Cz45tiUlOgmL77%2FozpVwb4mA35HODx2IYcmnRno6z2I3UZqinCD04Ih%2BKbENflNiDQg%3D%3D"
            )

            //공공데이터포털에서 받은 인증키 붙이기
            urlBuilder.append(
                "&" + URLEncoder.encode("ServiceKey", "UTF-8")
                    .toString() + "=" + URLEncoder.encode(
                    "-",
                    "UTF-8"
                )
            )

            // 페이지 번호
            urlBuilder.append(
                "&" + URLEncoder.encode("pageNo", "UTF-8").toString() + "=" + URLEncoder.encode(
                    "1",
                    "UTF-8"
                )
            )

            //한 페이지 결과 수
            urlBuilder.append(
                "&" + URLEncoder.encode("numOfRows", "UTF-8").toString() + "=" + URLEncoder.encode(
                    "10",
                    "UTF-8"
                )
            )

            //요청자료형식(XML/JSON)
            urlBuilder.append(
                "&" + URLEncoder.encode("dataType", "UTF-8").toString() + "=" + URLEncoder.encode(
                    "XML",
                    "UTF-8"
                )
            )

            //지역 번호
            urlBuilder.append(
                "&" + URLEncoder.encode("areaNo", "UTF-8")
                    .toString() + "=" + URLEncoder.encode("1100000000", "UTF-8")
            )

            //발표 시점 ex)‘21년 7월 6일 18시 발표'
            urlBuilder.append(
                "&" + URLEncoder.encode("time", "UTF-8")
                    .toString() + "=" + URLEncoder.encode("2024050518", "UTF-8")
            )

            val url = URL(urlBuilder.toString())
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection

            conn.requestMethod = "GET"

            val br: BufferedReader

            if (conn.responseCode >= 200 && conn.responseCode <= 300) {
                br = BufferedReader(InputStreamReader(conn.inputStream))
            } else {
                br = BufferedReader(InputStreamReader(conn.errorStream))
            }
            val sb = StringBuilder()
            var line: String?
            do {
                line = br.readLine()
                sb.append(line)
            } while (line != null)
            br.close()
            conn.disconnect()
            println(sb.toString())


            val urlBuilder1 =
                StringBuilder("http://apis.data.go.kr/1360000/HealthWthrIdxServiceV3/getPinePollenRiskIdxV3")

            //서비스 키
            urlBuilder1.append(
                "?" + URLEncoder.encode("ServiceKey", "UTF-8")
                    .toString() + "=sTccaeq7DXkVfkJLSv9Cz45tiUlOgmL77%2FozpVwb4mA35HODx2IYcmnRno6z2I3UZqinCD04Ih%2BKbENflNiDQg%3D%3D"
            )

            //공공데이터포털에서 받은 인증키 붙이기
            urlBuilder1.append(
                "&" + URLEncoder.encode("ServiceKey", "UTF-8").toString() + "=" + URLEncoder.encode(
                    "-",
                    "UTF-8"
                )
            )

            // 페이지 번호
            urlBuilder1.append(
                "&" + URLEncoder.encode("pageNo", "UTF-8").toString() + "=" + URLEncoder.encode(
                    "1",
                    "UTF-8"
                )
            )

            //한 페이지 결과 수
            urlBuilder1.append(
                "&" + URLEncoder.encode("numOfRows", "UTF-8").toString() + "=" + URLEncoder.encode(
                    "10",
                    "UTF-8"
                )
            )

            //요청자료형식(XML/JSON)
            urlBuilder1.append(
                "&" + URLEncoder.encode("dataType", "UTF-8").toString() + "=" + URLEncoder.encode(
                    "XML",
                    "UTF-8"
                )
            )

            //지역 번호
            urlBuilder1.append(
                "&" + URLEncoder.encode("areaNo", "UTF-8")
                    .toString() + "=" + URLEncoder.encode("1100000000", "UTF-8")
            )

            //발표 시점 ex)‘21년 7월 6일 18시 발표'
            urlBuilder1.append(
                "&" + URLEncoder.encode("time", "UTF-8")
                    .toString() + "=" + URLEncoder.encode("20240503", "UTF-8")
            )


            val br1: BufferedReader

            if (conn.responseCode >= 200 && conn.responseCode <= 300) {
                br1 = BufferedReader(InputStreamReader(conn.inputStream))
            } else {
                br1 = BufferedReader(InputStreamReader(conn.errorStream))
            }
            val sb1 = StringBuilder()
            var line1: String?
            do {
                line1 = br.readLine()
                sb1.append(line1)
            } while (line1 != null)
            br1.close()
            conn.disconnect()
            println(sb1.toString())
        }
    }
}

package com.picpay.desafio.android

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.fixtures.UserFixtures
import com.picpay.desafio.android.presentation.MainActivity
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.exp

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    private val mockWebServer = MockWebServer()

    private val PORT = 8080

    private val successResponse by lazy {
        MockResponse()
            .setResponseCode(200)
    }

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        mockWebServer.start(PORT)
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        scenario.close()
    }

    @Test
    fun shouldDisplayTitle() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        onView(withText(context.getString(R.string.title))).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayLoadingStateWhenDataIsLoaded() {
        val expectedUserList = UserFixtures.getJsonUserList(1)
        mockWebServer.enqueue(
            successResponse
                .setBody(expectedUserList)
        )
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayUserListItemsWhenDataIsLoaded() {
        val expectedUserList = UserFixtures.getJsonUserList(5)
        mockWebServer.enqueue(
            successResponse
                .setBody(expectedUserList)
        )
        val userList = UserFixtures.getUserListFromJson(expectedUserList)
        userList.forEach { user ->
            onView(withText(user.username)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun shouldDisplayItemAtRightPositionWhenDataIsLoaded() {
        val expectedUserList = UserFixtures.getJsonUserList(5)
        mockWebServer.enqueue(
            successResponse
                .setBody(expectedUserList)
        )
        val userList = UserFixtures.getUserListFromJson(expectedUserList)
        userList.forEachIndexed { index, user ->
            RecyclerViewMatchers.checkRecyclerViewItem(
                R.id.recyclerView,
                index,
                withText(user.username)
            )
        }
    }
}
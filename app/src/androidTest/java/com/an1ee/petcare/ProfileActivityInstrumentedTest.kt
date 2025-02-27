package com.an1ee.petcare.ui.activity

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.an1ee.petcare.R
import com.an1ee.petcare.model.Order
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileActivityInstrumentedTest {

    private lateinit var activityScenario: ActivityScenario<ProfileActivity>

    @Before
    fun setUp() {
        // Initialize Intents before each test
        Intents.init()

        // Launch the activity
        activityScenario = ActivityScenario.launch(ProfileActivity::class.java)
    }

    @After
    fun tearDown() {
        // Release Intents after each test
        Intents.release()

        // Close the activity scenario
        activityScenario.close()
    }

    @Test
    fun testProfileActivityDisplaysCorrectly() {
        // Check that the toolbar has the correct title
        onView(withId(R.id.toolbar))
            .check(matches(hasDescendant(withText("Profile"))))

        // Check that the profile image is displayed
        onView(withId(R.id.image_profile))
            .check(matches(isDisplayed()))

        // Check that the name text is displayed
        onView(withId(R.id.text_name))
            .check(matches(allOf(isDisplayed(), withText("John Doe"))))

        // Check that the email text is displayed
        onView(withId(R.id.text_email))
            .check(matches(allOf(isDisplayed(), withText("john.doe@example.com"))))

        // Check that the personal info card is displayed
        onView(withId(R.id.card_personal_info))
            .check(matches(isDisplayed()))

        // Check that the orders card is displayed
        onView(withId(R.id.card_orders))
            .check(matches(isDisplayed()))

        // Check that the orders recycler view is displayed
        onView(withId(R.id.recycler_orders))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testNavigationToProductDashboard() {
        // Click on home button
        onView(withId(R.id.button_home))
            .perform(click())

        // Verify navigation to ProductDashboardActivity
        intended(hasComponent(ProductDashboardActivity::class.java.name))
    }

    @Test
    fun testNavigationToProductCart() {
        // Click on cart button
        onView(withId(R.id.button_cart))
            .perform(click())

        // Verify navigation to ProductCartActivity
        intended(hasComponent(ProductCartActivity::class.java.name))
    }

    @Test
    fun testNavigationToOrder() {
        // Click on "My Orders" text
        onView(withId(R.id.my_order))
            .perform(click())

        // Verify navigation to OrderActivity
        intended(hasComponent(OrderActivity::class.java.name))
    }

    @Test
    fun testOrdersDisplayedInRecyclerView() {
        // Verify the recycler view has at least 2 items (from the sample data)
        onView(withId(R.id.recycler_orders))
            .check(matches(hasMinimumChildCount(2)))

        // Test can be extended to verify specific order content if test data is predictable
    }

    @Test
    fun testOrderItemInteraction() {
        // This test assumes the first order in the recycler view can be clicked
        // and that clicking it does something (you could expand this test based on your app behavior)
        onView(withId(R.id.recycler_orders))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        // Add verification of what should happen when clicking an order item
        // For example, if clicking shows details, you could verify a details view appears
    }

    // Helper method to create an intent with test data if needed
    private fun createProfileActivityIntent(): Intent {
        return Intent(ApplicationProvider.getApplicationContext(), ProfileActivity::class.java).apply {
            // Add any extras needed for testing
        }
    }
}
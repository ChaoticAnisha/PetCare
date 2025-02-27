package com.an1ee.petcare.ui.activity

import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.an1ee.petcare.R
import com.an1ee.petcare.adapter.OrderAdapter
import com.an1ee.petcare.model.Order
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.shadows.ShadowActivity
import org.robolectric.shadows.ShadowIntent

@RunWith(RobolectricTestRunner::class)
class ProfileActivityUnitTest {

    private lateinit var activity: ProfileActivity
    private lateinit var shadowActivity: ShadowActivity

    @Mock
    private lateinit var recyclerOrders: RecyclerView

    @Mock
    private lateinit var orderAdapter: OrderAdapter

    @Mock
    private lateinit var homeButton: ImageView

    @Mock
    private lateinit var cartButton: ImageView

    @Mock
    private lateinit var notificationButton: ImageView

    @Mock
    private lateinit var myOrderText: TextView

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        activity = Robolectric.buildActivity(ProfileActivity::class.java).create().get()
        shadowActivity = shadowOf(activity)

        // Set up RecyclerView mock
        activity.findViewById<RecyclerView>(R.id.recycler_orders)

        // Set up navigation button mocks
        homeButton = activity.findViewById(R.id.button_home)
        cartButton = activity.findViewById(R.id.button_cart)
        notificationButton = activity.findViewById(R.id.button_notification)
        myOrderText = activity.findViewById(R.id.my_order)
    }

    @Test
    fun `test initialization`() {
        // Verify RecyclerView is set up with LinearLayoutManager
        val recyclerView = activity.findViewById<RecyclerView>(R.id.recycler_orders)
        assert(recyclerView.layoutManager is LinearLayoutManager)
        assert(recyclerView.adapter is OrderAdapter)
    }

    @Test
    fun `test home button navigation`() {
        // When home button is clicked
        homeButton.performClick()

        // Then validate intent was created with correct destination
        val expectedIntent = shadowActivity.nextStartedActivity
        val shadowIntent = shadowOf(expectedIntent)
        assert(shadowIntent.intentClass == ProductDashboardActivity::class.java)
        assert(expectedIntent.flags and Intent.FLAG_ACTIVITY_CLEAR_TOP != 0)
    }

    @Test
    fun `test cart button navigation`() {
        // When cart button is clicked
        cartButton.performClick()

        // Then validate intent was created with correct destination
        val expectedIntent = shadowActivity.nextStartedActivity
        val shadowIntent = shadowOf(expectedIntent)
        assert(shadowIntent.intentClass == ProductCartActivity::class.java)
    }

    @Test
    fun `test my order text navigation`() {
        // When my order text is clicked
        myOrderText.performClick()

        // Then validate intent was created with correct destination
        val expectedIntent = shadowActivity.nextStartedActivity
        val shadowIntent = shadowOf(expectedIntent)
        assert(shadowIntent.intentClass == OrderActivity::class.java)
    }

    @Test
    fun `test load orders populates adapter`() {
        // Create a mock adapter
        val mockAdapter = mock(OrderAdapter::class.java)

        // Get recyclerView and set mock adapter
        val recyclerView = activity.findViewById<RecyclerView>(R.id.recycler_orders)
        recyclerView.adapter = mockAdapter

        // Manually call loadOrders (normally called in onCreate)
        val loadOrdersMethod = ProfileActivity::class.java.getDeclaredMethod("loadOrders")
        loadOrdersMethod.isAccessible = true
        loadOrdersMethod.invoke(activity)

        // Verify that submitList was called with a list of orders
        verify(mockAdapter).submitList(anyList())
    }
}
package com.example.ogacorp.malaika





import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

import android.widget.Button

@RunWith(AndroidJUnit4::class)
class AuthenticationUiTest {

    @Test
    fun loginWithBlankFieldsDoesNotNavigateAway() {

        ActivityScenario.launch(LoginActivity::class.java).use { scenario ->

            onView(withId(R.id.btnLogin))
                .perform(click())

            scenario.onActivity { activity ->

                assertEquals(
                    LoginActivity::class.java,
                    activity::class.java
                )

                val loginButton =
                    activity.findViewById<Button>(R.id.btnLogin)

                assertTrue(loginButton.isEnabled)
            }
        }
    }

    @Test
    fun loginWithInvalidEmailDoesNotNavigateAway() {

        ActivityScenario.launch(LoginActivity::class.java).use { scenario ->

            onView(withId(R.id.etLoginEmail))
                .perform(
                    replaceText("not-an-email"),
                    closeSoftKeyboard()
                )

            onView(withId(R.id.etLoginPassword))
                .perform(
                    replaceText("password123"),
                    closeSoftKeyboard()
                )

            onView(withId(R.id.btnLogin))
                .perform(click())

            scenario.onActivity { activity ->

                assertEquals(
                    LoginActivity::class.java,
                    activity::class.java
                )
            }
        }
    }

    @Test
    fun registerWithMismatchedPasswordsDoesNotNavigateAway() {

        ActivityScenario.launch(RegisterActivity::class.java).use { scenario ->

            onView(withId(R.id.etRegName))
                .perform(
                    replaceText("Test Client"),
                    closeSoftKeyboard()
                )

            onView(withId(R.id.etRegEmail))
                .perform(
                    replaceText("client@example.com"),
                    closeSoftKeyboard()
                )

            onView(withId(R.id.etRegPassword))
                .perform(
                    replaceText("Password123"),
                    closeSoftKeyboard()
                )

            onView(withId(R.id.etRegConfirm))
                .perform(
                    replaceText("Different123"),
                    closeSoftKeyboard()
                )

            onView(withId(R.id.btnRegister))
                .perform(click())

            scenario.onActivity { activity ->

                assertEquals(
                    RegisterActivity::class.java,
                    activity::class.java
                )

                val registerButton =
                    activity.findViewById<Button>(R.id.btnRegister)

                assertTrue(registerButton.isEnabled)
            }
        }
    }
}
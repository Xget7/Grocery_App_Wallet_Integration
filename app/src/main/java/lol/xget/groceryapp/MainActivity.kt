package lol.xget.groceryapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import lol.xget.groceryapp.domain.util.Destinations
import lol.xget.groceryapp.recoverPassword.presentation.RecoverPassword
import lol.xget.groceryapp.login.presentation.LoginScreen
import lol.xget.groceryapp.register.presentation.register_seller.RegistrationSellerScreen
import lol.xget.groceryapp.register.presentation.register_user.RegistrationScreen

import lol.xget.groceryapp.seller.profileSeller.presentation.SellerProfileScreen
import lol.xget.groceryapp.presentation.main.SplashScreen
import lol.xget.groceryapp.seller.mainSeller.presentation.AddProducts.AddProductScreen
import lol.xget.groceryapp.seller.mainSeller.presentation.EditProducts.EditProductScreen
import lol.xget.groceryapp.seller.mainSeller.presentation.SellerHomeScreen
import lol.xget.groceryapp.ui.GroceryAppTheme
import lol.xget.groceryapp.user.mainUser.presentation.ProductDetail.ProductDetailScreen
import lol.xget.groceryapp.user.mainUser.presentation.ShopDetails.ShopDetailScreen
import lol.xget.groceryapp.user.mainUser.presentation.UserHomeScreen
import lol.xget.groceryapp.user.profileUser.presentation.ProfileScreen
import lol.xget.groceryapp.user.shoppingCar.presentation.ShoppingCarScreen

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    var mAuth = FirebaseAuth.getInstance()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user: FirebaseUser? = mAuth.currentUser

        setContent {
            GroceryAppTheme {
                Surface(color = MaterialTheme.colors.background) {

                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Destinations.SplashDestinations.route
                    ) {
                        composable(
                            route = Destinations.LoginDestinations.route
                        ) {
                            LoginScreen(navController)
                        }

                        composable(
                            route = Destinations.RegisterUserDestinations.route
                        ) {
                            RegistrationScreen(navController, activity = this@MainActivity)
                        }

                        composable(
                            route = Destinations.RegisterSellerDestinations.route,
                        ) {
                            RegistrationSellerScreen(navController, activity = this@MainActivity)

                        }
                        composable(
                            route = Destinations.RecoverPasswordDestinations.route
                        ) {
                            RecoverPassword(navController)
                        }
                        composable(
                            route = Destinations.ProfileUserDestinations.route
                        ) {
                            ProfileScreen(navController)
                        }
                        composable(
                            route = Destinations.SellerAddProductDestinations.route
                        ) {
                            AddProductScreen(navController)
                        }
                        composable(
                            route = Destinations.SellerHomeDestinations.route
                        ) {
                            SellerHomeScreen(navController)
                        }
                        composable(
                            route = Destinations.SellerProfileDestinations.route
                        ) {
                            SellerProfileScreen(navController)
                        }
                        composable(
                            route = Destinations.SellerEditProductDestinations.route + "/{productId}"
                        ) {
                            EditProductScreen(navController)
                        }
                        composable(
                            route = Destinations.UserHomeDestinations.route
                        ) {
                            UserHomeScreen(navController)
                        }
                        composable(
                            route = Destinations.SplashDestinations.route
                        ) {
                            SplashScreen(navController, user)
                        }
                        composable(
                            route = Destinations.ShopDetailDestinations.route + "/{shopId}"
                        ) {
                            ShopDetailScreen(navController, activity = this@MainActivity)
                        }
                        composable(
                            route = Destinations.ProductDetailDestinations.route
                        ) {
                            ProductDetailScreen(navController)
                        }
                        composable(
                            route = Destinations.ShoppingCar.route
                        ) {
                            ShoppingCarScreen(navController)
                        }
                    }

                }

            }
        }
    }


}

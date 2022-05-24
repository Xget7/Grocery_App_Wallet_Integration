package lol.xget.groceryapp.user.mainUser.presentation.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import lol.xget.groceryapp.ui.components.DialogBoxLoading
import lol.xget.groceryapp.user.mainUser.presentation.components.OrderList

@Composable
fun UserOrdersScreen(
    navController: NavController,
    viewModel: UserOrdersViewModel = hiltViewModel()
) {
    val scrollLazyListState = rememberLazyListState()
    val orderList = remember() {
        viewModel.ordersList
    }
    if (viewModel.state.value.loading == true){
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DialogBoxLoading()
        }

    }else{
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(MaterialTheme.colors.background)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            ) {

                Text(
                    text = "You Orders: ",
                    fontSize = 34.sp,
                    color = MaterialTheme.colors.primaryVariant,
                    fontWeight = FontWeight.Medium
                )



                if (viewModel.state.value.successLoadOders == true){
                    LazyColumn(
                        state = scrollLazyListState
                    ){
                        items(orderList) { order ->
                            Spacer(modifier = Modifier.height(10.dp))
                            OrderList(
                                order = order,
                                shopName = viewModel.getShopTitleFromOrder(order.orderTo!!)
                            ){

                            }
                            Spacer(modifier = Modifier.height(10.dp))

                        }
                    }
                }
            }


        }
    }



}
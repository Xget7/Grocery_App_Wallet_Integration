package lol.xget.groceryapp.profileUser.presentation

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import com.talhafaki.composablesweettoast.util.SweetToastUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import lol.xget.groceryapp.R
import lol.xget.groceryapp.mainUser.domain.User
import lol.xget.groceryapp.domain.util.Screen
import lol.xget.groceryapp.login.presentation.components.EventDialog
import lol.xget.groceryapp.login.presentation.components.RoundedButton
import lol.xget.groceryapp.login.presentation.components.TransparentTextField

@ExperimentalCoroutinesApi
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {

    //TODO(fix upload progile photo and loading box and nav to menu when succesfull)

    var profileImage by remember {
        mutableStateOf<Uri?>(null)
    }

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){ uri->

        profileImage = uri
    }
    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }

    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ){
        profileImage?.let {

            if (Build.VERSION.SDK_INT < 28){
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver, it)
            }else{
                val source = ImageDecoder.createSource(context.contentResolver, it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }


        }
        IconButton(
            onClick = {
                //oNBAck

            }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back Icon",
                tint = MaterialTheme.colors.primary
            )
        }



        Text(
            modifier = Modifier.padding(start = 165.dp, top = 14.dp),
            text = "Profile",
            style = MaterialTheme.typography.h6.copy(
                color = MaterialTheme.colors.primaryVariant
            )
        )


        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top

            ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, bottom = 50.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Card(
                            modifier = Modifier
                                .size(100.dp)
                                .testTag(tag = "circle")
                                .clickable(
                                    onClick = { launcher.launch("image/*") }
                                ),
                            shape = CircleShape,
                            elevation = 12.dp
                        ) {
                            if (bitmap.value != null){
                                bitmap.value?.let { btm ->
                                    Image(
                                        bitmap = btm.asImageBitmap(),
                                        contentDescription = "User Profile Image",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize(),
                                    )
                                }
                            }else{

                                GlideImage(
                                        imageModel = viewModel.profilePhoto.value,
                                        // Crop, Fit, Inside, FillHeight, FillWidth, None
                                        contentScale = ContentScale.Crop,
                                        // shows an image with a circular revealed animation.
                                        // shows a placeholder ImageBitmap when loading.
                                        placeHolder = ImageBitmap.imageResource(R.drawable.holder),
                                        // shows an error ImageBitmap when the request failed.
                                        error = ImageBitmap.imageResource(R.drawable.error)
                                )


                            }



                        }
                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = viewModel.fullNameValue.value,
                            style = MaterialTheme.typography.h4.copy(
                                fontWeight = FontWeight.Medium
                            )
                        )


                        Spacer(modifier = Modifier.height(5.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 25.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {


                            TransparentTextField(
                                singleLine = true ,
                                textFieldValue = viewModel.fullNameValue,
                                textLabel = "Name",
                                keyboardType = KeyboardType.Text,
                                keyboardActions = KeyboardActions(
                                    onNext = {focusManager.moveFocus(FocusDirection.Down)}
                                ),
                                imeAction = ImeAction.Next
                            )

                            TransparentTextField(
                                textFieldValue = viewModel.phoneValue,
                                textLabel = "Phone Number",
                                maxChar = 10,
                                keyboardType = KeyboardType.Phone,
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                ),
                                imeAction = ImeAction.Next
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)

                            ) {
                                TransparentTextField(
                                    modifier = Modifier
                                        .width(100.dp)
                                        .height(50.dp),
                                    textFieldValue = viewModel.country,
                                    textLabel = "Country",
                                    keyboardType = KeyboardType.Text,
                                    keyboardActions = KeyboardActions(
                                        onNext = {
                                            focusManager.moveFocus(FocusDirection.Down)
                                        }
                                    ),
                                    imeAction = ImeAction.Next
                                )

                                TransparentTextField(
                                    modifier = Modifier
                                        .width(100.dp)
                                        .height(50.dp),
                                    textFieldValue = viewModel.stateValue,
                                    textLabel = "State",
                                    keyboardType = KeyboardType.Text,
                                    keyboardActions = KeyboardActions(
                                        onNext = {
                                            focusManager.moveFocus(FocusDirection.Down)
                                        }
                                    ),
                                    imeAction = ImeAction.Next
                                )

                                TransparentTextField(
                                    modifier = Modifier
                                        .width(100.dp)
                                        .height(50.dp),
                                    textFieldValue = viewModel.cityValue,
                                    textLabel = "City",
                                    keyboardType = KeyboardType.Text,
                                    keyboardActions = KeyboardActions(
                                        onNext = {
                                            focusManager.moveFocus(FocusDirection.Down)
                                        }
                                    ),
                                    imeAction = ImeAction.Next
                                )
                            }

                            TransparentTextField(
                                textFieldValue = viewModel.addressValue,
                                textLabel = " Address",
                                keyboardType = KeyboardType.Text,
                                keyboardActions = KeyboardActions(
                                    onNext = {
                                        focusManager.moveFocus(FocusDirection.Down)
                                    }
                                ),
                                imeAction = ImeAction.Next
                            )

                            val user = User(
                                profilePhoto = profileImage.toString(),
                                accountType = "user",
                                userName = viewModel.fullNameValue.value ,
                                phone = viewModel.phoneValue.value,
                                state = viewModel.stateValue.value,
                                city = viewModel.cityValue.value,
                                address = viewModel.addressValue.value,
                                country = viewModel.country.value,
                                uid = viewModel.firebaseAuthCurrentUser
                            )

                            Spacer(modifier = Modifier.height(6.dp))
                            RoundedButton(
                                text = "Save",
                                displayProgressBar = viewModel.state.value.loading!!,
                                onClick = {
                                    viewModel.updateUserProfile(user, profileImage)
                                }
                            )

                        }
                    }
                }

            }

        if (viewModel.state.value.successUpdate!!){
            SweetToastUtil.SweetSuccess(
                message = "Success Update!",
                duration = Toast.LENGTH_SHORT,
                padding = PaddingValues(top = 16.dp),
                contentAlignment = Alignment.TopCenter
            )
            LaunchedEffect(viewModel.state.value.successUpdate){
                delay(500)
                navController.navigate(
                    Screen.UserHomeScreen.route
                )
            }
        }


            if (viewModel.state.value.errorMsg != null){
                EventDialog(errorMessage = viewModel.state.value.errorMsg, onDismiss = {viewModel.hideErrorDialog()}) }
        }
    }




package com.example.composedemo

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composedemo.ui.theme.ComposeDemoTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Blue
                ) {
                    ArtSpaceDemo()
                }
            }
        }
    }
}

/*
* Art Space
* */
class ArtItem(val painter: Painter,
              val title: String,
              val author: String,
              val year: String) {
}

@Composable
fun ArtSpaceDemo() {

    val artItems = arrayOf(
        ArtItem(painter = painterResource(id = R.drawable.my_pic), title = "图图在这", author = "大耳朵图图", year =  "2023"),
        ArtItem(painter = painterResource(id = R.drawable.no_way), title = "不是吧阿Sir", author = "Web", year =  "2022"),
        ArtItem(painter = painterResource(id = R.drawable.nopicsaywhat), title = "没图你说个锤子", author = "神雕侠侣", year =  "2021"),
        ArtItem(painter = painterResource(id = R.drawable.show_us), title = "不是不相信你啊，是想让我们开开眼界", author = "功夫", year =  "2023"),
        ArtItem(painter = painterResource(id = R.drawable.traditionalkungfu), title = "传统功夫点到为止", author = "葵花点穴手", year =  "2022"),
        ArtItem(painter = painterResource(id = R.drawable.wheremypic), title = "俺的图图呢", author = "大耳朵图图", year =  "2021"),
    )

    var index by remember { mutableStateOf(0) }

    val changeIndex = { switch2Last: Boolean ->
        val count = artItems.count()

        val next = when (switch2Last) {
            true -> index - 1
            false -> index + 1
        }

        index = when(next) {
            -1 -> count - 1
            count -> 0
            else -> next
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = 15.dp, end = 15.dp, top = 20.dp, bottom = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp, alignment = Alignment.Bottom)) {
        //weight set 1 for fill whole space
        ArtPreview(modifier = Modifier.weight(1f),item = artItems[index])
        ArtistUI(title = artItems[index].title,
            name = artItems[index].author,
            year = artItems[index].year)
        SwitchPreview(modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
            onValueChange = changeIndex)
    }
}

@Composable
fun ArtPreview(item: ArtItem, modifier: Modifier) {
    Column(modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            //border的次序会影响生成的效果
            modifier = Modifier.fillMaxWidth()
                .border(2.dp, Color.Black)
                .padding(30.dp),
            painter = item.painter,
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
    }
}

@Composable
fun ArtistUI(modifier: Modifier = Modifier, title: String, name: String, year: String) {

      Column(modifier = modifier.background(color = Color.White).shadow(3.dp).padding(15.dp)
      ) {
          Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
          Row() {
              Text(text = name, fontSize = 10.sp, fontWeight = FontWeight.Bold)
              Text("($year)", fontSize = 10.sp)
          }
      }
}

@Composable
fun SwitchPreview(modifier: Modifier = Modifier, onValueChange:(changeToLast: Boolean)->Unit) {
    Row(modifier = modifier
        ,
        horizontalArrangement = Arrangement.spacedBy(30.dp)){
        //此处是否有必要单独提出来做一个函数
        Button(onClick = { onValueChange(true) },
            modifier = Modifier.weight(1f)) {
            Text(text = "Previous", color = Color.White, fontSize = 16.sp)
        }
        Button(onClick = { onValueChange(false) },
            modifier = Modifier.weight(1f)) {
            Text(text = "Next", color = Color.White,fontSize = 16.sp)
        }
    }
}

/*
* DiceRoll
* */
@Composable
fun DiceRollProjection(modifier: Modifier) {

    var result by remember {
        mutableStateOf(1)
    }

    var resourceImage = when(result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        6 -> R.drawable.dice_6
        else -> R.drawable.dice_1
    }

    Column(modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = resourceImage), contentDescription = null)
        Spacer(modifier = Modifier.height( 16.dp))
        Button(modifier = Modifier.size(100.dp,40.dp),onClick = { result = (1..6).random() }) {
            Text(text = "Roll", color = Color.White, fontSize = 16.sp)
        }
    }
}

/*
* TipCalculator
* */
@Composable
fun TipCounterUI() {
    var amountInput by remember { mutableStateOf("150") }
    var percentInput by remember { mutableStateOf("15") }
    var roundUp by remember { mutableStateOf(false) }

    var amount: Double = amountInput.toDoubleOrNull() ?: 0.0
    val percent: Double = percentInput.toDoubleOrNull() ?: 15.0
    val tip = calculateTip(amount, percent, roundUp)

    val keybordFocus = LocalFocusManager.current

    Column(modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = stringResource(id = R.string.calculate_tip),
//            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(16.dp))
        EditNumberTextFeild(amountInput,
            labelText = R.string.bill_amount,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { keybordFocus.moveFocus(FocusDirection.Next) }
            )
        ){ amountInput = it }
        Spacer(modifier = Modifier.height(16.dp))
        EditNumberTextFeild(percentInput,
            labelText = R.string.how_was_the_service,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = { keybordFocus.clearFocus() }
            )
        ){ percentInput = it }
        Row(modifier = Modifier
            .fillMaxWidth()
            .size(48.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = stringResource(id = R.string.round_up_tip),
                fontSize = 12.sp)
            Switch(checked = roundUp,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.End),
                onCheckedChange = { roundUp = it })
        }
        Text(text = stringResource(id = R.string.tip_amount,tip),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}

@Composable
fun EditNumberTextFeild(value: String,
                        @StringRes labelText: Int,
                        keyboardOptions: KeyboardOptions,
                        keyboardActions: KeyboardActions,
                        onValueChange:(String)->Unit) {
    TextField(value = value, label = { Text(text = stringResource(id = labelText)) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        onValueChange = onValueChange)
}

fun calculateTip(serverCost: Double, percent: Double = 15.0, isRoundUp: Boolean): String {
    var tip = serverCost * percent / 100.0
    if(isRoundUp) {
        tip = kotlin.math.ceil(tip)
    }
    return NumberFormat.getCurrencyInstance().format(tip)
}

/*
* Learn Together
* */

@Composable
fun LenrnTogether() {
    val image = painterResource(id = R.drawable.bg_compose_background)
    val title = stringResource(R.string.article_title)
    var p1 = stringResource(R.string.article_p1)
    var p2 = stringResource(R.string.article_p2)

    Column(modifier = Modifier.fillMaxSize(1f)) {
        Image(painter = image, contentDescription = null)
        Text(text = title,
            fontSize = 24 .sp,
            modifier = Modifier.padding(16 .dp))
        Text(text = p1,
            modifier = Modifier.padding(start = 16 .dp, end = 16 .dp),
            textAlign = TextAlign.Justify)
        Text(text = p2,
            modifier = Modifier.padding(16 .dp),
            textAlign = TextAlign.Justify)
    }
}

@Composable
fun AllTaskDoneUI() {
    val image = painterResource(id = R.drawable.ic_task_completed)
    val hint = "All tasks completed"
    var des = "Nice work!"

    Box {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = image, contentDescription = null)
            Text(
                text = hint,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
            )
            Text(
                text = des,
                fontSize = 16.sp
            )
        }
    }

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!", modifier = Modifier.padding(30.dp))
}

/*
* HappyBirthday
* */
@Composable
fun HappyBirthdayGreetingWithImage(name: String, from: String) {
    //此处不能提取为Image对象，会直接作为Image绘制出来
    val image = painterResource(id = R.drawable.androidparty)
    val modifier = Modifier.background(color = Color.Unspecified)

    Box {
        Image(painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Happy Birthday, $name",
                fontSize = 36.sp,
                modifier = Modifier.padding(
                    bottom = 30.dp
                )
            )
            Text(text = " --- from $from",
                modifier = Modifier
                    .padding(end = 16.dp)
                    .align(alignment = Alignment.End))
        }
    }
}

/*
*
* Composable Axis
* */
@Composable
fun AxisChild(modifier: Modifier, color: Color, title: String, content: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .background(color = color)
            .padding(16.dp)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(text = content, textAlign = TextAlign.Justify)
    }
}

//分成四象限
@Composable
fun FourAxis() {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(Modifier.weight(1f)) {
            AxisChild(
                modifier = Modifier.weight(1f),
                color = Color.Green,
                title = "Text composable",
                content = "Displays text and follows Material Design guidelines.")
            AxisChild(
                modifier = Modifier.weight(1f),
                color = Color.Yellow,
                title = "Image composable",
                content = "Creates a composable that lays out and draws a given Painter class object.")
        }
        Row(Modifier.weight(1f)) {
            AxisChild(
                modifier = Modifier.weight(1f),
                color = Color.Cyan,
                title = "Row composable",
                content = "A layout composable that places its children in a horizontal sequence.")
            AxisChild(
                modifier = Modifier.weight(1f),
                color = Color.LightGray,
                title = "Column composable",
                content = "A layout composable that places its children in a vertical sequence.")
        }
    }
}

//名片
@Composable
fun IDCardCompose() {

    val textColor = Color.White
    val image1 = Icons.Rounded.Phone
    val image2 = Icons.Rounded.Share
    val image3 = Icons.Rounded.Email

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0a3042))
            .padding(bottom = 20.dp)
    ) {
        //这部分可以提出来做单独的布局
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.android_logo),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Text(text = "Jackson",
                textAlign = TextAlign.Center,
                color = textColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold)
            Text(text = "iOS Developer",
                textAlign = TextAlign.Center,
                color = textColor,
                fontSize = 12.sp)
        }

        PersonInfoCard(icon = image1, content = "13333333333")
        PersonInfoCard(icon = image2, content = "@Jackson")
        PersonInfoCard(icon = image3, content = "jackson@163.com")
    }
}

@Composable
fun PersonInfoCard(icon: ImageVector, content: String) {
    //理论上来讲应该是有个设置border的方法的，暂时这么处理
    Divider(color = Color.White)
//    Box(modifier = Modifier
//        .fillMaxWidth()
//        .height(1.dp)
//        .background(Color.White))
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(44.dp)
        .padding(start = 30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start) {
        Icon(icon, contentDescription = null, tint = Color(0xff45dc84))
        //间距对象
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = content, fontSize = 10 .sp, color = Color.White)
    }
}

@Preview(showBackground = true)
@Composable()
fun DefaultPreview() {
    ComposeDemoTheme {
        //我想过这个modifier应该放在哪里，将骰子+按钮视作一个整体来说的话，确实应该将其放在外部传递
//        DiceRollProjection(Modifier.fillMaxSize().wrapContentHeight(Alignment.CenterVertically))
        ArtSpaceDemo()
    }
}
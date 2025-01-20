package com.example.vladislav.androidstudy.compose

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vladislav.androidstudy.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

// https://habr.com/ru/companies/rncb/articles/669374/

@Composable
fun TextDemo() {
    Text(
        "Hello World",
        Modifier
            .alpha(0.5f)
            .border(2.dp, Color.Blue)
            .background(Color.Red)
            .blur(2.dp)
            .fillMaxWidth()
            .padding(8.dp),
//                .size(100.dp)
        fontFamily = FontFamily.SansSerif,
        fontSize = 32.sp,
        fontStyle = FontStyle.Italic,
        textAlign = TextAlign.Center
    )
}

@Composable
fun TextCenterAlignment() {
    Box(
        modifier = Modifier
            .background(Color.Green)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "Hello World",
            fontSize = 32.sp,
        )
    }
}

@Composable
fun BoxSample() {
    Box(
        modifier = Modifier
//            .align(Alignment.Center)  // align is allowed when there is an outer block like Box. Check next fun BoxCenteredSample
            .background(Color.Red)
            .size(200.dp)
            .padding(32.dp)
            .background(Color.Green)
            .padding(32.dp)
            .background(Color.Blue),
        //contentAlignment = Alignment.Center,  // Doesn't help centering in this case
    ) //{}
}

@Composable
fun BoxCenteredSample() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .background(Color.Red)
                .size(200.dp)
                .padding(32.dp)
                .background(Color.Green)
                .padding(32.dp)
                .background(Color.Blue)
        )
    }
}

@Composable
fun Stepper(modifier: Modifier, itemsList: ImmutableList<StepperModel>) {
    Column(
        modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(Color.White)
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        itemsList.forEach {
            Row(
                modifier.height(IntrinsicSize.Max)  // For Box with fillMaxHeight could not take full height, but only until Spacer, also check https://stackoverflow.com/questions/67677125/fill-height-for-child-in-row
            ) {
                Column(
                    modifier = modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(it.numberBackgroundColor)
                    ) {
                        Text(
                            text = it.number,
                            modifier = modifier
                                .align(Alignment.Center)
                                .offset(0.dp, (-1).dp)
                        )
                    }
                    if (!it.isLastClause) {
                        Box(
                            modifier = modifier
                                .padding(top = 2.dp, bottom = 2.dp)
                                .width(1.dp)
                                .background(color = Color.Gray)
                                .fillMaxHeight(),
                        )
                    }
                }
                Column(modifier.padding(start = 8.dp)) {
                    Text(text = it.title)
                    Text(text = it.subtitle)
                    if (!it.isLastClause) {
                        Spacer(modifier = Modifier.size(10.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun StepperPreview() {
    val itemsList = listOf(
        StepperModel(
            numberBackgroundColor = Color.Green,
            number = "1",
            title = "Title 1",
            subtitle = "Subtitle 1",
            isLastClause = false
        ),
        StepperModel(
            numberBackgroundColor = Color.Green,
            number = "2",
            title = "Title 2",
            subtitle = "Here goes some text that is 2-3 rows long. Usually some description. Here goes some text that is 2-3 rows long. Usually some description.",
            isLastClause = false
        ),
        StepperModel(
            numberBackgroundColor = Color.Gray,
            number = "3",
            title = "Title 3",
            subtitle = "Here goes some text that is 2-3 rows long. Usually some description.",
            isLastClause = true
        )
    ).toImmutableList()
    Stepper(Modifier, itemsList)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardItem() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .animateContentSize(
                animationSpec = tween(
                    delayMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
//            .clip(RoundedCornerShape(12.dp)),     // Same to next row
        shape = RoundedCornerShape(12.dp),
        onClick = { }   // Providing this empty lambda adds ripple effect to the card
    ) {
        Row(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0.8f, 0.7f, 0.7f, 1f),
                            Color(1f, 1f, 1f, 1f),
                            Color(0.8f, 0.7f, 0.7f, 1f)
                        ),
                        start = Offset(0f, Float.POSITIVE_INFINITY),
                        end = Offset(Float.POSITIVE_INFINITY, 0f)
                    )
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
//                modifier = Modifier.clip(CircleShape).border(2.dp, Color.White, CircleShape),
                painter = painterResource(id = R.drawable.space_marine),
                contentDescription = "Space Marine"
            )
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = "Warhammer 40,000", fontSize = 20.sp)
                Text(text = "PC Game", fontSize = 16.sp)
            }
            DropDownMenu()
        }
    }
}

@Composable
fun RowScope.DropDownMenu() {   // One adds RowScope to be able to run it in a Row section
    var expanded by remember { mutableStateOf(false) }
    IconButton(
        modifier = Modifier.align(Alignment.CenterVertically),
        onClick = { expanded = true }) {
        Icon(Icons.Default.MoreVert, contentDescription = "Показать меню")
    }
    DropdownMenu(   // https://metanit.com/kotlin/jetpack/4.17.php
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        Text("Copy", fontSize = 18.sp, modifier = Modifier.padding(16.dp))
        Text("Paste", fontSize = 18.sp, modifier = Modifier.padding(16.dp))
        Text("Settings", fontSize = 18.sp, modifier = Modifier.padding(16.dp))
    }
}

@Preview()  // One can make several previews
@Composable
fun CardsPreview() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        repeat(8) {
            CardItem()
            Spacer(modifier = Modifier.size(12.dp))
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardWithExpandableSubtitleItem() {
    //    var isExpanded = mutableStateOf(false)  // Cannot be done this way. Check the error explanation
    var isExpanded by remember { mutableStateOf(false) }
    val subtitleMaxLines by remember {
        derivedStateOf {
            if (isExpanded) {
                7
            } else {
                1
            }
        }
    }
    val subtitleText by remember {
        derivedStateOf {
            if (isExpanded) {
                "Personal computer real time strategy game. Several races like Necrons, Chaos, Space Marines, Tau, Orks, Eldars fight each other on a \"space arena\"."
            } else {
                "PC Game"
            }
        }
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    delayMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = RoundedCornerShape(12.dp),
        onClick = {
            isExpanded = !isExpanded
        }
    ) {
        Row(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0.8f, 0.7f, 0.7f, 1f),
                            Color(1f, 1f, 1f, 1f),
                            Color(0.8f, 0.7f, 0.7f, 1f)
                        ),
                        start = Offset(0f, Float.POSITIVE_INFINITY),
                        end = Offset(Float.POSITIVE_INFINITY, 0f)
                    )
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = R.drawable.space_marine),
                contentDescription = "Space Marine"
            )
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(top = 8.dp, bottom = 8.dp)
                    .weight(1f),
            ) {
                Text(text = "Warhammer 40,000", fontSize = 20.sp)
                Text(text = subtitleText, maxLines = subtitleMaxLines, fontSize = 16.sp)
            }
            DropDownMenu()
        }
    }
}

@Preview
@Composable
fun CardWithExpandableSubtitleItemListPreview() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        repeat(8) {
            CardWithExpandableSubtitleItem()
            Spacer(modifier = Modifier.size(12.dp))
        }
    }
}

/**
 * When counter == 10, makes button to be green.
 */
@Preview
@Composable
fun CounterCardItemPreview() {
    val counter = remember { mutableIntStateOf(0) }
    val backgroundColor = remember { mutableStateOf(Color.Red) }
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            counter.intValue++
            if (counter.intValue == 10) {
                backgroundColor.value = Color.Green
            }
        }
    ) {
        Text(
            modifier = Modifier
                .background(backgroundColor.value)
                .padding(8.dp),
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            text = counter.intValue.toString()
        )
    }
}

@Preview
@Composable
fun CounterCardListPreview() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        repeat(15) {
            CounterCardItemPreview()
            Spacer(modifier = Modifier.size(12.dp))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Pager(modifier: Modifier) {
    val horizontalPagerState = rememberPagerState(pageCount = { 5 })
    val verticalPagerState = rememberPagerState(pageCount = { 5 })
    HorizontalPager(
        modifier = modifier.fillMaxSize(),
        state = horizontalPagerState
    ) { horizontalPageIndex ->
        VerticalPager(
            modifier = modifier.fillMaxSize(),
            state = verticalPagerState
        ) { verticalPageIndex ->
            Box(
                modifier = modifier
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(horizontalPageIndex * 0.1f, 1f - horizontalPageIndex * 0.2f,  horizontalPageIndex * 0.2f, 1f),
                                Color(1f, 1f, 1f, 1f),
                                Color(1f - verticalPageIndex * 0.2f, 1f - verticalPageIndex * 0.2f,  verticalPageIndex * 0.2f, 1f)
                            ),
                            start = Offset( (horizontalPageIndex) * 300f, 1600f + (verticalPageIndex - 2) * 300f),
                            end = Offset(500f +(horizontalPageIndex) * 300f, 1200f + (verticalPageIndex - 2) * 300f)
                        )
                    )
            ) {
                Text(
                    modifier = modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center), // Centers the text, check https://stackoverflow.com/questions/70708107/how-to-make-text-centered-vertically-in-android-compose
                    textAlign = TextAlign.Center,
                    text = "Page ${horizontalPageIndex + 1}|${verticalPageIndex + 1}",
                    fontSize = 32.sp,
                )
            }
        }
    }
}

@Preview
@Composable
fun PagerPreview() {
    Pager(Modifier)
}
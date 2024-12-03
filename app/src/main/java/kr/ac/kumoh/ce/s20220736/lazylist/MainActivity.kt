package kr.ac.kumoh.ce.s20220736.lazylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kr.ac.kumoh.ce.s20220736.lazylist.model.Song
import kr.ac.kumoh.ce.s20220736.lazylist.ui.theme.LazyListTheme
import kr.ac.kumoh.ce.s20220736.lazylist.viewmodel.SongViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LazyListTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: SongViewModel = viewModel()) {
    val listSong by viewModel.songs.observeAsState()

    repeat(30) { index ->
        viewModel.add(Song(index * 3, "First", "일"))
        viewModel.add(Song(index * 3 + 1, "Second", "이"))
        viewModel.add(Song(index * 3 + 2, "Third", "삼"))
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//        if (listSong != null) {
            listSong?.let {
                MyList(
                    modifier = Modifier.padding(innerPadding),
                    // 느낌표(!) 두 개 = 절대 Null일 수 없다는 뜻
                    list = listSong!!
                )
            } ?: run {
                Text(
                    "리스트가 없습니다.",
                    modifier = Modifier.padding(innerPadding)
                )
            }
//        }
    }
}

@Composable
fun TextTitle(title: String) {
    Text(title, fontSize = 30.sp)
}

@Composable
fun TextSinger(title: String) {
    Text(title, fontSize = 20.sp)
}

@Composable
fun SongItem(song: Song) {
    Column(
        modifier = Modifier
            // 이 순서가 중요하다.
            .fillMaxWidth()
            .background(Color(0xffffffcc))
            .padding(16.dp)
    ) {
        TextTitle(song.title)
        TextSinger("이 노래를 부른 가수는 ${song.singer} 입니다")
    }
}

@Composable
fun MyList(modifier: Modifier = Modifier, list: List<Song>) {
    LazyColumn (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(list) { song ->
            SongItem(song)
        }

    }

}
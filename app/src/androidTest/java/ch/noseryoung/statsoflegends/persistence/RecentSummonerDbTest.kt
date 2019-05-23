package ch.noseryoung.statsoflegends.persistence

import android.content.Context
import androidx.room.Room
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import androidx.test.rule.ActivityTestRule
import ch.noseryoung.statsoflegends.SearchActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException

@RunWith(JUnit4::class)
class RecentSummonerDbTest {
    private lateinit var recentSummonerDao: RecentSummonerDao
    private lateinit var db: RecentSummonerDb

    @get:Rule
    var mActivityRule = ActivityTestRule(
        SearchActivity::class.java
    )

    @Before
    fun createDb() {
        val context = mActivityRule.activity.applicationContext
        db = Room.inMemoryDatabaseBuilder(
            context, RecentSummonerDb::class.java).build()
        recentSummonerDao = db.recentSummonerDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeSummonerAndReadInList() {
        val summoner: RecentSummonerData = RecentSummonerData(summonerName = "jeff", region = "EUW")
        recentSummonerDao.insert(summoner)
        val returnedSummoner = recentSummonerDao.getAll()

        assertThat(returnedSummoner[0], equalTo(summoner))
    }
}
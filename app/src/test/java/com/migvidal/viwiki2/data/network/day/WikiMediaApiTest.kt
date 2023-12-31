package com.migvidal.viwiki2.data.network.day

import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.HttpException

@RunWith(JUnit4::class)
class WikiMediaApiTest {
    private val year = 2023
    private val month = 1

    @Test
    fun getFeatured_WithCorrectDate_doNotThrowException() {
        val day = 10
        runBlocking {
            try {
                WikiMediaApiImpl.wikiMediaApiService.getFeatured(
                    yyyy = year.toString(),
                    mm = String.format("%02d", month),
                    dd = String.format("%02d", day),
                )
                assert(true)
            } catch (e: Exception) {
                e.printStackTrace()
                assert(false)
            }
        }
    }

    @Test
    fun getFeatured_WithImpossibleDate_ThrowException() {
        val impossibleDay = 32
        runBlocking {
            try {
                WikiMediaApiImpl.wikiMediaApiService.getFeatured(
                    yyyy = year.toString(),
                    mm = String.format("%02d", month),
                    dd = String.format("%02d", impossibleDay),
                )
            } catch (e: Exception) {
                assert(e is HttpException)
            }
        }
    }
}
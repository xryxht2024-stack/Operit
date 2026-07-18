package com.ai.assistance.operit.api.chat.enhance

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ToolExecutionManagerTest {

    @Test
    fun extractToolInvocations_shouldKeepAllToolBlocksInSameChunk() = runBlocking {
        val response = """
        """.trimIndent()

        val invocations = ToolExecutionManager.extractToolInvocations(response)

        assertEquals(3, invocations.size)
        assertEquals(
            listOf(
                "https://www.baidu.com",
                "https://www.bing.com",
                "https://www.github.com"
            ),
            invocations.map { invocation ->
                invocation.tool.parameters.first { it.name == "url" }.value
            }
        )
    }
}
